package rwi.distributed.internal.client;

public class ConnectionManager {

	ClientStub stub;
	private int count, posY;

	protected void startup() {
		count = 0;
		stub = new ClientStub();
		int[] ids = new int[5];
		while (count++ < 5) {
			ids[count-1]=stub.register(0, count, posY);
		}
		count=0;
		while (count++ < 5) {
			stub.unregister(ids[count-1]);
		}
	}
}
