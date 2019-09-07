import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{
	private Handler handler;
	private Camera camera;
	private Game game;
	public MouseInput(Handler handler, Camera camera, Game game) {
		this.handler = handler;
		this.camera = camera;
		this.game = game;
	}
	/*
	public void mousePressed(MouseEvent e) {
		int mx = (int)(e.getX() + camera.getX());
		int my = (int)(e.getY() + camera.getY());
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject go = handler.object.get(i);
			
			if(go.getId() == ID.Player) {
				//int distance = (int) Math.sqrt(Math.pow((go.getX() - (mx+20)),2) + Math.pow((go.getY() - (my+20)),2));
				if(game.ammo > 0) {
					//handler.addObject(new Bullet(go.getX()+20, go.getY()+20, ID.Bullet, handler, mx, my));
					game.ammo--;
					System.out.println(game.ammo);
				}else {
					System.out.println("you are out of ammo!");
				}
				
			}
		}
	}
	*/
}
