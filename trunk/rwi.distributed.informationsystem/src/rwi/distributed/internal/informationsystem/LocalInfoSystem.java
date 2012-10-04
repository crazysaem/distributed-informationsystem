package rwi.distributed.internal.informationsystem;

import java.util.ArrayList;
import java.util.HashMap;

import rwi.distributed.core.interfaces.client.IObjectClient;
import rwi.distributed.core.interfaces.server.IIS;
import rwi.distributed.core.variables.GlobalVars;

public class LocalInfoSystem implements IIS {

	HashMap<Integer, RWIVehicle> objectMap;
	private float minX, maxX, minY, maxY;

	public LocalInfoSystem(float minX, float maxX, float minY, float maxY) {
		objectMap = new HashMap<>();
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
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

}
