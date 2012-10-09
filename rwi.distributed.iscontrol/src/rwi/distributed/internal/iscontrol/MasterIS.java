package rwi.distributed.internal.iscontrol;

import java.util.ArrayList;
import java.util.HashMap;

import rwi.distributed.core.classes.LocalInfoSystem;
import rwi.distributed.core.classes.RWIVehicle;
import rwi.distributed.core.interfaces.server.IIS;
import rwi.distributed.core.interfaces.server.IISManager;
import rwi.distributed.iscontrol.IMasterIs;

public class MasterIS implements IMasterIs {

	ArrayList<IIS> infosystems;
	HashMap<Integer, IIS> idMap;

	boolean blocksplit;
	
	private IDGen objIdGen;
	private IDGen isIdGen;
	private IISManager manager;

	public void setISManager(IISManager value) {
		manager = value;
	}

	protected void startup() {
		this.objIdGen = new IDGen();
		this.isIdGen = new IDGen();
		blocksplit = false;
		this.infosystems = new ArrayList<>();
		this.idMap = new HashMap<>();
		System.out.println("Master Controler started!");

		IIS is = new LocalInfoSystem(0, 600, 0, 600, isIdGen.nextId());
		infosystems.add(is);
		System.out.println("IS Started 0-600;0-600;");
	}

	public MasterIS() {
		this.objIdGen = new IDGen();
		this.infosystems = new ArrayList<>();
		this.idMap = new HashMap<>();
	}

	@Override
	public synchronized String register(int type, float posX, float posY) {

		int id = objIdGen.nextId();
		IIS is = findIS(posX, posY);

		idMap.put(id, is);
		String result = is.registerRWI_Object(id, type, posX, posY);

		if (is.isFull() && !blocksplit){
			blocksplit= true;
			splitIS(is);
			blocksplit=false;
		}
		// if (is.isFull()) {
		// new SplitThread(is, this).start();
		// }
		return result;

	}

	@Override
	public synchronized void unregister(int id) {
		idMap.get(id).unregisterRWI_Object(id);
		idMap.remove(id);
		objIdGen.removeId(id);
	}

	private IIS findIS(float posX, float posY) {

		for (IIS ris : infosystems) {
			if (ris.isInRange(posX, posY))
				return ris;
		}

		return makeNewIS(posX, posY);

	}

	private IIS makeNewIS(float posX, float posY) {
		int range = 20;
		IIS s = manager.generateIS(posX - range, posX + range, posY - range,
				posY + range, isIdGen.nextId());
		infosystems.add(s);
		System.out.println("New Is generated!");
		return s;
	}

	public void splitIS(IIS is) {

		float[] range = is.getRange();
		float distX = range[1] - range[0];
		float distY = range[3] - range[2];
		float[] range2 = new float[4];
		
		System.out.println("Split IS("+is.getID()+"): "+range[0]+":"+range[1]+":"+range[2]+":"+range[3]+"-Objs:"+is.getCount());
		if (distX < distY) {
			// calculate the new ranges
			range2[0] = range[0];
			range2[1] = range[1];
			range2[2] = range[2];
			range2[3] = range[3] - distY / 2;

			range[2] = range[2] + distY / 2;
		} else {
			// calculate the new ranges
			range2[0] = range[0];
			range2[1] = range[1] - distX / 2;
			range2[2] = range[2];
			range2[3] = range[3];

			range[0] = range[0] + distX / 2;
		}
		is.setRange(range[0], range[1], range[2], range[3]);

		// TODO: implement functionality to choose between a new local or
		// network InfoSys
		// generate new InfoSys
		int id = isIdGen.nextId();
		LocalInfoSystem nis = (LocalInfoSystem) manager.generateIS(range2[0],
				range2[1], range2[2], range2[3], id);
		infosystems.add(nis);
				
		moveObjs(is, nis);
		
		System.out.println("To: "+range[0]+":"+range[1]+":"+range[2]+":"+range[3]+"-Objs:"+is.getCount());
		System.out.println("And: "+range2[0]+":"+range2[1]+":"+range2[2]+":"+range2[3]+"-Objs:"+nis.getCount());
	}

	private void moveObjs(IIS is, IIS nis) {
		ArrayList<RWIVehicle> ve = is.getObjectsOutOfRange();
		for (RWIVehicle v : ve) {
			idMap.put(v.getId(), nis);
		}
		nis.registerObjects(ve);
	}

	public String getInfo(){
		String s = "";
		s="<div style=\"width:600px;height:600px;background-color:#eee;position:absolute;\">";
		for(IIS is:infosystems){
			float[] r = is.getRange();
			s+=is.getInfo();
			s+="<div style=\"width:"+(r[1]-r[0])+"px;height:"+(r[3]-r[2])+"px;top:"+(600-r[2]-(r[3]-r[2]))+";left:"+r[0]+"px;position:absolute;border:1px solid grey;\">";
			s+="</div>";
		}
		s+="</div>";
		return s;
	}
	
}
