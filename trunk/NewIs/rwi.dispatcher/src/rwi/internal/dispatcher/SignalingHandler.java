package rwi.internal.dispatcher;

import rwi.core.classes.NetWorkIS;
import rwi.internal.dispatcher.communication.SignalTransfer;

public class SignalingHandler {
	
	private Dispatcher dis;
	private ServerManager smanager;
		
	public SignalingHandler(Dispatcher dis, ServerManager smanager) {
		this.dis = dis;
		this.smanager = smanager;
	}
		
	public void handleServerReady(String ip,String port){
		smanager.addFreeServer(ip, port);
	}
	
	public void handleUnregister(int id){
		dis.unregister(id);
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
	
	public void forwarUnregister(String ip,String port,int id){
		SignalTransfer.forwardUnregister(ip, port, id);
	}
}
