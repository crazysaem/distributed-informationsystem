package rwi.infosystem.is.communication;

import rwi.infosystem.core.classes.NetWorkIS;
import rwi.infosystem.core.classes.Requester;
import rwi.infosystem.core.variables.RwiCommunication;

public class SignalTransfer extends Requester{

	public static void askForSplit(String ownport,int state,NetWorkIS parent){
		String message = generateParamter(RwiCommunication.PARAMETER_PORT, ownport);
		message += generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_SPLIT_REQUEST);
		message += generateParamter(RwiCommunication.PARAMETER_SPLIT_TYPE, state);
		sendRequest(parent.getIp(), parent.getPort(), RwiCommunication.SIGNALING_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	}
	
	public static void sendDeleteRequest(int id,NetWorkIS parent){
		String message = generateParamter(RwiCommunication.PARAMETER_ID, id);
		message = generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_UNREGISTER);
		Requester.sendRequest(parent.getIp(), parent.getPort(), RwiCommunication.REGISTER_SERVLET, message, RwiCommunication.REQUESTMETHOD_DELETE);
	}
}
