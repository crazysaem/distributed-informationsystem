package rwi.infosystem.core.interfaces.server;

public interface ICommunicationHandler {
	/*
	 * If object already got an id assigned
	 */
	public void register(int id, int type, float[] pos,float[] size,int state, String ipaddress,String port);
	/*
	 * If object did not have an id
	 */
	public int register(int type, float[] pos,float[] size,int state, String ipaddress,String port);
	
	public void unregister(int id);
	
	public void updatePosition(int id, float[] pos);
	
	public void updateState(int id,int state);
	
	public String getInfo();
}
