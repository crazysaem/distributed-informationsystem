package rwi.distributed.internal.master.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rwi.distributed.core.variables.RwiCommunication;
import rwi.distributed.iscontrol.IMasterIs;

public class RegisterServlet extends HttpServlet{
	
	private IMasterIs isControl;
	
	public RegisterServlet(IMasterIs isControl){
		this.isControl = isControl;
	}

	//unregister
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doDelete(req, resp);
	}
	
	//register
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int type = -1;
		float posX = -1;
		float posY = -1;
		//catch parameters
		if(req.getParameter(RwiCommunication.PARAMETER_POSX)!=null && !req.getParameter(RwiCommunication.PARAMETER_POSX).isEmpty())
			posX = Float.parseFloat(req.getParameter(RwiCommunication.PARAMETER_POSX));
		if(req.getParameter(RwiCommunication.PARAMETER_POSY)!=null && !req.getParameter(RwiCommunication.PARAMETER_POSY).isEmpty())
			posY = Float.parseFloat(req.getParameter(RwiCommunication.PARAMETER_POSY));
		if(req.getParameter(RwiCommunication.PARAMETER_TYPE)!=null && !req.getParameter(RwiCommunication.PARAMETER_TYPE).isEmpty())
			type = Integer.parseInt(req.getParameter(RwiCommunication.PARAMETER_TYPE));
		isControl.register(type, posX, posY);
		super.doPost(req, resp);
	}
}
