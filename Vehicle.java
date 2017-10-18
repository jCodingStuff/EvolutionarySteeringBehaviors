import java.awt.Graphics;

public class Vehicle {

	DrawingCanvas canvas;

	final int AGENT_WIDTH = 28;
	final int AGENT_HEIGHT = 28;

	JVector position;
	JVector speed;
	JVector acceleration;

	public Vehicle(DrawingCanvas canvas) {
		this.canvas = canvas;
		position = new JVector((int) (Math.random()*canvas.getWidth()), (int) (Math.random()*canvas.getHeight()));
		speed = new JVector((Math.random()*3) - 3, (Math.random()*3) - 3);
		acceleration = new JVector(0, 0);
	}

	public void update() {
		speed = JVector.add(acceleration, speed);
		position = JVector.add(speed, position);
	}

	public void draw(Graphics g) {
		g.fillRect(position.intX(), position.intY(), AGENT_WIDTH, AGENT_WIDTH);
	}

	public void borders() {
		if (position.intX() > canvas.getWidth()) {
			position.x = 0;
		}
		else if (position.intX() < 0) {
			position.x = canvas.getWidth();
		}
		else if (position.intY() > canvas.getHeight()) {
			position.y = 0;
		}
		else if (position.intY() < 0) {
			position.y = canvas.getHeight();
		}
	}

}