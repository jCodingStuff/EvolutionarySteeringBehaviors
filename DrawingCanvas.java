import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class DrawingCanvas extends Canvas {

	private BufferStrategy buffer;
	private Graphics g;
	ArrayList<Vehicle> agents;
	ArrayList<JVector> food;
	ArrayList<JVector> poison;
	final int CIRCLE = 6;

	public DrawingCanvas(int width, int height) {
		setSize(width, height);
		setFocusable(true);

		//AGENTS
		agents = new ArrayList<Vehicle>();
		for (int i = 0; i < 20; i++) {
			int x = (int) (Math.random()*canvasSize().getX());
			int y = (int) (Math.random()*canvasSize().getY());
			double[] dna = new double[4];
			// Attraction to food
			dna[0] = (Math.random()*4) - 2;
			// Attraction to poison
			dna[1] = (Math.random()*4) - 2;
			// Food perception
			dna[2] = (int)(Math.random()*101);
			// Poison perception
			dna[3] = (int)(Math.random()*101);
			agents.add(new Vehicle(x, y, dna, this));
		}

		//FOOD
		food = new ArrayList<JVector>();
		for (int i = 0; i < 45; i++) {
			int x = (int) (Math.random()*canvasSize().getX());
			int y = (int) (Math.random()*canvasSize().getY());
			food.add(new JVector(x, y));
		}

		//POISON
		poison = new ArrayList<JVector>();
		for (int i = 0; i < 20; i++) {
			int x = (int) (Math.random()*canvasSize().getX());
			int y = (int) (Math.random()*canvasSize().getY());
			poison.add(new JVector(x, y));
		}
	}

	public void draw() {
		buffer = getBufferStrategy();

		if (buffer == null) {
			createBufferStrategy(2); //If more resources are needed, increase the number
			return;
		}

		g = buffer.getDrawGraphics(); //Draw in the buffer

			//DRAW Background
			g.setColor(new Color(24, 23, 69));
			g.fillRect(0, 0, canvasSize().intX(), canvasSize().intY());

			//Add food once in a while
			if (Math.random()*100 < 5) {
				int x = (int) (Math.random()*canvasSize().getX());
				int y = (int) (Math.random()*canvasSize().getY());
				food.add(new JVector(x, y));
			}

			//Add poison once in a while
			if (Math.random()*100 < 2) {
				int x = (int) (Math.random()*canvasSize().getX());
				int y = (int) (Math.random()*canvasSize().getY());
				poison.add(new JVector(x, y));
			}

			//Draw Food
			g.setColor(new Color(0, 255, 0));
			for (int i = 0; i < food.size(); i++) {
				g.fillOval(food.get(i).intX() - CIRCLE/2, food.get(i).intY() - CIRCLE/2, CIRCLE, CIRCLE);
			}

			//Draw poison
			g.setColor(new Color(255, 0, 0));
			for (int i = 0; i < poison.size(); i++) {
				g.fillOval(poison.get(i).intX() - CIRCLE/2, poison.get(i).intY() - CIRCLE/2, CIRCLE, CIRCLE);
			}

			//Draw agents
			for (int i = agents.size() - 1; i >= 0; i--) { //Loop backwards to check everything
				agents.get(i).boundaries(); //Repel the walls
				agents.get(i).applyBehaviours(food, poison);
				agents.get(i).update();
				agents.get(i).draw(g);

				Vehicle newAgent = agents.get(i).cloneMe();
				if (newAgent != null) {
					agents.add(newAgent); //As looping the array backwards, I add thing to the end and I can get to the beginning
				}

				if (agents.get(i).dead()) {
					int x = agents.get(i).position.intX();
					int y = agents.get(i).position.intY();
					food.add(new JVector(x, y));
					agents.remove(i);
				}
			}

		g.dispose();
		buffer.show();
	}

	public JVector canvasSize() {
		return new JVector(this.getWidth(), this.getHeight());
	}

}
