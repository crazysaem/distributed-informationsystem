package rwi.distributed.internal.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import rwi.distributed.core.classes.IDGen;
import rwi.distributed.core.classes.LocalInfoSystem;
import rwi.distributed.core.interfaces.server.IIS;
import rwi.distributed.core.variables.RwiCommunication;

public class Dispatcher {

	private HttpService http;
	ArrayList<IIS> infosystems;
	HashMap<Integer, IIS> idMap;
	private LocalInfoSystem localinfossystem;	
	
	private IDGen objIdGen;
	
	protected void setHttp(HttpService value){
		this.http = value;
	}
	
	protected void startup() {
		this.objIdGen = new IDGen();
		this.infosystems = new ArrayList<>();
		this.idMap = new HashMap<>();
		
		try {
			RegisterServlet regservlet = new RegisterServlet(this);
			PositionServlet posservlet = new PositionServlet(this);
			
			http.registerServlet(RwiCommunication.REGISTER_SERVLET, regservlet, null, null);
			http.registerServlet(RwiCommunication.POSITION_SERVLET, posservlet, null, null);
			
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
	
	public synchronized String register(int type, float posX, float posY) {
		int id = objIdGen.nextId();
		IIS is = findIS(posX, posY);

		idMap.put(id, is);
		String result = is.registerRWI_Object(id, type, posX, posY);
		
		return result;

	}

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
		return null;
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
