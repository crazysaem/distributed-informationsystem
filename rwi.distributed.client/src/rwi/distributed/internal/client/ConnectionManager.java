package rwi.distributed.internal.client;

public class ConnectionManager {

	ClientStub stub;
	
	protected void startup() {
		stub =  new ClientStub();
		int id = stub.register(1, 20, 20);
		stub.unregister(id);
	}
}
