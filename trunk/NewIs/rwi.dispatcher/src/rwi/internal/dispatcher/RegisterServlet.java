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
		float posX = -1;
		float posY = -1;
		// catch parameters

		if (req.getParameter(RwiCommunication.PARAMETER_POSX) != null
				&& !req.getParameter(RwiCommunication.PARAMETER_POSX).isEmpty())
			posX = Float.parseFloat(req
					.getParameter(RwiCommunication.PARAMETER_POSX));
		if (req.getParameter(RwiCommunication.PARAMETER_POSY) != null
				&& !req.getParameter(RwiCommunication.PARAMETER_POSY).isEmpty())
			posY = Float.parseFloat(req
					.getParameter(RwiCommunication.PARAMETER_POSY));
		if (req.getParameter(RwiCommunication.PARAMETER_TYPE) != null
				&& !req.getParameter(RwiCommunication.PARAMETER_TYPE).isEmpty())
			type = Integer.parseInt(req
					.getParameter(RwiCommunication.PARAMETER_TYPE));
		
		float[] size = {3,2};
		int state = 0;
		String ipaddress = req.getRemoteAddr();
		String port = "8080";
		
		resp.setContentType("text/html;charset=UTF-8");
		resp.getWriter().write("" + dis.register(type, new float[]{posX, posY},size, state, ipaddress, port));		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
