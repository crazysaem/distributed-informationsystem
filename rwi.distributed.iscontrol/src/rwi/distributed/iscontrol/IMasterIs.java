package rwi.distributed.iscontrol;

public interface IMasterIs {

	public String register(int type, float posX, float posY);

	public void unregister(int id);

	public String getInfo();
}