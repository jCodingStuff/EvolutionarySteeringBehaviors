import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class DrawingCanvas extends Canvas {

	private BufferStrategy buffer;
	private Graphics g;
	Vehicle agent;
	ArrayList<Food> foodArray;

	public DrawingCanvas(int width, int height) {
		setSize(width, height);
		agent = new Vehicle(this);
		foodArray = new ArrayList<Food>();
		
		for (int i = 0; i < 10; i++) {
			foodArray.add(new Food(this));
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
			
			//Draw Food
			g.setColor(new Color(0, 255, 0));
			for (int i = 0; i < foodArray.size(); i++) {
				foodArray.get(i).draw(g);
			}

			//Draw agent
			g.setColor(new Color(255, 0, 0));
			agent.borders();

			JVector closestFood = new JVector(0, 0);

				//Checking for the nearest one
				JVector closestVector = JVector.sub(new JVector(Double.MAX_VALUE, Double.MAX_VALUE), agent.position);
				double closestMagnitude = closestVector.getMagnitude();
				for (int i = 0; i < foodArray.size(); i++) {
					JVector optionVector = JVector.sub(foodArray.get(i).position, agent.position);
					double optionMagnitude = optionVector.getMagnitude();
					if (optionMagnitude < closestMagnitude) {
						closestMagnitude = optionMagnitude;
						closestFood = foodArray.get(i).position;
					}
				}

			//Seek
			agent.seek(closestFood);
			agent.update();
			agent.draw(g);

			//Delete food if eaten
			for (int i = foodArray.size() - 1; i >= 0; i--) {
				JVector distFoodAgent = JVector.sub(foodArray.get(i).position, agent.position);
				double magFoodAgent = distFoodAgent.getMagnitude();
				if (magFoodAgent < 10) {
					foodArray.remove(i);
				}
			}		

		g.dispose();
		buffer.show();
	}

	public JVector canvasSize() {
		return new JVector(this.getWidth(), this.getHeight());
	}

}