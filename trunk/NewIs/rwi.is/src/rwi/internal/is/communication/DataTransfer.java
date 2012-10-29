package rwi.internal.is.communication;

import rwi.core.classes.Requester;
import rwi.core.variables.RwiCommunication;

public class DataTransfer extends Requester {
	
	public static void forwardRegister(String IPAddress, String Port, int id, int type,
			float[] pos,float[]size,int state,String clientIp,String clientPort) {
		// generate the parameter string
		String Parameters = "";
		Parameters += generateParamter(RwiCommunication.PARAMETER_ID, id);
		Parameters += generateParamter(RwiCommunication.PARAMETER_TYPE, type);
		Parameters += generateParamter(RwiCommunication.PARAMETER_STATE, state);
		Parameters += generateParamter(RwiCommunication.PARAMETER_POSX, pos[0]);
		Parameters += generateParamter(RwiCommunication.PARAMETER_POSY, pos[1]);
		Parameters += generateParamter(RwiCommunication.PARAMETER_SIZEX, size[0]);
		Parameters += generateParamter(RwiCommunication.PARAMETER_SIZEY, size[1]);
		Parameters += generateParamter(RwiCommunication.PARAMETER_IPADR, clientIp);
		Parameters += generateParamter(RwiCommunication.PARAMETER_PORT, clientPort);

		// send request
		sendRequest(IPAddress, Port,
				RwiCommunication.REGISTER_SERVLET, Parameters,
				RwiCommunication.REQUESTMETHOD_POST);
	}
	
	public static void forwardUpdatePos(String IPAddress,String Port,int id,float[] pos){
		String Parameters = "";
		Parameters += generateParamter(RwiCommunication.PARAMETER_ID, id);
		Parameters += generateParamter(RwiCommunication.PARAMETER_POSX, pos[0]);
		Parameters += generateParamter(RwiCommunication.PARAMETER_POSY, pos[1]);
		
		// send request
		sendRequest(IPAddress, Port,
				RwiCommunication.POSITION_SERVLET, Parameters,
				RwiCommunication.REQUESTMETHOD_POST);
	}
}
