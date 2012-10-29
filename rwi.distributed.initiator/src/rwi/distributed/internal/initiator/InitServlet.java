package rwi.distributed.internal.initiator;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.Region;



public class InitServlet extends HttpServlet{
	
	Initiator i;
	
	public InitServlet(Initiator i){
		this.i= i;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//i.createDispatcher();
		System.out.println(req.getRemoteHost());
		System.out.println(req.getRemoteAddr());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
