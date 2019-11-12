import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class EnemyPlayer extends GameObject{
	private float rotation = 0;
	private String color;
	int spot = 0;
	
	private BufferedImage image;
	Handler handler;
	public EnemyPlayer(int x, int y, ID id, Handler handler, String color) {
		
		super(x, y, id);
		this.color = color;
		try {
			if(color.equals("Blue")) image = ImageIO.read(getClass().getResource("/BlueTank.png"));
			if(color.equals("Red")) image = ImageIO.read(getClass().getResource("/RedTank.png"));
			if(color.equals("Green")) image = ImageIO.read(getClass().getResource("/GreenTank.png"));
			if(color.equals("Yellow")) image = ImageIO.read(getClass().getResource("/YellowTank.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.handler = handler;
		
		
	}
	
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setRotation(float rotation) {this.rotation = rotation;}

	@Override
	public void tick() {
		
		
	}
	
	@Override
	public void render(Graphics g) {
		System.out.println(color+" tank "+x+" "+ y);
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(Math.toRadians(rotation), image.getWidth()/2, image.getHeight()/2);
		
		Graphics2D graphics2d = (Graphics2D)g;
		graphics2d.drawImage(image, at, null);
		
	}
	
	

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 40, 40);
	}
	
	//networking
	

}
