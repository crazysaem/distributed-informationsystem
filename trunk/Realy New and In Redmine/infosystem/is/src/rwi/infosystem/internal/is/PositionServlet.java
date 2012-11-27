package rwi.infosystem.internal.is;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rwi.infosystem.core.interfaces.server.ICommunicationHandler;
import rwi.infosystem.core.variables.RwiCommunication;

public class PositionServlet extends HttpServlet{
	
	private ICommunicationHandler dis;
	
	public PositionServlet(ICommunicationHandler dis){
		this.dis = dis;
	}

	//get Routing
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);	
	}
	
	//update Position
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int id = -1;
		float[] pos = null;
		
		//catch parameters
		if (req.getParameter(RwiCommunication.PARAMETER_ID) != null && !req.getParameter(RwiCommunication.PARAMETER_ID).isEmpty())
			id = Integer.parseInt(req.getParameter(RwiCommunication.PARAMETER_ID));
		
		if (req.getParameter(RwiCommunication.PARAMETER_POSITION) != null && !req.getParameter(RwiCommunication.PARAMETER_POSITION).isEmpty()){
			String ptemp = req.getParameter(RwiCommunication.PARAMETER_POSITION);
			String[] t = ptemp.split("-");
			pos = new float[]{Float.parseFloat(t[0]),Float.parseFloat(t[1])};
		}
		
						
		if(id>=0 && pos !=null)
			dis.updatePosition(id, pos);
		
		resp.setContentType("text/html;charset=UTF-8");
		resp.getWriter().write("pos_updated to:"+pos[0]+","+pos[1]);
	}
}
