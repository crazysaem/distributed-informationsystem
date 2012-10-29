package rwi.internal.is;

import rwi.core.classes.NetWorkIS;
import rwi.core.interfaces.server.ICommunicationHandler;
import rwi.internal.is.communication.SignalTransfer;

public class SignalingHandler {
	
	private ICommunicationHandler is;
		
	public SignalingHandler(ICommunicationHandler is) {
		this.is = is;
	}
	
	public void updateToParent() {
		
	}
	
}
