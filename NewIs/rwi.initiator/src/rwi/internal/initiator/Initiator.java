package rwi.internal.initiator;

import javax.servlet.ServletException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import rwi.core.variables.RwiCommunication;

public class Initiator {

	public String port;
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

	public void createInformationSystem(){
		System.out.println("IS Created");
	}
	
	protected void setHttp(HttpService service) {
		http = service;				
	}

	protected void startup(BundleContext context) {
		this.context = context;
		this.port = context.getProperty("org.osgi.service.http.port");
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
		System.out.println("send ready...");		
		InitSignalTransfer.sendReady("127.0.0.1", port);
	}
}
