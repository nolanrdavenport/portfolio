
public class Ship {
	int x, y;
	float xVel, yVel;
	Vertex center;
	float angle = -90;
	int stepAngle = 0;
	float hVel = 0.0f;
	boolean thrust = false;
	
	Vertex[] vertices = new Vertex[3];
	public Vertex[] thrustVertices = new Vertex[3];
	public Ship(int x, int y) {
		this.x = x;
		this.y = y;
		
		vertices[0] = new Vertex(x, y);
		vertices[1] = new Vertex(x-20, y+34);
		vertices[2] = new Vertex(x+20, y+34);
		
		thrustVertices[0] = new Vertex(x-5, y+30);
		thrustVertices[1] = new Vertex(x+5, y+30);
		thrustVertices[2] = new Vertex(x, y+45);
		
		center = new Vertex(x, y+17);
		
	}
	
	public Vertex[] getVertices() {
		return vertices;
	}
	
	public Vertex getCenter() {
		return center;
	}
	
	public void turn(int stepAngle) {
		this.stepAngle = stepAngle;
	}
	
	public void setThruster(boolean a) {
		thrust = a;
	}
	
	public float calcHVel(float ach) {
		float max = 3.5f;
		
		max = max - ach * 2;
		
		return max;
	}
	
	public void move() {
		angle += stepAngle;
		//thrusting
		
		if(thrust) {
			hVel += calcHVel(hVel);
			xVel = (float) (xVel + (hVel * Math.cos(Math.toRadians(angle)))/3);
			yVel = (float) (yVel + (hVel * Math.sin(Math.toRadians(angle)))/3);
		}
		for(int i = 0; i < vertices.length; i++) {
			vertices[i].setY(vertices[i].getY() + Math.round(yVel));
			vertices[i].setX(vertices[i].getX() + Math.round(xVel));
			
			thrustVertices[i].setY(thrustVertices[i].getY() + Math.round(yVel));
			thrustVertices[i].setX(thrustVertices[i].getX() + Math.round(xVel));
		}
		center.setX(center.getX() + Math.round(xVel));
		center.setY(center.getY() + Math.round(yVel));
		
		for(int i = 0; i < vertices.length; i++) {
			if(i == 0) {
				//first vertex 
				float xDifT = 17 * (float)Math.cos(Math.toRadians(angle));
				int xDif = Math.round(xDifT);
				float yDifT = 17 * (float)Math.sin(Math.toRadians(angle));
				int yDif = Math.round(yDifT);
				
				vertices[0].setX(center.getX() + xDif);
				vertices[0].setY(center.getY() + yDif);
				
			}if(i == 1) {
				//first vertex 
				float xDifT = 17 * (float)Math.cos(Math.toRadians(angle+120));
				int xDif = Math.round(xDifT);
				float yDifT = 17 * (float)Math.sin(Math.toRadians(angle+120));
				int yDif = Math.round(yDifT);
				
				
				vertices[i].setX(center.getX() + xDif);
				vertices[i].setY(center.getY() + yDif);
				
			}if(i == 2) {
				//first vertex 
				float xDifT = 17 * (float)Math.cos(Math.toRadians(angle+240));
				int xDif = Math.round(xDifT);
				float yDifT = 17 * (float)Math.sin(Math.toRadians(angle+240));
				int yDif = Math.round(yDifT);
				
				vertices[i].setX(center.getX() + xDif);
				vertices[i].setY(center.getY() + yDif);
				
			}
		}
		
		for(int i = 0; i < thrustVertices.length; i++) {
			if(i == 0) {
				//first vertex 
				float xDifT = 15 * (float)Math.cos(Math.toRadians(angle + 189));
				int xDif = Math.round(xDifT);
				float yDifT = 15 * (float)Math.sin(Math.toRadians(angle + 189));
				int yDif = Math.round(yDifT);
				
				thrustVertices[i].setX(center.getX() + xDif);
				thrustVertices[i].setY(center.getY() + yDif);
				
			}if(i == 1) {
				//first vertex 
				float xDifT = 15 * (float)Math.cos(Math.toRadians(angle+171));
				int xDif = Math.round(xDifT);
				float yDifT = 15 * (float)Math.sin(Math.toRadians(angle+171));
				int yDif = Math.round(yDifT);
				
				
				thrustVertices[i].setX(center.getX() + xDif);
				thrustVertices[i].setY(center.getY() + yDif);
				
			}if(i == 2) {
				//first vertex 
				float xDifT = 30 * (float)Math.cos(Math.toRadians(angle+180));
				int xDif = Math.round(xDifT);
				float yDifT = 30 * (float)Math.sin(Math.toRadians(angle+180));
				int yDif = Math.round(yDifT);
				
				thrustVertices[i].setX(center.getX() + xDif);
				thrustVertices[i].setY(center.getY() + yDif);
				
			}
		}
		
		
	}
	
}
