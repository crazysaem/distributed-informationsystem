package rwi.distributed.core.classes;

public class RWIVehicle {

	float posX, posY;
	int type;
	int id;

	public RWIVehicle(float posX, float posY, int type, int id) {
		this.posX = posX;
		this.posY = posY;
		this.type = type;
		this.id = id;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public int getType() {
		return type;
	}

	public int getId() {
		return id;
	}

}
