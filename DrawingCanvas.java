import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class DrawingCanvas extends Canvas {

	private BufferStrategy buffer;
	private Graphics g;
	ArrayList<Vehicle> agents;
	ArrayList<JVector> food;
	ArrayList<JVector> poison;

	public DrawingCanvas(int width, int height) {
		setSize(width, height);

		//AGENTS
		agents = new ArrayList<Vehicle>();
		for (int i = 0; i < 10; i++) {
			int x = (int) (Math.random()*canvasSize().getX());
			int y = (int) (Math.random()*canvasSize().getY());
			agents.add(new Vehicle(x, y, this));
		}

		//FOOD
		food = new ArrayList<JVector>();
		for (int i = 0; i < 50; i++) {
			int x = (int) (Math.random()*canvasSize().getX());
			int y = (int) (Math.random()*canvasSize().getY());
			food.add(new JVector(x, y));
		}

		//POISON
		poison = new ArrayList<JVector>();
		for (int i = 0; i < 10; i++) {
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

			//Add food once in a while, use a timer
			if (Math.random()*100 < 5) {
				int x = (int) (Math.random()*canvasSize().getX());
				int y = (int) (Math.random()*canvasSize().getY());
				food.add(new JVector(x, y));
			}
			//Draw Food
			g.setColor(new Color(0, 255, 0));
			for (int i = 0; i < food.size(); i++) {
				g.fillOval(food.get(i).intX(), food.get(i).intY(), 8, 8);
			}

			//Draw poison
			g.setColor(new Color(255, 0, 0));
			for (int i = 0; i < poison.size(); i++) {
				g.fillOval(poison.get(i).intX(), poison.get(i).intY(), 8, 8);
			}

			//Draw agents
			for (int i = agents.size() - 1; i >= 0; i--) { //Loop backwards to check everything
				agents.get(i).boundaries(); //Repel the walls
				agents.get(i).applyBehaviours(food, poison);
				agents.get(i).update();
				agents.get(i).draw(g);

				if (agents.get(i).dead()) {
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