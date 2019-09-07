import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyBullet extends GameObject {

	private Handler handler;
	private Game game;
	public EnemyBullet(int x, int y, ID id, Handler handler, int mx, int my, Game game) {
		super(x, y, id);
		this.game = game;
		this.handler = handler;

		velX = mx;
		velY = my;
	}

	@Override
	public void tick() {
		new Thread(new bulletContact()).start();

		BulletCollider point1 = new BulletCollider(x + 5, y, ID.BulletCollider, this, handler, 1);
		BulletCollider point2 = new BulletCollider(x + 10, y + 5, ID.BulletCollider, this, handler, 1);
		BulletCollider point3 = new BulletCollider(x + 5, y + 10, ID.BulletCollider, this, handler, 1);
		BulletCollider point4 = new BulletCollider(x, y + 5, ID.BulletCollider, this, handler, 1);
		if (point1.collision()) {
			velY = -velY;
		}
		if (point2.collision()) {
			velX = -velX;
		}
		if (point3.collision()) {
			velY = -velY;
		}
		if (point4.collision()) {
			velX = -velX;
		}

		x += velX;
		y += velY;

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillOval(x - 5, y - 5, 10, 10);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 5, 5);
	}
	
	public class bulletContact implements Runnable{
		
		public void run() {
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject go = handler.object.get(i);
				if (go.getId() == ID.Player) {
					game.playerDeath();
					if (getBounds().intersects(go.getBounds())) {
						handler.object.remove(go);
					}
				}
				if (go.getId() == ID.Enemy) {
					
					if (getBounds().intersects(go.getBounds())) {
						handler.object.remove(go);
					}
				}
			}
		}
	}

}
