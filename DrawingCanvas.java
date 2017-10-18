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
	//ArrayList<Food> foodArray;

	public DrawingCanvas(int width, int height) {
		setSize(width, height);
		agent = new Vehicle(this);
		/*
		for (int i = 0; i < 20; i++) {
			foodArray.add(new Food(this));
		}
		*/
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

			//Draw agent
			g.setColor(new Color(255, 0, 0));
			agent.borders();
			agent.update();
			agent.draw(g);

			//Draw Food
			/*
			g.setColor(new Color(0, 0, 255));
			for (int i = 0; i < foodArray.size(); i++) {
				foodArray.get(i).draw(g);
			}
			*/

		g.dispose();
		buffer.show();
	}

	public JVector canvasSize() {
		return new JVector(this.getWidth(), this.getHeight());
	}

}