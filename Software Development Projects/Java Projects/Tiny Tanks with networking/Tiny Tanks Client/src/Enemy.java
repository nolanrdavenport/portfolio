import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends GameObject{
	private Handler handler;
	Random rand = new Random();
	int choose = 0;
	int hp = 100;
	public Enemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		choose = rand.nextInt(10);
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject go = handler.object.get(i);
			if(go.getId() == ID.Block) {
				if(getBounds().intersects(go.getBounds())) {
					x += -velX;
					y += -velY;
				}
			}
			
			if(go.getId() == ID.Bullet) {
				if(getBounds().intersects(go.getBounds())) {
					hp -= 50;
					handler.removeObject(go);
				}
			}
		}
		if(hp <= 0) {
			handler.removeObject(this);
		}
		if(choose == 0) {
			velX = (rand.nextInt(4  - -4) + -4);
			velY = (rand.nextInt(4  - -4) + -4);
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect(x, y, 40, 40);
		
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 40, 40);
	}
	public Rectangle getBoundsBig() {
		return new Rectangle(x-20, y-20, 80, 80);
	}

}
