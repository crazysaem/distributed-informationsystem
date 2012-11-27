package rwi.infosystem.core.classes;

import rwi.infosystem.core.interfaces.server.ICommunicationHandler;

public class NetWorkIS implements ICommunicationHandler{

	private String ipaddress;
	private String port;
	private float[] range;
	
	public NetWorkIS(String ip,String port){
		this.ipaddress = ip;
		this.port = port;
		this.range = null;
	}
	
	public NetWorkIS(String ip,String port,float[] range){
		this.ipaddress = ip;
		this.port = port;
		this.range = range;
	}
	
	public NetWorkIS(String ipaddress,String port,float minX, float maxX, float minY, float maxY){
		this.ipaddress = ipaddress;
		this.port = port;
		this.range[0] = minX;
		this.range[1] = maxX;
		this.range[2] = minY;
		this.range[3] = maxY;
	}
	
	public boolean isInRange(float posX, float posY) {
		if ((range[0] <= posX && posX <= range[1]) && (range[2] <= posY && posY <= range[3])) {
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
	
	public float[] getRange(){
		return range;
	}
	
	public boolean isRangeEmpty(){
		return (range==null);
	}

	public String toString(){
		return this.ipaddress+":"+this.port+" with Range{X:"+range[0]+"-"+range[1]+";Y:"+range[2]+"-"+range[3]+"}";
	}
	
	public void updateRange(float[] range){
		//update localy
		this.range = range;
		//update the system in the network
		NWISSignalingHandler.updateRange(this, range);
	}

	@Override
	public String getInfo() {
		//maybe implement this
		return null;
	}
	
	public boolean equals(NetWorkIS nwis){
		if(this.port.equals(nwis.port) && this.ipaddress.equals(nwis.ipaddress))
			return true;
		else return false;
	}
}
