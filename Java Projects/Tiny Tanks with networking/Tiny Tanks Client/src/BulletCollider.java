import java.awt.Graphics;
import java.awt.Rectangle;

public class BulletCollider extends GameObject{
	Player player;
	Handler handler;
	int size;
	public BulletCollider(int x, int y, ID id, Bullet bullet, Handler handler, int size) {
		super(x, y, id);
		this.size = size;
		this.player = player;
		this.handler = handler;
		// TODO Auto-generated constructor stub
	}

	public BulletCollider(int x, int y, ID id, EnemyBullet enemyBullet, Handler handler, int size) {
		// TODO Auto-generated constructor stub
		super(x, y, id);
		this.size = size;
		this.player = player;
		this.handler = handler;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x, y, size, size);
	}
	
	public boolean collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject go = handler.object.get(i);
			
			if(go.getId() == ID.Block) {
				if(getBounds().intersects(go.getBounds())) {
					//x += -velX;
					//y += -velY;
					return true;
				}
			}else {
				return false;
			}
		}
		return false;
		
		
		
	}

}