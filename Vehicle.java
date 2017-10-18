import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class Vehicle {

	DrawingCanvas canvas;

	final int AGENT_WIDTH = 15;
	final int AGENT_HEIGHT = 15;

	double maxVelocity;
	double maxForce;
	double health;
	int borders;

	JVector position;
	JVector velocity;
	JVector acceleration;

	double[] dna;

	public Vehicle(int x, int y, DrawingCanvas canvas) {
		this.canvas = canvas;
		position = new JVector(x, y);
		velocity = new JVector(0, -2);
		acceleration = new JVector(0, 0);

		maxVelocity = 3;
		maxForce = 0.5;

		health = 1;
		borders = 20; //Appy a steering behaviour when going outside the screen

		dna = new double[2];
		dna[0] = (Math.random()*4) - 2;
		dna[1] = (Math.random()*4) - 2;
	}

	public void update() {

		health -= 0.005;

		velocity.add(acceleration);
		double speed = velocity.getMagnitude();
		double max = maxData(speed, maxVelocity);
		velocity.setMagnitude(max);
		position.add(velocity);
		acceleration.multiply(0);
	}

	public boolean dead() {
		return (this.health <= 0);
	}
	
	public void applyBehaviours(ArrayList<JVector> good, ArrayList<JVector> bad) {
		JVector steerG = this.eat(good, 0.2); //Eat food, gain health
		JVector steerB = this.eat(bad, -0.5); //Eat poison, get sick :(

		steerG.multiply(this.dna[0]);
		steerB.multiply(this.dna[1]);

		this.applyForce(steerG);
		this.applyForce(steerB);
	}
	

	public void applyForce(JVector force) {
		acceleration.add(force);
	}

	public void draw(Graphics g) { //Should design a lerp algorithm for changing the color with the health
		if (this.health < 0.5) {
			g.setColor(new Color(255, 0, 0));
		}
		else {
			g.setColor(new Color(0, 255, 0));
		}
		g.fillRect(position.intX(), position.intY(), AGENT_WIDTH, AGENT_WIDTH);
	}

	public void boundaries() { //When it gets closer to a wall, make a force to get it away
		JVector desired = null;

		if (this.position.intX() < borders) {
			desired = new JVector(this.maxVelocity, this.velocity.getY());
		}
		else if (this.position.intX() > canvas.getWidth() - borders) {
			desired = new JVector(-this.maxVelocity, this.velocity.getY());
		}

		if (this.position.intY() < borders) {
			desired = new JVector(this.velocity.getX(), this.maxVelocity);
		}
		else if (this.position.intY() > canvas.getHeight() - borders) {
			desired = new JVector(this.velocity.getX(), -this.maxVelocity);
		}

		if (desired != null) {
			desired.normalize();
			desired.multiply(this.maxVelocity);
			JVector steer = JVector.sub2Vecs(desired, this.velocity);
			double mag = steer.getMagnitude();
			double maxF = maxData(mag, this.maxForce);
			steer.setMagnitude(maxF);
			this.applyForce(steer);
		}
	}

	public JVector eat(ArrayList<JVector> list, double nutrition) {
		double record = Double.MAX_VALUE;
		int closestIndex = -1;
		for (int i = 0; i < list.size(); i++) {
			JVector dist = JVector.sub2Vecs(this.position, list.get(i));
			double mag = dist.getMagnitude();
			if (mag < record) {
				record = mag;
				closestIndex = i;
			}
		}


		// This is the moment of eating!
		if (record < 7) {
			list.remove(closestIndex);
			this.health += nutrition;
		}
		else if (closestIndex >= 0 && closestIndex < list.size()) {
			return this.seek(list.get(closestIndex));
		}

		return new JVector(0, 0); //There's nothing

	}

	public JVector seek(JVector target) {
		JVector desired =  JVector.sub2Vecs(target, this.position);
		desired.setMagnitude(maxVelocity);
		JVector steering = JVector.sub2Vecs(desired, this.velocity);
		//Limit to max force
		double mag = steering.getMagnitude();
		double maxF = maxData(mag, maxForce);
		steering.setMagnitude(maxF);
		return steering;
		//applyForce(steering);
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

	private double maxData(double value, double max) {
		if (value > max) {
			return max;
		}
		else {
			return value;
		}
	}

}