package rwi.distributed.internal.initiator;

import javax.servlet.ServletException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import rwi.distributed.core.variables.RwiCommunication;

public class Initiator {

	Bundle dispatcher;
	BundleContext context;
	HttpService http;

	public void createDispatcher() {
		try {
			dispatcher = context.installBundle("file:C:/Users/Tobi/plugins/rwi.distributed.dispatcher_1.0.0.201210172002.jar");
			System.out.println("Dispatcher installed.");
			dispatcher.start();					
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		sendReady();
		System.out.println("Initiator created.");
	}
	
	protected void shutdown(){
		try {
			dispatcher.uninstall();
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendReady(){
		
	}
}
