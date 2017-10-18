public class Run {

	private static DrawingCanvas canvas;
	private static Window frame;
	private static int RPS = 0;
	private static int FPS = 0;

	public static void main(String[] args) {
		startGame();
	}

	public static void startGame() {
		createWindow(950, 600, "Evolutionary Steering Behaviors");
		iterateMainLoop();
	}

	public static void refresh() {
		RPS++;
		canvas.draw();
	}

	public static void draw() {
		FPS++;
	}

	public static void createWindow(int Width, int height, String title) {
		canvas = new DrawingCanvas(Width, height);
		frame = new Window(title, canvas);
	}

	//Create the framerate
	public static void iterateMainLoop() {
		final int NS_PER_SECOND = 1000000000;
		final int RPS_OBJECTIVE = 60;
		final int NS_PER_FRAME = NS_PER_SECOND / RPS_OBJECTIVE;

		long referenceRefreshTime = System.nanoTime();
		long referenceTimeCounter = System.nanoTime();

		double delta = 0;
		double timeWithoutProcessing;

		while (true) { //Loop until the simulation ends
			long initialTime = System.nanoTime();
			timeWithoutProcessing = initialTime - referenceRefreshTime;
			referenceRefreshTime = initialTime;
			delta += timeWithoutProcessing / NS_PER_FRAME;

			while (delta >= 1) {
				delta--;
				refresh();
			}

			draw();

			if (System.nanoTime() - referenceTimeCounter >= NS_PER_SECOND) {
				System.out.println("RPS = " + RPS + ", FPS = " + FPS);
				referenceTimeCounter = System.nanoTime();
				RPS = 0;
				FPS = 0;	
			}
		}
	}

}