package rwi.distributed.core.classes;

import java.util.HashMap;

import rwi.distributed.core.interfaces.server.IIS;
import rwi.distributed.core.variables.GlobalVars;

public class LocalInfoSystem implements IIS {

	HashMap<Integer, RWIVehicle> objectMap;
	private float minX, maxX, minY, maxY;
	private int id;

	public LocalInfoSystem(float minX, float maxX, float minY, float maxY,
			int id) {
		objectMap = new HashMap<>();
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.id = id;
	}

	@Override
	public String registerRWI_Object(int id, int type, float posX, float posY) {

		if (type <= GlobalVars.MAXVEHICLETYPE) {
			objectMap.put(id, new RWIVehicle(posX, posY, type));
		}
		return "" + id;
	}

	@Override
	public void updateRWIObjectPos(int id, float posX, float posY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterRWI_Object(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public String calculateRoute(int id, float targetPosX, float targetPosY) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeRWIObjectState(int id, int state) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return id;
	}

}
