package rwi.distributed.internal.initiator;

import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import rwi.distributed.core.variables.RwiCommunication;

public class Initiator {

	BundleContext context;
	HttpService http;

	public void createDispatcher() {
		System.out.println("Generated");
	}

	protected void setHttp(HttpService service) {
		http = service;
	}

	protected void startup(BundleContext context) {
		this.context = context;
		InitServlet servlet = new InitServlet(this);
		String address = RwiCommunication.INIT_SERVLET;

		try {
			http.registerServlet(address, servlet, null, null);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamespaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("initiated...");
	}
}
