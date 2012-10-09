package rwi.distributed.core.interfaces.server;


public interface IISManager {

	public IIS generateIS(float minX, float maxX, float minY, float maxY, int id);

	public void destroyIS(IIS tobedestroyed);

}