package rwi.distributed.internal.informationsystem;

import java.util.ArrayList;

import rwi.distributed.core.classes.LocalInfoSystem;
import rwi.distributed.core.interfaces.server.IIS;
import rwi.distributed.core.interfaces.server.IISManager;

public class ISManager implements IISManager {

	ArrayList<LocalInfoSystem> islist;

	protected void startup() {
		this.islist = new ArrayList<>();
	}

	@Override
	public IIS generateIS(float minX, float maxX, float minY, float maxY,int id) {
		LocalInfoSystem temp = new LocalInfoSystem(minX, maxX, minY, maxY,id);
		islist.add(temp);
		return temp;
	}

	@Override
	public void destroyIS(IIS tobedestroyed) {
		islist.remove(tobedestroyed);
	}
}
