package rwi.internal.initiator;

import rwi.infosystem.core.classes.Requester;
import rwi.infosystem.core.variables.RwiCommunication;

public class InitSignalTransfer extends Requester{

	public static void sendReady(String ownip,String ownport){
		String message = "";
		message += generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_SERVERREADY);
		message += generateParamter(RwiCommunication.PARAMETER_IPADR, ownip);
		message += generateParamter(RwiCommunication.PARAMETER_PORT, ownport);
		sendRequest(RwiCommunication.ROOT_ADDRESS, RwiCommunication.ROOT_PORT, RwiCommunication.SIGNALING_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	}
	
//	public static void sendInfoSystemReady(String ownport){
//		String message = generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_IS_READY);
//		message += generateParamter(RwiCommunication.PARAMETER_PORT, ownport);
//		sendRequest(RwiCommunication.ROOT_ADDRESS, RwiCommunication.ROOT_PORT, RwiCommunication.SIGNALING_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
//	}
}
