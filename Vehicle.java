import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class Vehicle {

	DrawingCanvas canvas;

	final int AGENT_WIDTH = 10;
	final int AGENT_HEIGHT = 10;
	final int mr = 1; //Mutation probability, by 100

	double maxVelocity;
	double maxForce;
	double health;
	int borders;

	JVector position;
	JVector velocity;
	JVector acceleration;

	double[] dna;

	public Vehicle(int x, int y, double[] dna, DrawingCanvas canvas) {
		this.canvas = canvas;
		position = new JVector(x, y);
		velocity = new JVector(0, -2);
		acceleration = new JVector(0, 0);

		maxVelocity = 3;
		maxForce = 0.5;

		health = 1;
		borders = 20; //Appy a steering behaviour when going outside the screen

		this.dna = new double[4];
		this.dna[0] = dna[0];
		this.dna[1] = dna[1];
		this.dna[2] = dna[2];
		this.dna[3] = dna[3];
	}

	public void update() {

		health -= 0.006;

		velocity.add(acceleration);
		double speed = velocity.getMagnitude(); //Limit the speed
		double max = maxData(speed, maxVelocity);
		velocity.setMagnitude(max);
		position.add(velocity);
		acceleration.multiply(0);
	}

	public boolean dead() {
		return (this.health <= 0);
	}

	public void applyBehaviours(ArrayList<JVector> good, ArrayList<JVector> bad) {
		JVector steerG = this.eat(good, 0.3, this.dna[2]); //Eat food, gain health
		JVector steerB = this.eat(bad, -0.5, this.dna[3]); //Eat poison, get sick :(

		steerG.multiply(this.dna[0]);
		steerB.multiply(this.dna[1]);

		this.applyForce(steerG);
		this.applyForce(steerB);
	}

	public Vehicle cloneMe() {
		if (Math.random()*10000 < 20) {
			//Mutation
			if (Math.random()*100 <= mr) {
				this.dna[0] += Math.random()*0.2 - 0.1;
			}
			if (Math.random()*100 <= mr) {
				this.dna[1] += Math.random()*0.2 - 0.1;
			}
			if (Math.random()*100 <= mr) {
				this.dna[2] += Math.random()*20 - 10;
			}
			if (Math.random()*100 <= mr) {
				this.dna[3] += Math.random()*20 - 10;
			}
			return new Vehicle(this.position.intX(), this.position.intY(), this.dna, canvas);
		}
		else {
			return null;
		}
	}

	public void applyForce(JVector force) {
		acceleration.add(force);
	}

	public void draw(Graphics g) {
		float red = Utils.constrain(1f - ((float) this.health), 0f, 1f);
		float green = Utils.constrain((float) this.health, 0f, 1f);
		g.setColor(new Color(red, green, 0f));
		g.fillRect(position.intX() - AGENT_WIDTH/2, position.intY() - AGENT_WIDTH/2, AGENT_WIDTH, AGENT_WIDTH);
		g.setColor(new Color(0, 255, 0)); //Food perception
		g.drawOval(position.intX() - (int)this.dna[2], position.intY() - (int)this.dna[2], (int)(this.dna[2]*2), (int)(this.dna[2]*2)); //Put the centre of the circle in the centre
		g.setColor(new Color(255, 0, 0)); //Poison perception
		g.drawOval(position.intX() - (int)this.dna[3], position.intY() - (int)this.dna[3], (int)(this.dna[3]*2), (int)(this.dna[3]*2));
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

	public JVector eat(ArrayList<JVector> list, double nutrition, double perception) {
		double record = Double.MAX_VALUE;
		JVector closest = null;
		for (int i = list.size() - 1; i >= 0; i--) {
			JVector dist = JVector.sub2Vecs(this.position, list.get(i));
			double mag = dist.getMagnitude();

			// This is the moment of eating!
			if (mag < 4) {
				list.remove(i);
				this.health += nutrition;
			}
			else {
				if (mag < record && mag < perception) {
					record = mag;
					closest = list.get(i);
				}
			}
		}

		if (closest != null) {
			return this.seek(closest);
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

	private double maxData(double value, double max) {
		if (value > max) {
			return max;
		}
		else {
			return value;
		}
	}

}
