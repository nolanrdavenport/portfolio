import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Crate extends GameObject{
	private Game game;
	private Handler handler;
	public Crate(int x, int y, ID id, Handler handler,Game game) {
		super(x, y, id);
		this.handler = handler;
		this.game = game;
	}

	@Override
	public void tick() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject go = handler.object.get(i);
			if(getBounds().intersects(go.getBounds()) && go.getId() == ID.Player) {
				game.ammo += 50;
				handler.object.remove(this);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(x, y, 40, 40);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 40, 40);
	}
	
}
