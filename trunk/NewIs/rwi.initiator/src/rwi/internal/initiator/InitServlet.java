package rwi.internal.initiator;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rwi.core.variables.RwiCommunication;



public class InitServlet extends HttpServlet{
	
	Initiator i;
	
	public InitServlet(Initiator i){
		this.i= i;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int mode = -1;
		if(req.getParameter(RwiCommunication.PARAMETER_SIGNALING_MODE)!=null && !req.getParameter(RwiCommunication.PARAMETER_SIGNALING_MODE).isEmpty()){
			mode = Integer.parseInt(req.getParameter(RwiCommunication.PARAMETER_SIGNALING_MODE));
		}

		resp.setContentType("text/html;charset=UTF-8");
		
		switch(mode){
		case RwiCommunication.SIGNALING_MODE_INIT_DISP:
			//i.createDispatcher();
			System.out.println("ds created..");
			resp.getWriter().write("created");
			break;
		case RwiCommunication.SIGNALING_MODE_INIT_IS:
			i.createInformationSystem();
			resp.getWriter().write("created");
			break;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
