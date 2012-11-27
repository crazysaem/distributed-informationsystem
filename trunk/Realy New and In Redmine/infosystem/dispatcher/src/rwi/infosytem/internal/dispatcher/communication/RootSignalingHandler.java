package rwi.infosytem.internal.dispatcher.communication;

import rwi.infosystem.core.classes.NetWorkIS;
import rwi.infosystem.core.variables.RwiCommunication;
import rwi.infosystem.internal.dispatcher.Dispatcher;
import rwi.infosystem.internal.dispatcher.ServerManager;
/**
 * handle means an incoming request
 * send or forward means creating a new request
 * @author Mr.N0I3oDy
 *
 */
public class RootSignalingHandler extends DispatchSignalingHandler{


	protected ServerManager smanager;
	
	@Override
	public void askForInfoSystem(String port){
		this.state = SIGNALING_STATE_WAITING_FOR_IS;		
		smanager.getInfoSystem();
	}
	
	public RootSignalingHandler(Dispatcher dis, ServerManager smanager) {
		super(dis);
		this.smanager=smanager;
	}
	
	public void handleServerReady(String ip,String port){
		smanager.addFreeServer(ip, port);
	}
	
	public void handleAskForIs(String ip,String port){
		smanager.getInfoSystem(ip, port);
	}
	
	@Override
	public void handleSplitRequest(String ip, String port, int type){
		if(type == RwiCommunication.SPLIT_TYPE_ONE || type == RwiCommunication.SPLIT_TYPE_TWO){
			splittemp = new NetWorkIS(ip, port);
			modetemp = type;
			splitstate = SIGNALING_STATE_WAITING_FOR_IS;
			smanager.getInfoSystem();	
		}else if(type == RwiCommunication.SPLIT_TYPE_FOUR){
			//TODO
		}
	}
	
	public boolean sendDispatchercreation(String ip,String port){
		if(SignalTransfer.initDispatcherCreation(ip, port).equals("created"))
			return true;
		else return false;
	}
		
	public String sendInfoSystemcreation(String ip,String port){
		String newport = SignalTransfer.initInformationSystemCreation(ip, port);
		return newport;
	}

	public void sendNewInfoSystem(String ip,String port,String isip,String isport){
		if((ip.equals("root")&&port.equals("root")) || (ip.equals("127.0.0.1") && port.equals("8080"))){
			handleInfoSystemReady(new NetWorkIS(isip, isport));
		}else{
			SignalTransfer.sendNewInfoSystem(ip, port, isip, isport);
		}
	}	
}
