package rwi.distributed.core.interfaces.client;

public interface IObjectClient {
	public int register(int type, float posX, float posY);

	public void unregister(int id);
}
