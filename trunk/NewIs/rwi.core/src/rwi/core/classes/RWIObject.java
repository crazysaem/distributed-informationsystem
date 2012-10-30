package rwi.core.classes;

public class RWIObject {
	
	private float[] pos; //[0]=x,[1]=y
	private int type;
	private int state;
	private int id;
	private float[] size; //[0]=length,[1]=width
	private float[] vector; //[0]=x,[1]=y
	private String ipaddress;
	private String port;
	public RWIObject(float[] pos, int type, int state, int id, float[] size,
			String ipaddress, String port) {
		super();
		this.pos = pos;
		this.type = type;
		this.state = state;
		this.id = id;
		this.size = size;
		this.ipaddress = ipaddress;
		this.port = port;
	}	
	
}
