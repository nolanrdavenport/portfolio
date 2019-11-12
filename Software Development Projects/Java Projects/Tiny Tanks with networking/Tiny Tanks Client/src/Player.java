import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import javax.imageio.ImageIO;
import javax.tools.ForwardingFileObject;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.stream.XMLEventFactory;

public class Player extends GameObject {
	private float rotation = 0;
	private boolean forward = false;
	private boolean backward = false;
	private PlayerCollider collider;
	private int port;
	private String color;
	private DatagramSocket socket;
	private String serverIP;
	private Game game;
	int spot = 0;

	private BufferedImage image;
	Handler handler;

	public Player(int x, int y, ID id, Handler handler, String color, int port, String serverIP, Game game) {

		super(x, y, id);
		this.game = game;
		this.serverIP = serverIP;
		this.color = color;
		this.port = port;
		try {
			socket = new DatagramSocket(port + 5);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new Thread(new broadcastLocation()).start();
		try {
			if (color.equals("Blue"))
				image = ImageIO.read(getClass().getResource("/BlueTank.png"));
			if (color.equals("Red"))
				image = ImageIO.read(getClass().getResource("/RedTank.png"));
			if (color.equals("Green"))
				image = ImageIO.read(getClass().getResource("/GreenTank.png"));
			if (color.equals("Yellow"))
				image = ImageIO.read(getClass().getResource("/YellowTank.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.handler = handler;

		collider = new PlayerCollider(x, y, ID.PlayerCollider, this, handler, 40);

	}

	@Override
	public void tick() {
		// collisions
		PlayerCollider point1 = new PlayerCollider(x, y, ID.PlayerCollider, this, handler, 1);
		PlayerCollider point2 = new PlayerCollider(x + 40, y, ID.PlayerCollider, this, handler, 1);
		PlayerCollider point3 = new PlayerCollider(x, y + 40, ID.PlayerCollider, this, handler, 1);
		PlayerCollider point4 = new PlayerCollider(x + 40, y + 40, ID.PlayerCollider, this, handler, 1);
		if (velY <= 0 && !point1.collision() && !point2.collision()) {
			y += Math.round(velY);
		} else if (velY > 0 && !point3.collision() && !point4.collision()) {
			y += Math.round(velY);
		}
		if (velX >= 0 && !point2.collision() && !point4.collision()) {
			x += Math.round(velX);
		} else if (velX < 0 && !point1.collision() && !point3.collision()) {
			x += Math.round(velX);
		}

		// keypress
		if (handler.isUp())
			forward = true;
		else if (!handler.isDown())
			forward = false;

		if (handler.isDown())
			backward = true;
		else if (!handler.isUp())
			backward = false;

		if (handler.isLeft()) {
			rotation -= 4;
		} else if (!handler.isRight())
			velX = 0;

		if (handler.isRight()) {
			rotation += 4;
		} else if (!handler.isLeft())
			velX = 0;

		if (forward) {
			velX = (float) (5 * Math.sin(Math.toRadians(rotation)));
			velY = -(float) (5 * Math.cos(Math.toRadians(rotation)));
		} else if (backward) {
			velX = -(float) (5 * Math.sin(Math.toRadians(rotation)));
			velY = (float) (5 * Math.cos(Math.toRadians(rotation)));
		} else {
			velX = 0;
			velY = 0;
		}

	}
	
	
	
	public void shoot() {
		float mxi = (float) (7 * Math.sin(Math.toRadians(rotation)));
		float myi = -(float) (7 * Math.cos(Math.toRadians(rotation)));
		int mx = Math.round(mxi);
		int my = Math.round(myi);
		handler.addObject(new Bullet(x + 20, y + 20, ID.Bullet, handler, mx, my, game));
		
		// networking
		try {
			InetAddress IP = InetAddress.getByName(serverIP);
			int port = 50000;
			String state = "shoot_"+x+"_"+y+"_"+mx+"_"+my;
			byte[] buf = state.getBytes();
			DatagramPacket packet = new DatagramPacket(buf, buf.length, IP, port);
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(Graphics g) {
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(Math.toRadians(rotation), image.getWidth() / 2, image.getHeight() / 2);

		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.drawImage(image, at, null);

	}

	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, 40, 40);
	}

	// networking
	public class broadcastLocation implements Runnable {
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000 / 60);
					InetAddress IP = InetAddress.getByName(serverIP);
					int port = 50000;
					String state = "state_" + color + "_" + x + "_" + y + "_" + rotation;
					byte[] buf = state.getBytes();
					DatagramPacket packet = new DatagramPacket(buf, buf.length, IP, port);
					socket.send(packet);
				} catch (Exception e) {
				}
			}
		}
	}

}
