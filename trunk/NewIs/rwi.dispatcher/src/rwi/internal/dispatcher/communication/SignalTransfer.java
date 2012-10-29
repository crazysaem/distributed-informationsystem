package rwi.internal.dispatcher.communication;

import rwi.core.classes.Requester;
import rwi.core.variables.RwiCommunication;

public class SignalTransfer extends Requester{

	public static String initDispatcherCreation(String IPAdress, String Port){
		String message = "";
		message += generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_INIT_DISP);
		return sendRequest(IPAdress, Port , RwiCommunication.INIT_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
		
	}
	public static String initDispatcherCreation(String IPAdress, int Port){
		return initDispatcherCreation(IPAdress, Integer.toString(Port));
	}

	public static String initInformationSystemCreation(String IPAdress, String Port,float[] range){
		String message = "";
		message += generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_INIT_IS);
		message += generateParamter(RwiCommunication.PARAMETER_RANGE, range[0]+"-"+range[1]+"-"+range[2]+"-"+range[3]);
		return sendRequest(IPAdress, Port , RwiCommunication.INIT_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	}
	
	public static String initInformationSystemCreation(String IPAdress, int Port,float[] range){
		return initInformationSystemCreation(IPAdress, Integer.toString(Port), range);
	}
	
	public static void forwardUnregister(String ip,String port,int id){
		String message = generateParamter(RwiCommunication.PARAMETER_ID, id);
		message+= generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_UNREGISTER);
		sendRequest(ip, port , RwiCommunication.SIGNALING_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	}
	
	public static void askForInfoSystem(String myport,float[] range){
		String message = generateParamter(RwiCommunication.PARAMETER_PORT, myport);
		message+= generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_ASK_FOR_IS);
		message += generateParamter(RwiCommunication.PARAMETER_RANGE, range[0]+"-"+range[1]+"-"+range[2]+"-"+range[3]);
		sendRequest(RwiCommunication.ROOT_ADDRESS, RwiCommunication.ROOT_PORT, RwiCommunication.SIGNALING_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	}
	
	public static void sendNewInfoSystem(String ip,String port,String isip,String isport,float[] range){
		String message = generateParamter(RwiCommunication.PARAMETER_IPADR, isip);
		message+=generateParamter(RwiCommunication.PARAMETER_PORT, isport);
		message += generateParamter(RwiCommunication.PARAMETER_RANGE, range[0]+"-"+range[1]+"-"+range[2]+"-"+range[3]);
		message+=generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_IS_READY);
		sendRequest(ip, port, RwiCommunication.SIGNALING_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	}
}
