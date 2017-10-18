import java.awt.Graphics;

public class Food {

	final int DIAMETER = 10;

	DrawingCanvas canvas;
	JVector position;

	public Food(DrawingCanvas canvas) {
		this.canvas = canvas;
		this.position = new JVector((int) (Math.random()*canvas.getWidth()), (int) (Math.random()*canvas.getHeight()));
	}

	public void draw(Graphics g) {
		g.fillOval(position.intX(), position.intY(), DIAMETER, DIAMETER);
	}

}