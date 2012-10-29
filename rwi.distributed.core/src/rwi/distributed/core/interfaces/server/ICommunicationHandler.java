package rwi.distributed.core.interfaces.server;

public interface ICommunicationHandler {
	/*
	 * If object already got an id assigned
	 */
	public void register(int id, int type, float posX, float posY);
	/*
	 * If object did not have an id
	 */
	public int register(int type, float posX, float posY);
	public void unregister();
	public void updatePosition();
	public void getObjectsInRange();
}
