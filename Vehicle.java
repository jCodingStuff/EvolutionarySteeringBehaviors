import java.awt.Graphics;

public class Vehicle {

	DrawingCanvas canvas;

	final int AGENT_WIDTH = 20;
	final int AGENT_HEIGHT = 20;

	final double maxSpeed = 1;
	final double maxForce = 2;

	JVector position;
	JVector speed;
	JVector acceleration;

	public Vehicle(DrawingCanvas canvas) {
		this.canvas = canvas;
		position = new JVector((int) (Math.random()*canvas.getWidth()), (int) (Math.random()*canvas.getHeight()));
		speed = new JVector(0, 0);
		acceleration = new JVector(0, 0);
	}

	public void update() {
		speed = JVector.add(speed, acceleration);
		position = JVector.add(position, speed);
	}

	public void applyBehaviours() {
	}

	public void applyForce(JVector force) {
		acceleration = JVector.add(acceleration, force);
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

	public void seek(JVector goal) {
		JVector desired =  JVector.sub(goal, position);

		double mapspeed = 0;
		double desMag = desired.getMagnitude();
		
		if (desMag < 75) {
			mapspeed = mapData(desMag, 0, 75, 0, maxSpeed);
		}
		else {
			mapspeed = maxSpeed;
		}

		desired.setMagnitude(mapspeed);
		JVector steering = JVector.sub(desired, speed);
		steering.setMagnitude(maxForce);
		applyForce(steering);
	}

	private double mapData(double value, double min1, double max1, double min2, double max2) {
		double tempValue = value - min1;
		double interval1 = max1 - min1;
		double interval2 = max2 - min2;
		double perc = tempValue / interval1;
		double tempMap = perc * interval2;
		double mapped = tempMap + min2;
		return mapped;
	}

}