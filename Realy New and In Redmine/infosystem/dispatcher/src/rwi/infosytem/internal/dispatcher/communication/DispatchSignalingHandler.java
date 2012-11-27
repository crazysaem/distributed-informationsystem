package rwi.infosytem.internal.dispatcher.communication;

import rwi.infosystem.core.classes.NetWorkIS;
import rwi.infosystem.core.variables.RwiCommunication;
import rwi.infosystem.internal.dispatcher.Dispatcher;
/**
 * handle means an incoming request
 * send or forward means creating a new request
 * @author Mr.N0I3oDy
 *
 */
public class DispatchSignalingHandler {

	protected Dispatcher dis;
	protected int state;
	protected int splitstate;
	protected NetWorkIS splittemp;
	protected int modetemp;

	public DispatchSignalingHandler(Dispatcher dis) {
		this.dis = dis;
		this.state = SIGNALING_STATE_NOTHING_TO_DO;
		this.splitstate = SIGNALING_STATE_NOTHING_TO_DO;
	}

	public void handleUnregister(int id) {
		dis.unregister(id);
	}

	public void forwarUnregister(String ip, String port, int id) {
		SignalTransfer.forwardUnregister(ip, port, id);
	}
	
	public void askForInfoSystem(String port){
		this.state = SIGNALING_STATE_WAITING_FOR_IS;		
		SignalTransfer.askForInfoSystem(port);		
	}
	
	public void handleInfoSystemReady(NetWorkIS nwis){
		if (state == SIGNALING_STATE_WAITING_FOR_IS){
			dis.addInfoSystem(nwis);
			state = SIGNALING_STATE_NOTHING_TO_DO;
		}//if there is a split waiting for an is
		else if(splitstate == SIGNALING_STATE_WAITING_FOR_IS){
			dis.split(splittemp, modetemp, nwis);
			splitstate = SIGNALING_STATE_NOTHING_TO_DO;
		}
	}
	//new InfoSystem was created successfully
	public void handleInfoSystemReady(String ip,String port){
		handleInfoSystemReady(new NetWorkIS(ip, port));
		
	}
	
	public boolean setIsParentAndRange(NetWorkIS target,String ownport){
		for(int x=0;x<10;x++){
			String result = SignalTransfer.sendParentAndRange(target, ownport, target.getRange()); 
			if(result!=null && result.equals(RwiCommunication.READY))
				return true;
			else{
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;		
	}
	
	public void setState(int state){
		this.state = state;
	}

	public void handleSplitRequest(String ip,String port,int type){
		if(type == RwiCommunication.SPLIT_TYPE_ONE || type == RwiCommunication.SPLIT_TYPE_TWO){
			splittemp = new NetWorkIS(ip, port);
			modetemp = type;
			splitstate = SIGNALING_STATE_WAITING_FOR_IS;
			SignalTransfer.askForInfoSystem(dis.getPort());	
		}else if(type == RwiCommunication.SPLIT_TYPE_FOUR){
			//TODO
		}
	}
	
	public static final int SIGNALING_STATE_NOTHING_TO_DO = 0;
	public static final int SIGNALING_STATE_WAITING_FOR_IS = 1;
	public static final int SIGNALING_STATE_WAITING_FOR_DS = 2;
}
