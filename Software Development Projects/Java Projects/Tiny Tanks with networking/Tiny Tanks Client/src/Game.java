import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketPermission;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private Window window;
	private Thread thread;
	private Handler handler;
	private Player player;
	private Camera camera;
	private BufferedImage level = null;
	private int playersRemaining;
	private int numClients;
	public int ammo = 10;
	private boolean running;
	private String colorMain;
	private int port;
	private DatagramSocket socket;
	private String serverIP;

	private EnemyPlayer blueEnemy, redEnemy, greenEnemy, yellowEnemy;

	public Game(String color, int port, int numClients, String serverIP) {
		playersRemaining = numClients;
		this.serverIP = serverIP;
		this.numClients = numClients;
		this.port = port;
		this.colorMain = color;
		window = new Window(1280, 720, "Game", this);
		if (colorMain.equals("Blue"))
			camera = new Camera(0, 0);
		if (colorMain.equals("Red"))
			camera = new Camera(1280, 0);
		if (colorMain.equals("Green"))
			camera = new Camera(0, 720);
		if (colorMain.equals("Yellow"))
			camera = new Camera(1280, 720);

		handler = new Handler();

		this.addMouseListener(new MouseInput(handler, camera, this));
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/game world.png");
		start();
		loadLevel(level);
		try {
			new Thread(new ReceiveThread(this)).start();
			socket = new DatagramSocket(port);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void playerDeath() {
		playersRemaining--;
	}

	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		running = false;
		try {
			window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				// updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
				// updates = 0;
			}
		}
		stop();
	}

	public void tick() {
		if(playersRemaining <= 1) {
			//stop();
		}
		
		System.out.println(playersRemaining);
		for (int i = 0; i < handler.object.size(); i++) {
			if (handler.object.get(i).getId() == ID.Player) {
				camera.tick(handler.object.get(i));
				
			}
			
		}
		
		
		handler.tick();
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		// start of drawing things

		g.setColor(Color.gray);
		g.fillRect(0, 0, 1280, 720);
		g2d.translate(-camera.getX(), -camera.getY());
		handler.render(g);

		g2d.translate(camera.getX(), camera.getY());
		// end of drawing things
		g.dispose();
		bs.show();

	}

	// loading the level
	private void loadLevel(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		for (int xx = 0; xx < w; xx++) {
			for (int yy = 0; yy < h; yy++) {
				Color color = new Color(image.getRGB(xx, yy));
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				if (red == 255 && green == 255 && blue == 255) {
					handler.addObject(new Block(xx * 40, yy * 40, ID.Block));
				}
				if (red == 255 && green == 0 && blue == 0) {
					// handler.addObject(new Enemy(xx *40, yy * 40, ID.Enemy, handler));
				}
				if (red == 0 && green == 0 && blue == 255) {
					handler.addObject(new Crate(xx * 40, yy * 40, ID.Crate, handler, this));
				}
			}
		}

		for (int xx = 0; xx < w; xx++) {
			for (int yy = 0; yy < h; yy++) {
				Color color = new Color(image.getRGB(xx, yy));
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				if (red == 64 && green == 64 && blue == 64 && colorMain.equals("Blue")) {
					player = new Player(xx * 40, yy * 40, ID.Player, handler, colorMain, port, serverIP, this);
					handler.addObject(player);
					this.addKeyListener(new KeyInput(handler, player));
				}
				if (red == 63 && green == 63 && blue == 63 && colorMain.equals("Red")) {
					player = new Player(xx * 40, yy * 40, ID.Player, handler, colorMain, port, serverIP, this);
					handler.addObject(player);
					this.addKeyListener(new KeyInput(handler, player));
				}
				if (red == 62 && green == 62 && blue == 62 && colorMain.equals("Green")) {
					player = new Player(xx * 40, yy * 40, ID.Player, handler, colorMain, port, serverIP, this);
					handler.addObject(player);
					this.addKeyListener(new KeyInput(handler, player));
				}
				if (red == 61 && green == 61 && blue == 61 && colorMain.equals("Yellow")) {
					player = new Player(xx * 40, yy * 40, ID.Player, handler, colorMain, port, serverIP, this);
					handler.addObject(player);
					this.addKeyListener(new KeyInput(handler, player));
				}
			}
		}

	}

	public class ReceiveThread implements Runnable {
		private Game game;

		public ReceiveThread(Game game) {
			this.game = game;
		}

		public void run() {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			while (true) {
				try {

					byte[] buf = new byte[2048];
					DatagramPacket p = new DatagramPacket(buf, buf.length);
					socket.receive(p);
					String msg = new String(p.getData()).trim();
					String msgCopy = msg;
					String[] msgs = msg.split("_");
					
					
					if (msgs[0].equals("shoot")) {
						handler.addObject(new EnemyBullet(Integer.parseInt(msgs[1]) + 20, Integer.parseInt(msgs[2]) + 20,
								ID.Bullet, handler, Integer.parseInt(msgs[3]), Integer.parseInt(msgs[4]), game));

					}

					if (msgs[0].equals("state")) {

						int x = Integer.parseInt(msgs[2]);
						int y = Integer.parseInt(msgs[3]);
						float rotation = Float.parseFloat(msgs[4]);

						if (msgs[1].equals("Blue") && !colorMain.equals("Blue")) {
							if (blueEnemy == null) {
								blueEnemy = new EnemyPlayer(x, y, ID.Enemy, handler, "Blue");
								handler.addObject(blueEnemy);
							} else {

								blueEnemy.setX(x);
								blueEnemy.setY(y);
								blueEnemy.setRotation(rotation);
							}
						}

						if (msgs[1].equals("Red") && !colorMain.equals("Red")) {
							if (redEnemy == null) {
								redEnemy = new EnemyPlayer(x, y, ID.Enemy, handler, "Red");
								handler.addObject(redEnemy);
							} else {
								redEnemy.setX(x);
								redEnemy.setY(y);
								redEnemy.setRotation(rotation);
							}
						}

						if (msgs[1].equals("Green") && !colorMain.equals("Green")) {
							if (greenEnemy == null) {
								greenEnemy = new EnemyPlayer(x, y, ID.Enemy, handler, "Green");
								handler.addObject(greenEnemy);
							} else {
								greenEnemy.setX(x);
								greenEnemy.setY(y);
								greenEnemy.setRotation(rotation);
							}
						}

						if (msgs[1].equals("Yellow") && !colorMain.equals("Yellow")) {
							if (yellowEnemy == null) {
								yellowEnemy = new EnemyPlayer(x, y, ID.Enemy, handler, "Yellow");
								handler.addObject(yellowEnemy);
							} else {
								yellowEnemy.setX(x);
								yellowEnemy.setY(y);
								yellowEnemy.setRotation(rotation);
							}
						}

					}

				} catch (Exception e) {
					System.err.println(e);
				}
			}
		}
	}
}
