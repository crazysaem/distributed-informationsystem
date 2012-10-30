package rwi.internal.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import rwi.core.classes.IDGen;
import rwi.core.classes.NetWorkIS;
import rwi.core.interfaces.server.ICommunicationHandler;
import rwi.core.variables.RwiCommunication;

public class Dispatcher implements ICommunicationHandler {
	private String myport;
	private DispatchSignalingHandler signalhandler;
	private ServerManager smanager;
	private boolean isroot = false;
	
	private float[] range;
	private NetWorkIS parent;
	private HttpService http;
	
	ArrayList<NetWorkIS> infosystems;
	HashMap<Integer, NetWorkIS> idMap;

	private IDGen objIdGen;

	protected void setHttp(HttpService value) {
		this.http = value;
	}

	protected void startup(BundleContext context) {
		this.range = new float[4];
		myport = context.getProperty("org.osgi.service.http.port");
		if(context.getProperty("rwi.internal.dispatcher.isroot")!=null){
			isroot = Boolean.parseBoolean(context.getProperty("rwi.internal.dispatcher.isroot"));
		}
		if(isroot){
			System.out.println("Root Dispatcher:");
			this.smanager = new ServerManager(null);
			this.signalhandler =  new RootSignalingHandler(this,smanager);
			this.smanager.setSignal((RootSignalingHandler)signalhandler);
			//this.smanager.getInfoSystem(RwiCommunication.ROOT_ADDRESS, RwiCommunication.ROOT_PORT, new float[]{0,600,0,600});
		}else{
			System.out.println("Dispatcher:");
			this.signalhandler =  new DispatchSignalingHandler(this);
			signalhandler.askForInfoSystem(myport);
			this.smanager = null;
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
		signalhandler.forwarUnregister(parent.getIp(), parent.getPort(), id);
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
	
	public void addInfoSystem(NetWorkIS nwis){
		infosystems.add(nwis);		
	}
}
