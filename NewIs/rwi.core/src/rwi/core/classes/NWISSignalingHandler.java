package rwi.core.classes;

import rwi.core.variables.RwiCommunication;

public class NWISSignalingHandler extends Requester {

	public static void updateRange(NetWorkIS nwis,float[] newRange){
		String message = generateParamter(RwiCommunication.PARAMETER_RANGE, newRange);
		message += generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_UPDATE_RANGE);
		sendRequest(nwis.getIp(), nwis.getPort(), RwiCommunication.SIGNALING_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	} 
}
