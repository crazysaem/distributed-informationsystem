package rwi.distributed.internal.dispatcher.communication;

import rwi.distributed.core.variables.RwiCommunication;

public class SignalTransfer extends Requester{

	public static void initDispatcherCreation(String IPAddress){
		sendRequest(IPAddress, RwiCommunication.SIGNALING_PORT, RwiCommunication.INIT_SERVLET, "", RwiCommunication.REQUESTMETHOD_POST, null);
	}
}
