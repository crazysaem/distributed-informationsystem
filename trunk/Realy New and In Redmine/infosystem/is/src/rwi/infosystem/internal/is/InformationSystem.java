package rwi.infosystem.internal.is;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import rwi.infosystem.core.classes.InfoServlet;
import rwi.infosystem.core.classes.NetWorkIS;
import rwi.infosystem.core.classes.RWIObject;
import rwi.infosystem.core.interfaces.server.ICommunicationHandler;
import rwi.infosystem.core.variables.GlobalVars;
import rwi.infosystem.core.variables.RwiCommunication;

public class InformationSystem  implements ICommunicationHandler{

	private boolean isWaitingForSplit = false;
	private int state = 0;
	private IsSignalingHandler signalHandler;
	private NetWorkIS parent;
	private HttpService http;
	private float[] range;
	private String myport;
	
	HashMap<Integer, RWIObject> objectMap;
	ArrayList<Integer> idlist = new ArrayList<>();
	
	private int objcount;	

	protected void setHttp(HttpService value) {
		System.out.println("setHttp");
		this.http = value;				
	}	
	
	protected void startup(BundleContext context) {
		this.myport = context.getProperty("org.osgi.service.http.port");
		System.out.println("IS startup...");
		objectMap = new HashMap<>();
		range = new float[4];
		signalHandler = new IsSignalingHandler(this);
		try {
			RegisterServlet regservlet = new RegisterServlet(this);
			PositionServlet posservlet = new PositionServlet(this);
			SignalingServlet signalservlet = new SignalingServlet(signalHandler);
			InfoServlet infoservlet = new InfoServlet(this);

			http.registerServlet(RwiCommunication.REGISTER_SERVLET, regservlet,
					null, null);
			http.registerServlet(RwiCommunication.POSITION_SERVLET, posservlet,
					null, null);
			http.registerServlet(RwiCommunication.SIGNALING_SERVLET,
					signalservlet, null, null);
			http.registerServlet(RwiCommunication.INFO_SERVLET,
					infoservlet, null, null);

			System.out.println("Servlets registered...");
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamespaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void register(int id, int type, float[] pos,
			float[] size, int state, String ipaddress, String port) {
		
		objcount++;
		if (objcount <= GlobalVars.MAXVEHICLETYPE) {
			objectMap.put(id, new RWIObject(pos, type, state, id, size,ipaddress, port));
			System.out.println("Object at:"+pos[0]+"|"+pos[1]);
		}else if(isWaitingForSplit){
			isWaitingForSplit = true;
			signalHandler.sendSplitRequest(myport, state, parent);			
		}
		idlist.add(id);
	}

	@Override
	public int register(int type, float[] pos, float[] size, int state,
			String ipaddress, String port) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public synchronized void unregister(int id) {
		objectMap.remove(((Integer)id));
		idlist.remove(((Integer)id));
		if(parent!=null){
			signalHandler.forwardDeleteToParent(id, parent);
		}
		objcount--;

	}

	@Override
	public void updatePosition(int id, float[] pos) {
		
		RWIObject rwi = objectMap.get(id);
		rwi.setPos(pos);
	}

	@Override
	public void updateState(int id, int state) {
		
		RWIObject rwi = objectMap.get(id);
		rwi.setState(state);
	}

	public void setParentAndRange(NetWorkIS nwis,float[] range) {
		this.parent = nwis;
		System.out.println("Parent: "+nwis.getIp()+":"+nwis.getPort());
		this.range = range;
		System.out.println("Range:["+range[0]+"-"+range[1]+"]["+range[2]+"-"+range[3]+"]");
	}
	
	public void setRange(float[] range){
		this.range = range;
		System.out.println("Range changed to:["+range[0]+"-"+range[1]+"]["+range[2]+"-"+range[3]+"]");
		reCheckAllObjects();
	}

	@Override
	public String getInfo() {
		String info = "<ul>";
		for(RWIObject obj : objectMap.values())
			info += "<li>ID"+obj.getId()+": at "+obj.getPos()[0]+"x,"+obj.getPos()[1]+"y</li>";
		info += "</ul>";
		return info;
	}
	
	private void reCheckAllObjects(){
		//TODO
		//Send all OutOfRange to parent
		objcount = objcount / 2; //Needs to be calculated!!
		isWaitingForSplit = false;
	}
}
