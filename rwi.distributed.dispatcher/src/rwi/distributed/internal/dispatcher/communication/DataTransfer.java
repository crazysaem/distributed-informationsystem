package rwi.distributed.internal.dispatcher.communication;

import rwi.distributed.core.variables.RwiCommunication;

public class DataTransfer extends Requester {
	
	public static String forwardRegister(String IPAddress, int id, int type,
			float posX, float posY) {
		// generate the parameter string
		String Parameters = "";
		Parameters += generateParamter(RwiCommunication.PARAMETER_ID, id);
		Parameters += generateParamter(RwiCommunication.PARAMETER_TYPE, type);
		Parameters += generateParamter(RwiCommunication.PARAMETER_POSX, posX);
		Parameters += generateParamter(RwiCommunication.PARAMETER_POSY, posY);

		// send request
		String serveradr = "";
		sendRequest(IPAddress, RwiCommunication.DATA_PORT,
				RwiCommunication.REGISTER_SERVLET, Parameters,
				RwiCommunication.REQUESTMETHOD_POST, serveradr);
		return serveradr;
	}
}
