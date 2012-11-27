package rwi.infosystem.internal.is;

import rwi.infosystem.core.classes.NetWorkIS;
import rwi.infosystem.core.classes.Requester;
import rwi.infosystem.is.communication.SignalTransfer;
/**
 * handle means an incoming request
 * send or forward means creating a new request
 * @author Mr.N0I3oDy
 *
 */
public class IsSignalingHandler {
	
	private InformationSystem is;
		
	public IsSignalingHandler(InformationSystem is) {
		this.is = is;
	}
	
	public void forwardDeleteToParent(int id,NetWorkIS parent) {
		SignalTransfer.sendDeleteRequest(id, parent);
	}

	public static void sendSplitRequest(String port,int state,NetWorkIS parent){
		SignalTransfer.askForSplit(port, state, parent);
	}
	
	public void handleSetParentAndRange(String ip,String port,float[] range){
		is.setParentAndRange(new NetWorkIS(ip, port),range);
	}
	
	public void handleUpdateRange(float[] range){
		is.setRange(range);
	}
}
