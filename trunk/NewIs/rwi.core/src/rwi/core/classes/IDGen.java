package rwi.core.classes;

import java.util.ArrayList;

public class IDGen {
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
