package rwi.distributed.core.interfaces.server;

import rwi.distributed.core.classes.RWIVehicle;


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
	
	public boolean isInRange(float posX, float posY);
	
	public boolean isFull();
	
	/**
	 * 
	 * @return [0]= minX, [1]=maxX,[2]= minY, [3]=maxY,
	 */
	public float[] getRange();
	
	public void setRange(float minX, float maxX,float minY, float maxY);
	
	public RWIVehicle[] getObjectsOutOfRange();
	
	public void registerObjects(RWIVehicle[] v);
}
