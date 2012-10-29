package rwi.internal.dispatcher;

import rwi.internal.dispatcher.communication.SignalTransfer;

public class SignalingHandler {
	
	private Dispatcher dis;
	private ServerManager smanager;
		
	public SignalingHandler(Dispatcher dis, ServerManager smanager) {
		this.dis = dis;
		this.smanager = smanager;
	}
	/**
	 * 
	 * @param type 0 = IS, 1 = DISPATCHER
	 * @param ip
	 * @param port
	 */
	public void handleRegistration(int type,String ip,String port){
		NetWorkIS nwis = new NetWorkIS(ip, port, 0, 1000, 0, 1000);
	}
	
	public void handleServerReady(String ip,String port){
		smanager.addFreeServer(ip, port);
	}
	
	public boolean handleDispatchercreation(String ip,String port){
		if(SignalTransfer.initDispatcherCreation(ip, port).equals("created"))
			return true;
		else return false;
	}
}
