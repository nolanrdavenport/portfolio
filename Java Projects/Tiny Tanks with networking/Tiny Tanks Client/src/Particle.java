import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Particle extends GameObject{
	Random rand = new Random();
	Handler handler;
	Player player;
	public Particle(int x, int y, ID id, Handler handler, Player player) {
		super(x, y, id);
		this.handler = handler;
		this.player = player;
		super.velY = rand.nextFloat()+rand.nextInt(4)-2;
		super.velX = rand.nextFloat()+rand.nextInt(4)-2;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		super.velX = velX + rand.nextFloat()-.5f;
		super.velY = velY + rand.nextFloat()-.5f;
		for(int i = 0; i < handler.object.size(); i++) {
			//particles
			if(handler.object.get(i).id == ID.Particle && (handler.object.get(i).getX() >= 1280 || handler.object.get(i).getX() <= 0 || handler.object.get(i).getY() >= 720 || handler.object.get(i).getY() <= 0)) {
				handler.object.remove(i);
				i--;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		float distance;
		distance = (float)Math.sqrt(Math.pow((x - (player.getX()+25)),2) + Math.pow((y - (player.getY()+25)),2));
		if(distance >= 255) {
			distance = 255;
		}
		if(distance <= 0) {
			distance = 0;
		}
		g.setColor(new Color(255-(int)distance, 255-(int)distance, 255-(int)distance));
		g.fillRect(x, y, 2, 2);
	}

	

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 2, 2);
	}
	
}
