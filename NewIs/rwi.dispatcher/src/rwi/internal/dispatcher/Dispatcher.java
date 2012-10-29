package rwi.internal.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import rwi.core.classes.IDGen;
import rwi.core.interfaces.server.ICommunicationHandler;
import rwi.core.variables.RwiCommunication;

public class Dispatcher implements ICommunicationHandler {
	private SignalingHandler signalhandler;
	private ServerManager smanager;
	private boolean isroot = false;
	
	private NetWorkIS parent;
	private HttpService http;
	
	ArrayList<NetWorkIS> infosystems;
	HashMap<Integer, NetWorkIS> idMap;

	private IDGen objIdGen;

	protected void setHttp(HttpService value) {
		this.http = value;
	}

	protected void startup(BundleContext context) {
		this.smanager = new ServerManager(signalhandler);
		this.signalhandler =  new SignalingHandler(this,smanager);
		if(context.getProperty("rwi.internal.dispatcher.isroot")!=null){
			isroot = Boolean.parseBoolean(context.getProperty("rwi.internal.dispatcher.isroot"));
		}
		
		this.objIdGen = new IDGen();
		this.infosystems = new ArrayList<>();
		this.idMap = new HashMap<>();
		
		// bind the servlets
		try {
			RegisterServlet regservlet = new RegisterServlet(this);
			PositionServlet posservlet = new PositionServlet(this);
			SignalingServlet signalservlet = new SignalingServlet(signalhandler);

			http.registerServlet(RwiCommunication.REGISTER_SERVLET, regservlet,
					null, null);
			http.registerServlet(RwiCommunication.POSITION_SERVLET, posservlet,
					null, null);
			http.registerServlet(RwiCommunication.SIGNALING_SERVLET, signalservlet,
					null, null);

			System.out.println("Servlets registered.");
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamespaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Dispatcher started!");
	}

	private NetWorkIS findDispatchTarget(float posX, float posY) {
		for (NetWorkIS ris : infosystems) {
			if (ris.isInRange(posX, posY))
				return ris;
		}
		return parent;
	}

	private NetWorkIS findDispatchTarget(int id) {
		if (!idMap.containsKey(id)) {
			return parent;
		} else {
			return idMap.get(id);
		}
	}

	@Override
	public void register(int id, int type, float[] pos, float[] size,int state, String ipaddress, String port) {
		NetWorkIS nwis = findDispatchTarget(pos[0], pos[1]);
		
		addMapping(id, nwis);
		nwis.register(id, type, pos, size, state, ipaddress, port);
	}

	@Override
	public int register(int type, float[] pos, float[] size, int state,String ipaddress, String port) {
		int id = objIdGen.nextId();

		NetWorkIS nwis = findDispatchTarget(pos[0], pos[1]);
		addMapping(id, nwis);
		nwis.register(id, type, pos, size, state, ipaddress, port);
		
		return id;
	}

	@Override
	public void updatePosition(int id, float[] pos) {
		NetWorkIS nwis = findDispatchTarget(id);
		nwis.updatePosition(id, pos);
	}

	@Override
	public void updateState(int id, int state) {
		NetWorkIS nwis = findDispatchTarget(id);
		nwis.updateState(id, state);
	}

	@Override
	public void unregister(int id) {
		removeMapping(id);
		NetWorkIS nwis = findDispatchTarget(id);
		nwis.unregister(id);
	}
	
	private void addMapping(int id, NetWorkIS is){
		this.idMap.put(id, is);
	}
	private void removeMapping(int id){
		this.idMap.remove(id);
	}
	
	private void split(NetWorkIS nwis){
		//TODO initialise Signlaling between all the componentes
	}
}
