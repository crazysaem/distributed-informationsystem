package rwi.distributed.core.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import rwi.distributed.core.interfaces.server.IIS;
import rwi.distributed.core.variables.GlobalVars;

public class LocalInfoSystem implements IIS {

	private boolean isInUse;
	HashMap<Integer, RWIVehicle> objectMap;
	ArrayList<Integer> idlist = new ArrayList<>();
	private float minX, maxX, minY, maxY;
	private int id;
	private int objcount;

	public LocalInfoSystem(float minX, float maxX, float minY, float maxY,
			int id) {
		objectMap = new HashMap<>();
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.id = id;
		this.objcount = 0;
		isInUse = false;
	}

	@Override
	public String registerRWI_Object(int id, int type, float posX, float posY) {

		objcount++;
		if (type <= GlobalVars.MAXVEHICLETYPE) {
			objectMap.put(id, new RWIVehicle(posX, posY, type, id));
		}
		idlist.add(id);
		return "" + id;
	}

	@Override
	public void updateRWIObjectPos(int id, float posX, float posY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterRWI_Object(int id) {
		// TODO Auto-generated method stub
		objectMap.remove(id);
		idlist.remove(id);
		objcount--;
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

	@Override
	public boolean isInRange(float posX, float posY) {
		if ((minX <= posX && posX <= maxX) && (minY <= posY && posY <= maxY)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isFull() {
		if (objcount >= 1000)
			return true;
		else
			return false;
	}

	@Override
	public float[] getRange() {
		float[] res = { minX, maxX, minY, maxY };
		return res;
	}

	@Override
	public void setRange(float minX, float maxX, float minY, float maxY) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

	@Override
	public ArrayList<RWIVehicle> getObjectsOutOfRange() {
		ArrayList<RWIVehicle> list = new ArrayList<>();
		ArrayList<Integer> idl = (ArrayList<Integer>) idlist.clone();
		int count = 0;
		RWIVehicle v;
		while (count < idl.size()
				&& (v = objectMap.get(idl.get(count++))) != null) {
			if (!isInRange(v.posX, v.posY)) {
				list.add(v);
				idlist.remove((Integer) idl.get(count - 1));
				objectMap.remove(idl.get(count - 1));
				objcount--;
			}
		}
		return list;
	}

	@Override
	public void registerObjects(ArrayList<RWIVehicle> ve) {
		for (RWIVehicle v : ve) {
			registerRWI_Object(v.id, v.type, v.posX, v.posY);
		}
	}

	public int getCount() {
		return this.objcount;
	}

	public String getInfo() {
		String s = "";
		ArrayList<Integer> idl = (ArrayList<Integer>) idlist.clone();
		int count = 0;
		RWIVehicle v;
		float width = maxY-minY;
		while (count < idl.size() && (v = objectMap.get(idl.get(count++))) != null) {
			s+="<div style=\"width:2px;height:2px;background-color:#000;top:"+(v.posY)+"px;left:"+(v.posX)+"px;position:absolute;\"></div>";
		}
		return s;
	}

	@Override
	public IIS split(IIS is, int id, HashMap<Integer, IIS> idMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean contains(int id){
		return idlist.contains((Integer)id);
	}

}
