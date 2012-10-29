package rwi.distributed.internal.dispatcher.communication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rwi.distributed.internal.dispatcher.Dispatcher;

public class PositionServlet extends HttpServlet{
	
	private Dispatcher dis;
	
	public PositionServlet(Dispatcher dis){
		this.dis = dis;
	}

	//get Routing
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String s = dis.getInfo();
		
		resp.setContentType("text/html;charset=UTF-8");
		resp.getWriter().write(s);
	}
	
	//update Position
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
}
