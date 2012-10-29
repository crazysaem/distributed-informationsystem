package rwi.core.classes;

import rwi.core.interfaces.server.ICommunicationHandler;

public class NetWorkIS implements ICommunicationHandler{

	private String ipaddress;
	private String port;
	private float minX, maxX, minY, maxY;
	
	public NetWorkIS(String ip,String port){
		this.ipaddress = ip;
		this.port = port;
		this.minX = -1;
		this.maxX = -1;
		this.minY = -1;
		this.maxY = -1;
	}
	
	public NetWorkIS(String ipaddress,String port,float minX, float maxX, float minY, float maxY){
		this.ipaddress = ipaddress;
		this.port = port;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}
	
	public boolean isInRange(float posX, float posY) {
		if ((minX <= posX && posX <= maxX) && (minY <= posY && posY <= maxY)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void register(int id, int type, float[] pos, float[] size, int state,
			String ipaddress, String port) {
		DataTransfer.forwardRegister(this.ipaddress, this.port, id, type, pos, size, state, ipaddress, port);
	}

	@Override
	public int register(int type, float[] pos, float[] size, int state,
			String ipaddress, String port) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void unregister(int id) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void updatePosition(int id, float[] pos) {
		DataTransfer.forwardUpdatePos(ipaddress, port, id, pos);
	}

	@Override
	public void updateState(int id, int state) {
		// TODO Auto-generated method stub	
	}
	
	public String getIp(){
		return this.ipaddress;
	}
	
	public String getPort(){
		return this.port;
	}

}
