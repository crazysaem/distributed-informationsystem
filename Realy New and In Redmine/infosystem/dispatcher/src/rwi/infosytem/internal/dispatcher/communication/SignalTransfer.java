package rwi.infosytem.internal.dispatcher.communication;

import rwi.infosystem.core.classes.NetWorkIS;
import rwi.infosystem.core.classes.Requester;
import rwi.infosystem.core.variables.RwiCommunication;

public class SignalTransfer extends Requester{

	public static String initDispatcherCreation(String IPAdress, String Port){
		String message = "";
		message += generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_INIT_DISP);
		return sendRequest(IPAdress, Port , RwiCommunication.INIT_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
		
	}
	public static String initDispatcherCreation(String IPAdress, int Port){
		return initDispatcherCreation(IPAdress, Integer.toString(Port));
	}

	public static String initInformationSystemCreation(String IPAdress, String Port){
		String message = "";
		message += generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_INIT_IS);
		return sendRequest(IPAdress, Port , RwiCommunication.INIT_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	}
	
	public static String initInformationSystemCreation(String IPAdress, int Port){
		return initInformationSystemCreation(IPAdress, Integer.toString(Port));
	}
	
	public static void forwardUnregister(String ip,String port,int id){
		String message = generateParamter(RwiCommunication.PARAMETER_ID, id);
		message+= generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_UNREGISTER);
		sendRequest(ip, port , RwiCommunication.SIGNALING_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	}
	
	public static void askForInfoSystem(String myport){
		String message = generateParamter(RwiCommunication.PARAMETER_PORT, myport);
		message+= generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_ASK_FOR_IS);
		sendRequest(RwiCommunication.ROOT_ADDRESS, RwiCommunication.ROOT_PORT, RwiCommunication.SIGNALING_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	}
	
	public static void sendNewInfoSystem(String ip,String port,String isip,String isport){
		String message = generateParamter(RwiCommunication.PARAMETER_IPADR, isip);
		message+=generateParamter(RwiCommunication.PARAMETER_PORT, isport);
		message+=generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_IS_READY);
		sendRequest(ip, port, RwiCommunication.SIGNALING_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	}
	
	public static String sendParentAndRange(NetWorkIS target, String ownport,float[] range){
		String message =generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_SET_PARENT_AND_RANGE);
		message+=generateParamter(RwiCommunication.PARAMETER_PORT, ownport);
		message+=generateParamter(RwiCommunication.PARAMETER_RANGE, range);
		return sendRequest(target.getIp(), target.getPort(), RwiCommunication.SIGNALING_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	}
}
