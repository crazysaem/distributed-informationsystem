package rwi.internal.dispatcher.communication;

import java.nio.channels.NetworkChannel;

import rwi.core.classes.NetWorkIS;
import rwi.core.variables.RwiCommunication;
import rwi.internal.dispatcher.Dispatcher;

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
	
	public void handleSplitRequest(NetWorkIS nwis,int mode){
		if(mode == RwiCommunication.SPLIT_METHOD_HALF){
			splittemp = nwis;
			modetemp = mode;
			splitstate = SIGNALING_STATE_WAITING_FOR_IS;
			SignalTransfer.askForInfoSystem(dis.getPort());	
		}else if(mode == RwiCommunication.SPLIT_METHOD_HALF){
			//TODO
		}
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
		String result = SignalTransfer.sendParentAndRange(target, ownport, target.getRange());
		return result.equals(RwiCommunication.READY);
	}
	
	public void setState(int state){
		this.state = state;
	}

	public static final int SIGNALING_STATE_NOTHING_TO_DO = 0;
	public static final int SIGNALING_STATE_WAITING_FOR_IS = 1;
	public static final int SIGNALING_STATE_WAITING_FOR_DS = 2;
}
