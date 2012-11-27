package rwi.infosystem.internal.initiator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import rwi.infosystem.core.variables.RwiCommunication;

public class Initiator {

	public String port;
	private Bundle itself;
	private Bundle bundle;
	private BundleContext context;
	private HttpService http;
	
	private Process process;
	
	public void createDispatcher() {
		try {
			bundle = context.getBundle("file:C:/Users/Mr.N0I3oDy/workspace/Plugins/rwi.dispatcher_1.0.0.201210301621.jar");
			if(bundle==null)
				bundle = context.installBundle("file:C:/Users/Mr.N0I3oDy/workspace/Plugins/rwi.dispatcher_1.0.0.201210301621.jar");
			System.out.println("Dispatcher installed.");
			bundle.start();					
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String createInformationSystem(){		
//		try {
//			bundle = context.getBundle("file:C:/plugins/rwi.infosystem.is_1.0.0.201210172000.jar");
//			if(bundle==null)
//				bundle = context.installBundle("file:C:/plugins/rwi.infosystem.is_1.0.0.201210172000.jar");
//			System.out.println("Infosystem installed.");
//			bundle.start();	
//		} catch (BundleException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return port;
		
		try {
			System.out.println(port);
			int x = Integer.parseInt(port.substring(3,4))+1;
			String newport = port.substring(0, 3)+x;			
			String target = "C:\\plugins\\infosystem.zip";
			Unzip.saveUrl(target, "https://dl.dropbox.com/s/7c6a85dfapqn0b9/infosystem.zip?dl=1");
		    
			Unzip.unrar(target);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Unzip.replaceInFile("C:\\plugins\\infosystem.ini",newport);
			
			process = Runtime.getRuntime().exec("C:\\plugins\\infosystem.exe");
			new Thread(new SyncPipe(process.getErrorStream(), System.err)).start();
		    new Thread(new SyncPipe(process.getInputStream(), System.out)).start();
		    
			System.out.println("InfoSystem installed.");

			return newport;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
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
		if(process!=null){
			PrintWriter stdin = new PrintWriter(process.getOutputStream());
			stdin.println("exit");
			stdin.close();
		}
	}
	
	private void sendReady(){
		System.out.println("send ready...");		
		InitSignalTransfer.sendReady(port);
	}
	
	private class SyncPipe implements Runnable
	{
	public SyncPipe(InputStream istrm, OutputStream ostrm) {
	      istrm_ = istrm;
	      ostrm_ = ostrm;
	  }
	  public void run() {
	      try
	      {
	          final byte[] buffer = new byte[1024];
	          for (int length = 0; (length = istrm_.read(buffer)) != -1; )
	          {
	              ostrm_.write(buffer, 0, length);
	          }
	      }
	      catch (Exception e)
	      {
	          e.printStackTrace();
	      }
	  }
	  private final OutputStream ostrm_;
	  private final InputStream istrm_;
	}
}
