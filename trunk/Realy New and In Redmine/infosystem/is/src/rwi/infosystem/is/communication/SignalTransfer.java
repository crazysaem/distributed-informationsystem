package rwi.infosystem.is.communication;

import rwi.infosystem.core.classes.Requester;
import rwi.infosystem.core.variables.RwiCommunication;

public class SignalTransfer extends Requester{

	public static String initDispatcherCreation(String IPAdress, int Port){
		String message = "";
		message += generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_INIT_DISP);
		return sendRequest(IPAdress, Integer.toString(Port) , RwiCommunication.INIT_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
		
	}
	public static String initDispatcherCreation(String IPAdress, String Port){
		return initDispatcherCreation(IPAdress, Integer.parseInt(Port));
	}

	public static String initInformationSystemCreation(String IPAdress, int Port){
		String message = "";
		message += generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_INIT_IS);
		return sendRequest(IPAdress, Integer.toString(Port) , RwiCommunication.INIT_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	}
	public static String initInformationSystemCreation(String IPAdress, String Port){
		return initDispatcherCreation(IPAdress, Integer.parseInt(Port));
	}
}
