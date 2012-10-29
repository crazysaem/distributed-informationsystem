package rwi.internal.dispatcher;

import rwi.internal.dispatcher.communication.SignalTransfer;

public class RootSignalingHandler extends SignalingHandler{


	protected ServerManager smanager;
	
	public RootSignalingHandler(Dispatcher dis, ServerManager smanager) {
		super(dis);
		this.smanager=smanager;
	}
	
	public void handleServerReady(String ip,String port){
		smanager.addFreeServer(ip, port);
	}
	
	public void handleAskForIs(String ip,String port,float[] range){
		smanager.getInfoSystem(ip, port,range);
	}
	
	public boolean sendDispatchercreation(String ip,String port){
		if(SignalTransfer.initDispatcherCreation(ip, port).equals("created"))
			return true;
		else return false;
	}
		
	public boolean sendInfoSystemcreation(String ip,String port,float[] range){
		if(SignalTransfer.initInformationSystemCreation(ip, port, range).equals("created"))
			return true;
		else return false;
	}

	public void sendNewInfoSystem(String ip,String port,String isip,String isport,float[] range){
		SignalTransfer.sendNewInfoSystem(ip, port, isip, isport,range);
	}
}
