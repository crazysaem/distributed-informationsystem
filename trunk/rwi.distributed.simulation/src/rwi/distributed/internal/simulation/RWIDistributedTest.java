package rwi.distributed.internal.simulation;

import java.util.Random;

import rwi.distributed.iscontrol.IMasterIs;

public class RWIDistributedTest {

	private IMasterIs masterIS;

	protected void startup(){
		int i=0;
		while(i++<10000){
		new Thread(){
			public void run(){
				String id = masterIS.register(10, 10, 10);
				System.out.println("Registered ID: "+id);
				try {
					sleep(new Random().nextInt(5000));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				masterIS.unregister(Integer.parseInt(id));
				System.out.println("Unregistered ID: "+id);
			}
		}.start();
		}
		
	}

	public void setMaster(IMasterIs value) {
		masterIS = value;
	}
}
