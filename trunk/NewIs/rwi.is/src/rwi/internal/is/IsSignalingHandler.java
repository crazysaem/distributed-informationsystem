package rwi.internal.is;

import rwi.core.classes.NetWorkIS;
import rwi.core.interfaces.server.ICommunicationHandler;
import rwi.internal.is.communication.SignalTransfer;

public class IsSignalingHandler {
	
	private InformationSystem is;
		
	public IsSignalingHandler(InformationSystem is) {
		this.is = is;
	}
	
	public void updateToParent() {
		
	}
	
	public void handleSetParentAndRange(String ip,String port,float[] range){
		is.setParentAndRange(new NetWorkIS(ip, port),range);
	}
	
}
