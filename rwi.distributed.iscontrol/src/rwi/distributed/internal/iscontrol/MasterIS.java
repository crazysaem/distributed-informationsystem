package rwi.distributed.internal.iscontrol;

import java.util.ArrayList;
import java.util.HashMap;

import rwi.dostributed.core.interfaces.server.IIS;

public class MasterIS {

	ArrayList<RangedIS> infosystems;
	HashMap<Integer, IIS> idMap;

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

}
