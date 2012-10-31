package rwi.internal.is;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import rwi.core.classes.NetWorkIS;
import rwi.core.classes.RWIObject;
import rwi.core.interfaces.server.ICommunicationHandler;
import rwi.core.variables.GlobalVars;
import rwi.core.variables.RwiCommunication;

public class InformationSystem  implements ICommunicationHandler{

	private IsSignalingHandler signalHandler;
	private NetWorkIS parent;
	private HttpService http;
	private float[] range;
	
	HashMap<Integer, RWIObject> objectMap;
	ArrayList<Integer> idlist = new ArrayList<>();
	
	private int objcount;	

	protected void setHttp(HttpService value) {
		System.out.println("setHttp");
		this.http = value;				
	}	
	
	protected void startup(BundleContext context) {
		System.out.println("IS startup...");
		objectMap = new HashMap<>();
		range = new float[4];
		signalHandler = new IsSignalingHandler(this);
		try {
			RegisterServlet regservlet = new RegisterServlet(this);
			PositionServlet posservlet = new PositionServlet(this);
			SignalingServlet signalservlet = new SignalingServlet(signalHandler);

			http.registerServlet(RwiCommunication.REGISTER_SERVLET, regservlet,
					null, null);
			http.registerServlet(RwiCommunication.POSITION_SERVLET, posservlet,
					null, null);
			http.registerServlet(RwiCommunication.SIGNALING_SERVLET,
					signalservlet, null, null);

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
		if (type <= GlobalVars.MAXVEHICLETYPE) {
			objectMap.put(id, new RWIObject(pos, type, state, id, size,ipaddress, port));
			System.out.println("Object at:"+pos[0]+"|"+pos[1]);
		}else{
			signalHandler.sendSplitRequest(">>>>MYPORT!!!<<<<<", 0);
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

		objectMap.remove(id);
		idlist.remove(id);
		updateRoot(id);
		objcount--;

	}

	@Override
	public void updatePosition(int id, float[] pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateState(int id, int state) {
		// TODO Auto-generated method stub
	}

	protected void updateRoot(int id) {
		signalHandler.updateToParent();
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
	}
}
