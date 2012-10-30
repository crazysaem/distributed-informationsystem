package rwi.internal.dispatcher;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rwi.core.interfaces.server.ICommunicationHandler;
import rwi.core.variables.RwiCommunication;

public class RegisterServlet extends HttpServlet {

	private ICommunicationHandler dis;

	public RegisterServlet(ICommunicationHandler dis) {
		this.dis = dis;
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id = -1;
		// catch parameters

		if (req.getParameter(RwiCommunication.PARAMETER_ID) != null
				&& !req.getParameter(RwiCommunication.PARAMETER_ID).isEmpty())
			id = Integer.parseInt(req
					.getParameter(RwiCommunication.PARAMETER_ID));
		
		dis.unregister(id);
	}
	
	// register
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int type = -1;
		int id = -1;
		int state = -1;
		float[] pos = null;
		float[] size = null;
		String ip = "";
		String port = "";
		// catch parameters

		if (req.getParameter(RwiCommunication.PARAMETER_ID) != null && !req.getParameter(RwiCommunication.PARAMETER_ID).isEmpty())
			id = Integer.parseInt(req.getParameter(RwiCommunication.PARAMETER_ID));
		
		if (req.getParameter(RwiCommunication.PARAMETER_POSITION) != null && !req.getParameter(RwiCommunication.PARAMETER_POSITION).isEmpty()){
			String ptemp = req.getParameter(RwiCommunication.PARAMETER_POSITION);
			String[] t = ptemp.split("-");
			pos = new float[]{Float.parseFloat(t[0]),Float.parseFloat(t[1])};
		}
		
		if (req.getParameter(RwiCommunication.PARAMETER_TYPE) != null && !req.getParameter(RwiCommunication.PARAMETER_TYPE).isEmpty())
			type = Integer.parseInt(req.getParameter(RwiCommunication.PARAMETER_TYPE));
		
		if (req.getParameter(RwiCommunication.PARAMETER_IPADR) != null && !req.getParameter(RwiCommunication.PARAMETER_IPADR).isEmpty()){
			ip = req.getParameter(RwiCommunication.PARAMETER_IPADR);
		}else{
			ip = req.getRemoteAddr();
			if(ip.equals("0:0:0:0:0:0:0:1")){
				ip = "127.0.0.1";
			}
		}
		if (req.getParameter(RwiCommunication.PARAMETER_PORT) != null && !req.getParameter(RwiCommunication.PARAMETER_PORT).isEmpty())
			port = req.getParameter(RwiCommunication.PARAMETER_PORT);
		else
			port = "0000";
		
		size = new float[]{3,2};
		state = 0;
		
		//if not already registered
		if(id<0){
			resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().write("" + dis.register(type, pos,size, state, ip, port));
		}//if registered
		else{
			dis.register(id, type, pos, size, state, ip, port);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
