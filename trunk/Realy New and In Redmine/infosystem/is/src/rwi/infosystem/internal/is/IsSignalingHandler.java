package rwi.infosystem.internal.is;

import rwi.infosystem.core.classes.NetWorkIS;
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
	
	public void updateToParent() {
		
	}
	

	public static void sendSplitRequest(String port,int mode){
		//TODO
		//Implement
	}
	
	public void handleSetParentAndRange(String ip,String port,float[] range){
		is.setParentAndRange(new NetWorkIS(ip, port),range);
	}
	
	public void handleUpdateRange(float[] range){
		is.setRange(range);
	}
}
