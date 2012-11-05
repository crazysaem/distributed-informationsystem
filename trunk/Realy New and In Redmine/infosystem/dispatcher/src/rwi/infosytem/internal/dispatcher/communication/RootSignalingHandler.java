package rwi.infosytem.internal.dispatcher.communication;

import rwi.infosystem.core.classes.NetWorkIS;
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
	
	public boolean sendDispatchercreation(String ip,String port){
		if(SignalTransfer.initDispatcherCreation(ip, port).equals("created"))
			return true;
		else return false;
	}
		
	public boolean sendInfoSystemcreation(String ip,String port){
		if(SignalTransfer.initInformationSystemCreation(ip, port).equals("created"))
			return true;
		else return false;
	}

	public void sendNewInfoSystem(String ip,String port,String isip,String isport){
		if(ip.equals("root")&&port.equals("root")){
			handleInfoSystemReady(new NetWorkIS(isip, isport));
		}else{
			SignalTransfer.sendNewInfoSystem(ip, port, isip, isport);
		}
	}
}
