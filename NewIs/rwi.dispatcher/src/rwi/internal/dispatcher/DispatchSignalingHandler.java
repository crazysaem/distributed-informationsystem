package rwi.internal.dispatcher;

import rwi.core.classes.NetWorkIS;
import rwi.internal.dispatcher.communication.SignalTransfer;

public class DispatchSignalingHandler {

	protected Dispatcher dis;
	protected int state;

	public DispatchSignalingHandler(Dispatcher dis) {
		this.dis = dis;
		this.state = SIGNALING_STATE_NOTHING_TO_DO;
	}

	public void handleUnregister(int id) {
		dis.unregister(id);
	}

	public void forwarUnregister(String ip, String port, int id) {
		SignalTransfer.forwardUnregister(ip, port, id);
	}

	public void handleAddInfoSystem(NetWorkIS nwis) {
		if (state == SIGNALING_STATE_WAITING_FOR_IS)
			dis.addInfoSystem(nwis);
	}
	
	public void askForInfoSystem(String port){
		this.state = SIGNALING_STATE_WAITING_FOR_IS;		
		SignalTransfer.askForInfoSystem(port);		
	}
	
	//new InfoSystem was created successfully
	public void handleInfoSystemReady(String ip,String port){
		NetWorkIS s = new NetWorkIS(ip, port);
		dis.addInfoSystem(s);
	}
	
	public void setIsParentAndRange(NetWorkIS target,String ownport,float[] range){
		SignalTransfer.sendParentAndRange(target, ownport, range);
	}
	
	public void setState(int state){
		this.state = state;
	}

	public static final int SIGNALING_STATE_NOTHING_TO_DO = 0;
	public static final int SIGNALING_STATE_WAITING_FOR_IS = 1;
	public static final int SIGNALING_STATE_WAITING_FOR_DS = 2;
}
