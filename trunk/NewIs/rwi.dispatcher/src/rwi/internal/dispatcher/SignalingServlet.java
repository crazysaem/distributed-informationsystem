package rwi.internal.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rwi.core.variables.RwiCommunication;

public class SignalingServlet extends HttpServlet{

	private SignalingHandler signalhandler;	
	
	public SignalingServlet(SignalingHandler signalhandler) {
		super();
		this.signalhandler = signalhandler;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int mode = -1;
		
		if(req.getParameter(RwiCommunication.PARAMETER_SIGNALING_MODE) != null && !req.getParameter(RwiCommunication.PARAMETER_SIGNALING_MODE).isEmpty()){
			mode = Integer.parseInt(req.getParameter(RwiCommunication.PARAMETER_SIGNALING_MODE));
		}
		String[] ipport;
		switch(mode){	
		//A free server is ready to be used
		case RwiCommunication.SIGNALING_MODE_SERVERREADY:
			ipport = retrieveIpAndPort(req);
			((RootSignalingHandler)signalhandler).handleServerReady(ipport[0],ipport[1]);
			break;
		//An object was unregistered. The ID needs to be removed from all instances.
		case RwiCommunication.SIGNALING_MODE_UNREGISTER:
			int id = -1;
			if(req.getParameter(RwiCommunication.PARAMETER_ID) != null && !req.getParameter(RwiCommunication.PARAMETER_ID).isEmpty()){
				id = Integer.parseInt(req.getParameter(RwiCommunication.PARAMETER_ID));
			}
			if(id>=0){
				signalhandler.handleUnregister(id);
			}
		//Someone asks for creation of an IS
		case RwiCommunication.SIGNALING_MODE_ASK_FOR_IS:
			if(req.getParameter(RwiCommunication.PARAMETER_PORT) != null && !req.getParameter(RwiCommunication.PARAMETER_PORT).isEmpty()){
				String port = req.getParameter(RwiCommunication.PARAMETER_PORT);
				String ip = req.getRemoteAddr();
				if(req.getParameter(RwiCommunication.PARAMETER_RANGE) != null && !req.getParameter(RwiCommunication.PARAMETER_RANGE).isEmpty()){
					String temps = req.getParameter(RwiCommunication.PARAMETER_RANGE);
					String[] tempa = temps.split("-");
					float[] range = new float[]{Float.parseFloat(tempa[0]),Float.parseFloat(tempa[1]),Float.parseFloat(tempa[2]),Float.parseFloat(tempa[3])};
					((RootSignalingHandler)signalhandler).handleAskForIs(ip, port,range);
				}
			}
			break;
		//InfoSystem was created at following IP and PORT
		case RwiCommunication.SIGNALING_MODE_IS_READY:
			ipport = retrieveIpAndPort(req);
			if(req.getParameter(RwiCommunication.PARAMETER_RANGE) != null && !req.getParameter(RwiCommunication.PARAMETER_RANGE).isEmpty()){
				String temps = req.getParameter(RwiCommunication.PARAMETER_RANGE);
				String[] tempa = temps.split("-");
				float[] range = new float[]{Float.parseFloat(tempa[0]),Float.parseFloat(tempa[1]),Float.parseFloat(tempa[2]),Float.parseFloat(tempa[3])};
				signalhandler.handleInfoSystemReady(ipport[0], ipport[1],range);
			}
			break;
		}	
	}
	
	private String[] retrieveIpAndPort(HttpServletRequest req){
		String[] ipport = new String[2];
		if(req.getParameter(RwiCommunication.PARAMETER_IPADR) != null && !req.getParameter(RwiCommunication.PARAMETER_IPADR).isEmpty()){
			ipport[0] = req.getParameter(RwiCommunication.PARAMETER_IPADR);
		}
		if(req.getParameter(RwiCommunication.PARAMETER_PORT) != null && !req.getParameter(RwiCommunication.PARAMETER_PORT).isEmpty()){
			ipport[1] = req.getParameter(RwiCommunication.PARAMETER_PORT);
		}
		return ipport;
	}
}
