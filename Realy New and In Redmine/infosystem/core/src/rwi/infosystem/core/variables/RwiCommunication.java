package rwi.infosystem.core.variables;



public class RwiCommunication {
	public static final String READY = "ready";
	
	public static final String PARAMETER_POSITION = "pos";
	public static final String PARAMETER_ID = "id";
	public static final String PARAMETER_TYPE = "type";
	public static final String PARAMETER_STATE = "state";
	public static final String PARAMETER_SIZE = "size";	
	public static final String PARAMETER_IPADR = "ipadr";
	public static final String PARAMETER_PORT = "port";
	public static final String PARAMETER_RANGE = "range";
	public static final String PARAMETER_INITDISP = "init_disp";
	public static final String PARAMETER_INITIS = "init_is";
	
	public static final String PARAMETER_SIGNALING_MODE = "mode";
	public static final int SIGNALING_MODE_REGIS = 0;
	public static final int SIGNALING_MODE_REGDISP = 1;
	public static final int SIGNALING_MODE_SERVERREADY = 2;
	public static final int SIGNALING_MODE_INIT_DISP = 3;
	public static final int SIGNALING_MODE_INIT_IS = 4;
	public static final int SIGNALING_MODE_UNREGISTER = 5;
	public static final int SIGNALING_MODE_ASK_FOR_IS = 6;
	public static final int SIGNALING_MODE_IS_READY = 7;
	public static final int SIGNALING_MODE_SET_PARENT_AND_RANGE = 8;
	public static final int SIGNALING_MODE_UPDATE_RANGE = 9;	
	
	public static final String REGISTER_SERVLET = "/register";
	public static final String UNREGISTER_SERVLET = "/unregister";
	public static final String POSITION_SERVLET = "/position";
	public static final String SIGNALING_SERVLET = "/signaling";
	public static final String INIT_SERVLET = "/init";
	
	public static final String ROOT_ADDRESS = "127.0.0.1";
	public static final String ROOT_PORT = "8080";
	
	public static final String REQUESTMETHOD_POST = "POST";
	public static final String REQUESTMETHOD_GET = "GET";
	public static final String REQUESTMETHOD_DELETE = "DELETE";
	
	public static final int SPLIT_METHOD_HALF = 0;
	public static final int SPLIT_METHOD_QUARTER = 1;
}