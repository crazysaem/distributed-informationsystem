package rwi.distributed.internal.iscontrol;

import rwi.distributed.core.interfaces.server.IIS;

public class RangedIS {
	
	private IIS infosystem;
	private float minX, maxX, minY, maxY;

	public RangedIS(float minX, float maxX, float minY, float maxY, IIS infosystem) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.infosystem = infosystem;
	}

	public IIS isInRange(float posX, float posY) {
		if ((minX < posX && posX < maxX) && (minY < posY && posY < maxY)) {
			return this.infosystem;
		}
		return null;
	}

}
