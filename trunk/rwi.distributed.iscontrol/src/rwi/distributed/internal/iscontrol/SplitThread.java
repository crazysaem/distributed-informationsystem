package rwi.distributed.internal.iscontrol;

import rwi.distributed.core.interfaces.server.IIS;

public class SplitThread extends Thread{

	private IIS is;
	private MasterIS master;
	
	public SplitThread(IIS is,MasterIS master){
		this.is= is;
		this.master =master; 
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		master.splitIS(is);
		super.run();
	}
}
