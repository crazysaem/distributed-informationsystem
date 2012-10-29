package rwi.distributed.internal.dispatcher.communication;

import java.util.ArrayList;
import java.util.HashMap;

import rwi.distributed.core.classes.RWIVehicle;
import rwi.distributed.core.interfaces.server.IIS;

public class NetworkIs implements IIS{

	private String IPAddress;
	
	public NetworkIs(String ip){
		this.IPAddress = ip;
	}
	
	@Override
	public String registerRWI_Object(int id, int type, float posX, float posY) {
		String serverip = DataTransfer.forwardRegister(IPAddress, id, type, posX, posY);
		return serverip;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isInRange(float posX, float posY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float[] getRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRange(float minX, float maxX, float minY, float maxY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<RWIVehicle> getObjectsOutOfRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerObjects(ArrayList<RWIVehicle> v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIS split(IIS is, int id, HashMap<Integer, IIS> idMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
