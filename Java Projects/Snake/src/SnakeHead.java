
public class SnakeHead {
	
	String direction = "up";
	int x, y;
	public SnakeHead(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public void setDirection(String direction) {		
		this.direction = direction;
	}
	
	public void moveHead() {
		if(direction.equals("up")) {
			y -= 20;
		}
		if(direction.equals("down")) {
			y += 20;
		}
		if(direction.equals("left")) {
			x -= 20;
		}
		if(direction.equals("right")) {
			x += 20;
		}
	}
}
