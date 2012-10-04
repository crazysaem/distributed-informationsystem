package rwi.distributed.internal.iscontrol;

import java.util.ArrayList;
import java.util.HashMap;

import rwi.distributed.core.interfaces.server.IIS;
import rwi.distributed.internal.informationsystem.ISManager;
import rwi.distributed.iscontrol.IMasterIs;

public class MasterIS implements IMasterIs {

	ArrayList<RangedIS> infosystems;
	HashMap<Integer, IIS> idMap;

	private IDGen idgen;
	private ISManager manager;

	public void setISManager(ISManager value) {
		manager = value;

	}

	protected void startup() {
		this.idgen = new IDGen();
		this.infosystems = new ArrayList<>();
		this.idMap = new HashMap<>();
	}

	public MasterIS() {
		this.idgen = new IDGen();
		this.infosystems = new ArrayList<>();
		this.idMap = new HashMap<>();
	}

	@Override
	public synchronized String register(int type, float posX, float posY) {

		IIS is = findIS(posX, posY);
		int id = idgen.nextId();
		idMap.put(id, is);
		System.out.println("unregistered:"+id);
		return is.registerRWI_Object(id, type, posX, posY);
		
	}

	@Override
	public synchronized void unregister(int id) {
		idMap.get(id).unregisterRWI_Object(id);
		idMap.remove(id);
		idgen.removeId(id);
	}

	private IIS findIS(float posX, float posY) {

		for (RangedIS ris : infosystems) {
			IIS is = ris.isInRange(posX, posY);
			if (is != null)
				return is;
		}
		System.out.println("New Is generated!");
		IIS s = manager.generateIS(posX - 10, posX - 20, posY - 10, posY + 10);
		infosystems.add(new RangedIS(posX - 10, posX - 20, posY - 10,
				posY + 10, s));
		return s;

	}

	private class IDGen {
		private int id;
		private ArrayList<Integer> freeids;

		public IDGen() {
			id = 0;
			freeids = new ArrayList<Integer>();
		}

		public synchronized int nextId() {
			if (freeids.size() == 0) {
				return id++;
			} else {
				return freeids.remove(0);
			}
		}

		public synchronized void removeId(int id) {
			if (id == (this.id - 1)) {
				this.id--;
			} else {
				this.freeids.add(id);
			}
		}
	}
}
