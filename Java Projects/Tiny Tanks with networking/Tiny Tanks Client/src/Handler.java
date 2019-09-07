import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	private boolean up = false, down = false, right = false, left = false;
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void tick() {
		for(int i = 0; i < object.size(); i++) {
			object.get(i).tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < object.size(); i++) {
			object.get(i).render(g);
		}
		
	}
	
	
	public void addObject(GameObject go) {
		object.add(go);
	}
	
	public void removeObject(GameObject go) {
		object.remove(go);
	}
}
