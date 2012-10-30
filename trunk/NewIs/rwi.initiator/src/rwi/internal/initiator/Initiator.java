package rwi.internal.initiator;

import java.util.concurrent.Executors;

import javax.servlet.ServletException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import rwi.core.variables.RwiCommunication;

public class Initiator implements BundleActivator {

	public String port;
	Bundle itself;
	Bundle bundle;
	BundleContext context;
	HttpService http;

	public void createDispatcher() {
		try {
			bundle = context.getBundle(21);
			if(bundle==null)
				bundle = context.installBundle("file:C:/Users/Mr.N0I3oDy/workspace/InfoSystem/rwi.dispatcher");
			System.out.println("Dispatcher installed.");
			bundle.start();					
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createInformationSystem(){		
		try {
			bundle = context.getBundle(22);
			if(bundle==null)
				bundle = context.installBundle("file:C:/Users/Mr.N0I3oDy/workspace/InfoSystem/rwi.is");
			bundle.start();
			System.out.println("InfoSystem installed.");
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	protected void setHttp(HttpService service) {
		http = service;	
		startup();
	}

	protected void startup() {
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
			bundle.uninstall();
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendReady(){
		System.out.println("send ready...");		
		InitSignalTransfer.sendReady("127.0.0.1", port);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		Executors.newFixedThreadPool(1).execute(new FindServiceTask(context));	
		this.context = context;
		this.port = context.getProperty("org.osgi.service.http.port");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	private class FindServiceTask implements Runnable{

		public FindServiceTask(BundleContext context){
			this.context =  context;
		}
		private BundleContext context;
		private boolean found = false;
		
		@Override
		public void run() {
			while(!found){
				ServiceReference<HttpService> servref = context.getServiceReference(HttpService.class);
				if(servref!=null){
					setHttp(context.getService(servref));
					//context.ungetService(servref);
					found = true;
				}else{
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}
}
