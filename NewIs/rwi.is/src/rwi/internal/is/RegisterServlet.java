package rwi.internal.is;


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
		
		if (req.getParameter(RwiCommunication.PARAMETER_STATE) != null && !req.getParameter(RwiCommunication.PARAMETER_STATE).isEmpty())
			state= Integer.parseInt(req.getParameter(RwiCommunication.PARAMETER_STATE));
		else state = 0;
		if (req.getParameter(RwiCommunication.PARAMETER_SIZE) != null && !req.getParameter(RwiCommunication.PARAMETER_SIZE).isEmpty()){
			String stemp = req.getParameter(RwiCommunication.PARAMETER_SIZE);
			String[] s = stemp.split("-");
			size = new float[]{Float.parseFloat(s[0]),Float.parseFloat(s[1])};
		}
		
		if (req.getParameter(RwiCommunication.PARAMETER_IPADR) != null && !req.getParameter(RwiCommunication.PARAMETER_IPADR).isEmpty()){
			ip = req.getParameter(RwiCommunication.PARAMETER_IPADR);
			if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){
				ip = req.getRemoteAddr();
			}
		}
		if (req.getParameter(RwiCommunication.PARAMETER_PORT) != null && !req.getParameter(RwiCommunication.PARAMETER_PORT).isEmpty())
			port = req.getParameter(RwiCommunication.PARAMETER_PORT);
						
		if(id>=0 && type >= 0 && size != null && pos !=null && state >=0 && !ip.equals("") && !port.equals(""))
			dis.register(id, type, pos, size, state, ip, port);
		
		//resp.setContentType("text/html;charset=UTF-8");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
