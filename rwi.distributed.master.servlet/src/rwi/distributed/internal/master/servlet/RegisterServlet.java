package rwi.distributed.internal.master.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rwi.distributed.core.variables.RwiCommunication;
import rwi.distributed.iscontrol.IMasterIs;

public class RegisterServlet extends HttpServlet {

	private IMasterIs isControl;

	public RegisterServlet(IMasterIs isControl) {
		this.isControl = isControl;
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
		
		isControl.unregister(id);
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
		resp.setContentType("text/html;charset=UTF-8");
		resp.getWriter().write("" + isControl.register(type, posX, posY));		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
