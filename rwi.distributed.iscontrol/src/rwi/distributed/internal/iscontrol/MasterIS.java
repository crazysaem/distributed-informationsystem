package rwi.distributed.internal.iscontrol;

import java.util.ArrayList;
import java.util.HashMap;

import rwi.distributed.core.classes.LocalInfoSystem;
import rwi.distributed.core.interfaces.server.IIS;
import rwi.distributed.core.interfaces.server.IISManager;
import rwi.distributed.iscontrol.IMasterIs;

public class MasterIS implements IMasterIs {

	ArrayList<IIS> infosystems;
	HashMap<Integer, IIS> idMap;

	private IDGen objIdGen;
	private IDGen isIdGen;
	private IISManager manager;

	public void setISManager(IISManager value) {
		manager = value;
	}

	protected void startup() {
		this.objIdGen = new IDGen();
		this.isIdGen =  new IDGen();
		this.infosystems = new ArrayList<>();
		this.idMap = new HashMap<>();
		System.out.println("Master Controler started!");
	}

	public MasterIS() {
		this.objIdGen = new IDGen();
		this.infosystems = new ArrayList<>();
		this.idMap = new HashMap<>();
	}

	@Override
	public synchronized String register(int type, float posX, float posY) {

		IIS is = findIS(posX, posY);
		int id = objIdGen.nextId();
		idMap.put(id, is);
		return is.registerRWI_Object(id, type, posX, posY);

	}

	@Override
	public synchronized void unregister(int id) {
		idMap.get(id).unregisterRWI_Object(id);
		idMap.remove(id);
		objIdGen.removeId(id);
	}

	private IIS findIS(float posX, float posY) {

		for (IIS ris : infosystems) {
			IIS is = ris.isInRange(posX, posY);
			if (is != null)
				return is;
		}

		return makeNewIS(posX, posY);

	}

	private IIS makeNewIS(float posX, float posY) {
		int range = 20;
		IIS s = manager.generateIS(posX - range, posX + range, posY - range,
				posY + range,isIdGen.nextId());
		infosystems.add(s);
		System.out.println("New Is generated!");
		return s;
	}

}
