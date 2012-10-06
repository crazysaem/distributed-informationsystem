package rwi.distributed.internal.client;

import rwi.distributed.core.variables.RwiCommunication;

public class ClientStub {
	private Client client;
	
	public ClientStub(){
		client = new Client();
	}
	

	public int register(int type,float posX,float posY){
		String message = "";
		message += generateParamter(RwiCommunication.PARAMETER_TYPE, type);
		message +=generateParamter(RwiCommunication.PARAMETER_POSX, posX);
		message +=generateParamter(RwiCommunication.PARAMETER_POSY, posY);		
		return client.register(message);		
	}
	
	public void unregister(int id){
		String message= "";
		message+=generateParamter(RwiCommunication.PARAMETER_ID, id);
		client.unregister(message);
	} 
	
	private String generateParamter(String parameter,float value){
		return parameter+"="+String.valueOf(value)+"&";
	}
	private String generateParamter(String parameter,int value){
		return parameter+"="+String.valueOf(value)+"&";
	}
	private String generateParamter(String parameter,String value){
		return parameter+"="+value+"&";
	}
}
