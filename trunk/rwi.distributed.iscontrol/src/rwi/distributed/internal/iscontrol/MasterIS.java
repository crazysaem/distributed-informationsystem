package rwi.distributed.internal.iscontrol;

import java.util.ArrayList;
import java.util.HashMap;

import rwi.dostributed.core.interfaces.server.IIS;
import rwi.informationsystem.internal.distributed.is.CentralInfoSysManager.IDGen;

public class MasterIS {

	ArrayList<RangedIS> infosystems;
	HashMap<Integer, IIS> idMap;

	private IDGen idgen;

	public MasterIS() {
		this.idgen = new IDGen();
	}

	public String register(int type, float posX, float posY) {

		IIS is = findIS(posX, posY);
		return is.registerRWI_Object(id, type, posX, posY);

	}

	private IIS findIS(float posX, float posY) {

		for (RangedIS ris : infosystems) {
			IIS is = ris.isInRange(posX, posY);
			if (is != null)
				return is;
		}
		return null;
	}

	private class IDGen {
		private int id;
		private ArrayList<Integer> freeids;

		public IDGen() {
			id = 0;
			freeids = new ArrayList<Integer>();
		}

		public int nextId() {
			if (freeids.size() == 0) {
				return id++;
			} else {
				return freeids.remove(0);
			}
		}

		public void removeId(int id) {
			if (id == (this.id - 1)) {
				this.id--;
			} else {
				this.freeids.add(id);
			}
		}
	}
}
