package rwi.distributed.internal.client;

import java.util.Random;
import java.util.Timer;

public class ConnectionManager {

	ClientStub stub;
	private int count;

	protected void startup() {
		
		Random rand = new Random();
		count = 0;
		stub = new ClientStub();
		// int[] ids = new int[11];
		long start = System.currentTimeMillis();
		while (count++ < 10000) {
			// ids[count-1]=
			stub.register(0, rand.nextInt(600)+1, rand.nextInt(600));
		}
		long end = System.currentTimeMillis() - start;
		System.out.println("Inserting Took: "+end+" Milliseconds ("+end/1000+"seconds)");
		// count=0;
		// while (count++ < 5) {
		// stub.unregister(ids[count-1]);
		// }
	}
}
