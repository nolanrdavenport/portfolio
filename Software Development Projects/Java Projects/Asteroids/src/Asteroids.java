import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Asteroids extends JPanel implements KeyListener{
	Window gameWindow = new Window(600, 600);
	Ship ship = new Ship(300, 300);
	GameLoop gameLoop = new GameLoop();
	boolean running = true;
	public Asteroids() {
		gameWindow.add(this);
		gameWindow.addKeyListener(this);
		gameLoop.start();
	}
	
	public static void main(String[] args) {
		new Asteroids();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, 600, 600);
		
		g.setColor(new Color(255,255,255));
		//ship
		Vertex[] vertices = ship.getVertices();
		g.drawLine(vertices[0].x, vertices[0].y, vertices[1].x, vertices[1].y);
		g.drawLine(vertices[1].x, vertices[1].y, vertices[2].x, vertices[2].y);
		g.drawLine(vertices[2].x, vertices[2].y, vertices[0].x, vertices[0].y);
		
		if(ship.thrust == true) {
			g.drawLine(ship.thrustVertices[0].x, ship.thrustVertices[0].y, ship.thrustVertices[1].x, ship.thrustVertices[1].y);
			g.drawLine(ship.thrustVertices[1].x, ship.thrustVertices[1].y, ship.thrustVertices[2].x, ship.thrustVertices[2].y);
			g.drawLine(ship.thrustVertices[2].x, ship.thrustVertices[2].y, ship.thrustVertices[0].x, ship.thrustVertices[0].y);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			ship.turn(6);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			ship.turn(-6);
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			ship.setThruster(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			ship.turn(0);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			ship.turn(0);
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			ship.setThruster(false);
		}
		
	}
	
	
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	public class GameLoop extends Thread implements Runnable{
		public void run() {
			while(running) {
				try {
					Thread.sleep(1000/30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ship.move();
				
				//end
				repaint();
			}
		}
	}
}
