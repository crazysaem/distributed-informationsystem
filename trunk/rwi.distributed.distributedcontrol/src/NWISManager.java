import java.util.ArrayList;

import rwi.distributed.core.classes.LocalInfoSystem;
import rwi.distributed.core.interfaces.server.IIS;
import rwi.distributed.core.interfaces.server.IISManager;



public class NWISManager implements IISManager {

	ArrayList<LocalInfoSystem> islist;

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
