package rwi.distributed.internal.informationsystem;

import java.util.ArrayList;

import rwi.distributed.core.interfaces.server.IIS;

public class ISManager {

	ArrayList<LocalInfoSystem> islist;

	protected void startup(){
		this.islist = new ArrayList<>();
	}

	public IIS generateIS(float minX, float maxX, float minY, float maxY) {
		LocalInfoSystem temp = new LocalInfoSystem(minX, maxX, minY, maxY);
		islist.add(temp);
		return temp;
	}
	
}
