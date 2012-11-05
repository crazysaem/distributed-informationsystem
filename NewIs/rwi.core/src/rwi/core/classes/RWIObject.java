package rwi.core.classes;

public class RWIObject {
	
	private Vector pos; 
	private int type;
	private int state;
	private int id;
	private float[] size; //[0]=length,[1]=width
	private Vector vector; 
	private String ipaddress;
	private String port;
	
	public RWIObject(float[] pos, int type, int state, int id, float[] size,
			String ipaddress, String port) {
		this.pos = new Vector(pos[0], pos[1]);
		this.vector = new Vector();
		this.type = type;
		this.state = state;
		this.id = id;
		this.size = size;
		this.ipaddress = ipaddress;
		this.port = port;
	}
	
	public float[] getPos() {
		return new float[]{pos.x, pos.y};
	}
	
	public void setPos(float[] pos) {
		Vector temp = new Vector(pos[0], pos[1]);
		this.vector = temp.direction(this.pos);
		this.pos = temp;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getType() {
		return type;
	}
	public int getId() {
		return id;
	}
	public float[] getSize() {
		return size;
	}
	public float[] getVector() {
		return new float[]{vector.x, vector.y};
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public String getPort() {
		return port;
	}
	
	private class Vector{
		private float x, y;
		
		public Vector(float x, float y){
			this.x = x;
			this.y = y;
		}
		
		public Vector(){
			x=0;
			y=0;
		}
		
		public Vector direction(Vector v){
			
			float xt =v.x - this.x;
			float yt = v.y - this.y;
			
			return new Vector(xt, yt);
		}
	}
}
