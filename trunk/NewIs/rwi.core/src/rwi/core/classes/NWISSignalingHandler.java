package rwi.core.classes;

import rwi.core.variables.RwiCommunication;
/**
 * handle means an incoming request
 * send or forward means creating a new request
 * @author Mr.N0I3oDy
 *
 */
public class NWISSignalingHandler extends Requester {

	public static void updateRange(NetWorkIS nwis,float[] newRange){
		String message = generateParamter(RwiCommunication.PARAMETER_RANGE, newRange);
		message += generateParamter(RwiCommunication.PARAMETER_SIGNALING_MODE, RwiCommunication.SIGNALING_MODE_UPDATE_RANGE);
		sendRequest(nwis.getIp(), nwis.getPort(), RwiCommunication.SIGNALING_SERVLET, message, RwiCommunication.REQUESTMETHOD_POST);
	} 
}
