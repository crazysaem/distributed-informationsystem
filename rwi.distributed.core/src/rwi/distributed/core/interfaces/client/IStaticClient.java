package rwi.distributed.core.interfaces.client;

public interface IStaticClient extends IObjectClient {
	public void updateState(int id, int state);
}
