package rwi.distributed.core.interfaces.server;


public interface IIS {

	/**
	 * 
	 * @param posX
	 * @param posY
	 * @return id of the registered RWI_Object
	 */
	public String registerRWI_Object(int id, int type, float posX, float posY);

	public void updateRWIObjectPos(int id, float posX, float posY);
	
	public void unregisterRWI_Object(int id);
	
	public String calculateRoute(int id, float targetPosX,float targetPosY);
	
	public void changeRWIObjectState(int id,int state);
	
	public int getID();
}
