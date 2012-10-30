package rwi.internal.is;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rwi.core.variables.RwiCommunication;

public class SignalingServlet extends HttpServlet{

	private IsSignalingHandler signalhandler;
	
	public SignalingServlet(IsSignalingHandler signalhandler) {
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
		case RwiCommunication.SIGNALING_MODE_SET_PARENT_AND_RANGE:
			ipport = retrieveIpAndPort(req);
			float[] range = retrieveRange(req);
			signalhandler.handleSetParentAndRange(ipport[0], ipport[1], range);	
			resp.getWriter().write(RwiCommunication.READY);
		}
		
	}
	
	private String[] retrieveIpAndPort(HttpServletRequest req){
		String[] ipport = new String[2];
		//ipport[0] = req.getRemoteAddr();
		if(req.getParameter(RwiCommunication.PARAMETER_IPADR) != null && !req.getParameter(RwiCommunication.PARAMETER_IPADR).isEmpty()){
			ipport[0] = req.getParameter(RwiCommunication.PARAMETER_IPADR);
		}else{
			ipport[0] = req.getRemoteAddr();
		}
		if(req.getParameter(RwiCommunication.PARAMETER_PORT) != null && !req.getParameter(RwiCommunication.PARAMETER_PORT).isEmpty()){
			ipport[1] = req.getParameter(RwiCommunication.PARAMETER_PORT);
		}
		return ipport;
	}
	
	private float[] retrieveRange(HttpServletRequest req){
		if(req.getParameter(RwiCommunication.PARAMETER_RANGE) != null && !req.getParameter(RwiCommunication.PARAMETER_RANGE).isEmpty()){
			try{
			String temps = req.getParameter(RwiCommunication.PARAMETER_RANGE);
			String[] tempa = temps.split("-");
			float[] range = new float[]{Float.parseFloat(tempa[0]),Float.parseFloat(tempa[1]),Float.parseFloat(tempa[2]),Float.parseFloat(tempa[3])};
			return range;
			}catch(Exception x){
				return null;
			}
		}else{
			return null;
		}
	}
}
