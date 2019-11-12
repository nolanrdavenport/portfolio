
public class Camera {
	private float x,y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject object) {
		x += ((object.getX() - x) - 1280/2 +20)* 0.05f;
		y += ((object.getY() - y)- 720/2 +20)* 0.05f;
		
		if(x <= 0) x = 0;
		if(x >= 1280+5) x = 1280+5;
		
		if(y <= 0) y = 0;
		if(y >= 720+25) y = 720+25;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	
}
