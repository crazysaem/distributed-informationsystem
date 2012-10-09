package rwi.distributed.internal.client;

import java.util.Random;

public class ConnectionManager {

	ClientStub stub;
	private int count;

	protected void startup() {
		Random rand = new Random();
		count = 0;
		stub = new ClientStub();
		// int[] ids = new int[11];
		while (count++ < 2000) {
			// ids[count-1]=
			stub.register(0, rand.nextInt(600)+1, rand.nextInt(600));
		}
		// count=0;
		// while (count++ < 5) {
		// stub.unregister(ids[count-1]);
		// }
	}
}
