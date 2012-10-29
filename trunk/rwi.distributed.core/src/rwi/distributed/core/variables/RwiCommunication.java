package rwi.distributed.core.variables;


public class RwiCommunication {
	public static final String PARAMETER_POSX = "posX";
	public static final String PARAMETER_POSY = "posY";
	public static final String PARAMETER_ID = "id";
	public static final String PARAMETER_TYPE = "type";
	public static final String PARAMETER_IPADR = "ipadr";
	public static final String PARAMETER_PORT = "port";
	
	public static final String REGISTER_SERVLET = "/register";
	public static final String UNREGISTER_SERVLET = "/unregister";
	public static final String POSITION_SERVLET = "/position";
	public static final String INIT_SERVLET = "/init";
	
	public static final String ROOT_ADDRESS = "127.0.0.1";
	public static final String DATA_PORT = "8080";
	public static final String SIGNALING_PORT = "8081";
	
	public static final String REQUESTMETHOD_POST = "POST";
	public static final String REQUESTMETHOD_GET = "GET";
	public static final String REQUESTMETHOD_DELETE = "DELETE";
}