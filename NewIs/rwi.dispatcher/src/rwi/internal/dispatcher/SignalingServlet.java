package rwi.internal.dispatcher;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rwi.core.variables.RwiCommunication;

public class SignalingServlet extends HttpServlet{

	private DispatchSignalingHandler signalhandler;	
	private ExecutorService exservice;
	
	public SignalingServlet(DispatchSignalingHandler signalhandler) {
		super();
		exservice = Executors.newFixedThreadPool(1);
		this.signalhandler = signalhandler;
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int mode = -1;
		
		if(req.getParameter(RwiCommunication.PARAMETER_SIGNALING_MODE) != null && !req.getParameter(RwiCommunication.PARAMETER_SIGNALING_MODE).isEmpty()){
			mode = Integer.parseInt(req.getParameter(RwiCommunication.PARAMETER_SIGNALING_MODE));
		}
		String[] ipport;
		switch(mode){	
		//A free server is ready to be used
		case RwiCommunication.SIGNALING_MODE_SERVERREADY:
			ipport = retrieveIpAndPort(req);
			//((RootSignalingHandler)signalhandler).handleServerReady(ipport[0],ipport[1]);
			exservice.execute(new Task(ipport[0], ipport[1], mode));
			break;
		//An object was unregistered. The ID needs to be removed from all instances.
		case RwiCommunication.SIGNALING_MODE_UNREGISTER:
			int id = -1;
			if(req.getParameter(RwiCommunication.PARAMETER_ID) != null && !req.getParameter(RwiCommunication.PARAMETER_ID).isEmpty()){
				id = Integer.parseInt(req.getParameter(RwiCommunication.PARAMETER_ID));
			}
			if(id>=0){
				//signalhandler.handleUnregister(id);
				exservice.execute(new Task(id, mode));
			}
		//Someone asks for creation of an IS
		case RwiCommunication.SIGNALING_MODE_ASK_FOR_IS:
			if(req.getParameter(RwiCommunication.PARAMETER_PORT) != null && !req.getParameter(RwiCommunication.PARAMETER_PORT).isEmpty()){
				String port = req.getParameter(RwiCommunication.PARAMETER_PORT);
				String ip = req.getRemoteAddr();				
				//((RootSignalingHandler)signalhandler).handleAskForIs(ip, port,range);
				exservice.execute(new Task(ip, port, mode));				
			}
			break;
		//InfoSystem was created at following IP and PORT
		case RwiCommunication.SIGNALING_MODE_IS_READY:
			ipport = retrieveIpAndPort(req);			
			//signalhandler.handleInfoSystemReady(ipport[0], ipport[1],range);
			exservice.execute(new Task(req.getRemoteAddr(), ipport[1], mode));			
			break;
		}	
	}
	
	private String[] retrieveIpAndPort(HttpServletRequest req){
		String[] ipport = new String[2];
		if(req.getParameter(RwiCommunication.PARAMETER_IPADR) != null && !req.getParameter(RwiCommunication.PARAMETER_IPADR).isEmpty()){
			ipport[0] = req.getParameter(RwiCommunication.PARAMETER_IPADR);
		}
		if(req.getParameter(RwiCommunication.PARAMETER_PORT) != null && !req.getParameter(RwiCommunication.PARAMETER_PORT).isEmpty()){
			ipport[1] = req.getParameter(RwiCommunication.PARAMETER_PORT);
		}
		return ipport;
	}
	
	private class Task implements Runnable{

		private String ip,port;
		private float[] range;
		private int id,mode;
		
		Task(int id,int mode){
			this.id = id;
			this.mode = mode;
		}
		
		Task(String ip,String port,int mode){
			this.ip = ip;
			this.port = port;
			this.mode = mode;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(mode==RwiCommunication.SIGNALING_MODE_IS_READY){
				signalhandler.handleInfoSystemReady(ip, port);
			}else if(mode==RwiCommunication.SIGNALING_MODE_ASK_FOR_IS){
				((RootSignalingHandler)signalhandler).handleAskForIs(ip, port);
			}else if(mode==RwiCommunication.SIGNALING_MODE_UNREGISTER){
				signalhandler.handleUnregister(id);
			}else if(mode==RwiCommunication.SIGNALING_MODE_SERVERREADY){
				((RootSignalingHandler)signalhandler).handleServerReady(ip,port);
			}
		}
		
	}
	
	
}
