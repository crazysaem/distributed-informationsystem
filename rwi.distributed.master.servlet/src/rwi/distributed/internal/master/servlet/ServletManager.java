package rwi.distributed.internal.master.servlet;

import org.eclipse.equinox.http.HttpService;

import rwi.distributed.core.variables.RwiCommunication;

public class ServletManager {

	private HttpService http;

	protected void setHttp(HttpService value) {
		this.http = value;
	}

	protected void startup() {
		String address = RwiCommunication.REGISTER_SERVLET;
		RegisterServlet reg_servlet = new RegisterServlet(null);
		reg_servlet.init();

		http.registerServlet(address, reg_servlet, null, null);
		}
}
