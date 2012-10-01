package rwi.distributed.core.interfaces.client;

public interface IVehicleClient extends IObjectClient {
	public void updatePos(int id, float posX, float posY);

	public void getRouteToPos(int id, float targetposX, float targetposY);
}
