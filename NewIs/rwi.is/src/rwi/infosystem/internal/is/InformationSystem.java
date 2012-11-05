package rwi.infosystem.internal.is;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import rwi.infosystem.core.classes.RWIObject;
import rwi.infosystem.core.interfaces.server.ICommunicationHandler;
import rwi.infosystem.core.variables.GlobalVars;
import rwi.infosystem.core.variables.RwiCommunication;

public class InformationSystem implements ICommunicationHandler {
	
	private HttpService http;
	HashMap<Integer, RWIObject> objectMap;
	ArrayList<Integer> idlist = new ArrayList<>();
	private int objcount;
	SignalingHandler signalHandler;

	protected void setHttp(HttpService value) {
		this.http = value;
	}

	protected void startup() {
		try {
			RegisterServlet regservlet = new RegisterServlet(this);
			PositionServlet posservlet = new PositionServlet(this);
			SignalingServlet signalservlet = new SignalingServlet();

			http.registerServlet(RwiCommunication.REGISTER_SERVLET, regservlet,
					null, null);
			http.registerServlet(RwiCommunication.POSITION_SERVLET, posservlet,
					null, null);
			http.registerServlet(RwiCommunication.SIGNALING_SERVLET,
					signalservlet, null, null);

			System.out.println("Servlets registered.");
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamespaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public synchronized void register(int id, int type, float[] pos, float[] size,
			int state, String ipaddress, String port) {

		objcount++;
		if (type <= GlobalVars.MAXVEHICLETYPE) {
			objectMap.put(id, new RWIObject(pos, type, state, id, size, ipaddress, port));
			
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
	
	protected void updateRoot(int id){
		signalHandler.updateToParent();
}

}
