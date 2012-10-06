package rwi.distributed.internal.master.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rwi.distributed.core.variables.RwiCommunication;
import rwi.distributed.iscontrol.IMasterIs;

public class UnregisterServlet extends HttpServlet {

	private IMasterIs isControl;

	public UnregisterServlet(IMasterIs isControl) {
		this.isControl = isControl;
	}

	// register
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id = -1;
		
		// catch parameters
		if (req.getParameter(RwiCommunication.PARAMETER_ID) != null
				&& !req.getParameter(RwiCommunication.PARAMETER_ID).isEmpty())
			id = Integer.parseInt(req
					.getParameter(RwiCommunication.PARAMETER_ID));
		isControl.unregister(id);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
