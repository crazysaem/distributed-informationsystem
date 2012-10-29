package rwi.internal.dispatcher;

import java.util.ArrayList;

import rwi.internal.dispatcher.communication.SignalTransfer;

public class ServerManager {

	private ArrayList<FreeServer> available;
	private RootSignalingHandler signaling;
	private boolean rootIsNeeded;
	private ArrayList<Waiting> waitingqueue;

	public void setSignal(RootSignalingHandler signal){
		this.signaling = signal;
	}
	
	public ServerManager(RootSignalingHandler signal) {
		this.available = new ArrayList<>();
		this.waitingqueue = new ArrayList<>();
		this.signaling = signal;
		rootIsNeeded = true;
	}

	public void addFreeServer(String ip, String port) {
		available.add(new FreeServer(ip, port));
		System.out.println("Free server added. IP:" + ip + ", Port:" + port);
		//if some system is waiting for server first handle this
		if (!waitingqueue.isEmpty()) {
			Waiting w = waitingqueue.remove(0);
			if(w.waitingforIS){
				makeInfoSystem(w.ip,w.port,w.range);
			}else{
				makeDispatcher(w.ip, w.port);
			}
		}
	}

	public void getInfoSystem(String ip, String port, float[] range) {
		if (available.isEmpty()) {
			waitingqueue.add(new Waiting(ip, port, true ,range));
		} else {
			makeInfoSystem(ip, port, range);
		}
	}

	private void makeInfoSystem(String ip, String port, float[] range) {
		FreeServer free = available.remove(0);
		boolean created = signaling.sendInfoSystemcreation(free.ip, free.port,range);
		if (created) {
			signaling.sendNewInfoSystem(ip, port, free.ip, free.port, range);
		}
	}

	public void getDispatcher(String ip, String port) {
		if (available.isEmpty()) {
			waitingqueue.add(new Waiting(ip, port, false,null));
		} else {
			makeDispatcher(ip, port);
		}
	}

	public void makeDispatcher(String ip, String port) {
		FreeServer free = available.remove(0);
		boolean created = signaling.sendDispatchercreation(free.ip, free.port);
		if (created) {

		}
	}

	private class FreeServer {
		public String ip;
		public String port;

		public FreeServer(String ip, String port) {
			this.ip = ip;
			this.port = port;
		}
	}

	private class Waiting {
		private String ip;
		private String port;
		private boolean waitingforIS;
		private float[] range;

		public Waiting(String ip, String port, boolean waitForIs,float[] range) {
			this.ip = ip;
			this.port = port;
			this.waitingforIS = waitForIs;
			this.range = range;
		}
	}
}
