package rwi.internal.dispatcher;

import java.util.ArrayList;

public class ServerManager {
	
	private ArrayList<FreeServer> available;
	private SignalingHandler signaling;
	
	public ServerManager(SignalingHandler signal) {
		this.available = new ArrayList<>();
		this.signaling = signal;	
	}

	public void addFreeServer(String ip,String port){
		available.add(new FreeServer(ip, port));
		System.out.println("Free server added. IP:"+ip+", Port:"+port);
	}

	public String[] retrieveDispatcher(){
		FreeServer free = available.remove(0);
		boolean created = signaling.sendDispatchercreation(free.ip, free.port);
		if(created){
			return new String[]{free.ip,free.port};
		}else{
			return null;
		}
	}
	private class FreeServer{
		public String ip;
		public String port;
		
		public FreeServer(String ip, String port) {
			this.ip = ip;
			this.port = port;
		}		
	}
}
