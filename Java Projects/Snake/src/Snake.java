import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Snake extends JPanel implements KeyListener{
	
	Window window;
	SnakeHead snakeHead;
	public GameLoop gameLoop;
	ArrayList<SnakeBody> snakeBodies = new ArrayList<SnakeBody>();
	Random rand;
	Food food;
	boolean running = true;
	boolean justAte = false;
	boolean dead = false;
	int score = 0;
	
	public Snake() {
		rand = new Random();
		food = new Food((rand.nextInt(30)+5) * 20, (rand.nextInt(30)+5) * 20);
		window = new Window(800, 800);
		window.add(this);
		window.addKeyListener(this);
		//create head
		snakeHead = new SnakeHead(400, 400);
		//create first few bodies
		snakeBodies.add(new SnakeBody(400, 420));
		snakeBodies.add(new SnakeBody(400, 440));
		snakeBodies.add(new SnakeBody(400, 460));
		//start game loop
		gameLoop = new GameLoop();
		gameLoop.start();
		
	}
	
	public static void main(String[] args) {
		new Snake();
		
	}
	
	public void randomizeFood() {
		food.setX((rand.nextInt(30)+5) * 20);
		food.setY((rand.nextInt(30)+5) * 20);
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		
		Font font = new Font("Ariel", Font.BOLD, 20);
		g.setFont(font);
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, 800, 800);
		
		//outline
		g.setColor(new Color(0, 0, 255));
		g.drawRect(100, 100, 600, 600);
		
		//render head
		g.setColor(new Color(255, 0, 0));
		g.fillRect(snakeHead.getX(), snakeHead.getY(), 20, 20);
		
		//render bodies
		for(int i = 0; i < snakeBodies.size(); i++) {
			g.fillRect(snakeBodies.get(i).getX(), snakeBodies.get(i).getY(), 20, 20);
		}
		
		//render food
		g.setColor(new Color(0, 255, 0));
		g.fillRect(food.getX(), food.getY(), 20, 20);
		
		if(dead) {
			g.setColor(new Color(255, 0, 0));
			g.drawString("YOU DIED", 350, 50);
			
		}
		
		//display score
		g.drawString("SCORE: "+score, 350, 750);
	}

	
	
	
	public class GameLoop extends Thread implements Runnable{
		public void run() {
			while(running) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(justAte) {
					SnakeBody[] tempBodies = new SnakeBody[snakeBodies.size()];
					for(int i = 0; i < snakeBodies.size()-1; i++) {
						tempBodies[i] = snakeBodies.get(i);
					}
					//moveBodies
					for(int i = 1; i < snakeBodies.size(); i++) {
						snakeBodies.set(i, tempBodies[i-1]);
					}
					//movehead
					snakeBodies.set(0, new SnakeBody(snakeHead.getX(), snakeHead.getY()));
					snakeHead.moveHead();
					
					justAte = false;
				}else {
					SnakeBody[] tempBodies = new SnakeBody[snakeBodies.size()];
					for(int i = 0; i < snakeBodies.size(); i++) {
						tempBodies[i] = snakeBodies.get(i);
					}
					//moveBodies
					for(int i = 1; i < snakeBodies.size(); i++) {
						snakeBodies.set(i, tempBodies[i-1]);
					}
					//movehead
					snakeBodies.set(0, new SnakeBody(snakeHead.getX(), snakeHead.getY()));
					snakeHead.moveHead();
				}
				
				//check for death
				for(int i = 0; i < snakeBodies.size(); i++) {
					if(snakeHead.getX() == snakeBodies.get(i).getX() && snakeHead.getY() == snakeBodies.get(i).getY()) {
						dead = true;
						running = false;
					}
				}
				
				//check for loop
				if(snakeHead.getX() >= 700) {
					snakeHead.setX(100);
				}else if(snakeHead.getX() < 100) {
					snakeHead.setX(680);
				}
				
				if(snakeHead.getY() >= 700) {
					snakeHead.setY(100);
				}else if(snakeHead.getY() < 100) {
					snakeHead.setY(680);
				}
				
				if(food.getX() == snakeHead.getX() && food.getY() == snakeHead.getY()) {
					randomizeFood();
					snakeBodies.add(new SnakeBody(snakeBodies.get(snakeBodies.size()-1).getX(), snakeBodies.get(snakeBodies.size()-1).getY()));
					justAte = true;
					score++;
				}
				
				
				System.out.println("got to end of loop");
				//end
				repaint();
			}
		}
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			snakeHead.setDirection("up");
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			snakeHead.setDirection("down");
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			snakeHead.setDirection("left");
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			snakeHead.setDirection("right");
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
