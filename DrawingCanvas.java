import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class DrawingCanvas extends Canvas {

	private BufferStrategy buffer;
	private Graphics g;

	public DrawingCanvas(int width, int height) {
		setSize(width, height);
	}

	public void draw() {
		buffer = getBufferStrategy();

		if (buffer == null) {
			createBufferStrategy(2); //If more resources are needed, increase the number
			return;
		}

		g = buffer.getDrawGraphics(); //Draw in the buffer

			//DRAW
			g.setColor(new Color(24, 23, 69));
			g.fillRect(0, 0, canvasSize().intX(), canvasSize().intY());

		g.dispose();
		buffer.show();
	}

	public JVector canvasSize() {
		return new JVector(this.getWidth(), this.getHeight());
	}

}