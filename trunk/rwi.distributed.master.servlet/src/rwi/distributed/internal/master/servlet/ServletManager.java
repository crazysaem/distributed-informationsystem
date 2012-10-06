package rwi.distributed.internal.master.servlet;

import javax.servlet.ServletException;

import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import rwi.distributed.core.variables.RwiCommunication;
import rwi.distributed.iscontrol.IMasterIs;

public class ServletManager {

	private HttpService http;
	private IMasterIs mastercontrol;

	protected void setHttp(HttpService value) {
		this.http = value;
	}

	protected void setMaster(IMasterIs value) {
		this.mastercontrol = value;
	}
	
	protected void startup() {
		initServlets(mastercontrol);
	}

	private void initServlets(IMasterIs master) {
		RegisterServlet reg_servlet = new RegisterServlet(master);
		PositionServlet pos_servlet = new PositionServlet(master);
		try {
			// register Registration-Servlet
			reg_servlet.init();
			String address = RwiCommunication.REGISTER_SERVLET;
			http.registerServlet(address, reg_servlet, null, null);
			// register Position-Servlet
			pos_servlet.init();
			address = RwiCommunication.POSITION_SERVLET;
			http.registerServlet(address, pos_servlet, null, null);
		} catch (ServletException | NamespaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
