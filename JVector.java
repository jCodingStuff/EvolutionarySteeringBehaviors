public class JVector {

	double x;
	double y;
	double magnitude;

	public JVector(double x, double y) {
		this.x = x;
		this.y = y;
		this.magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public double getX() {
		return x;
	}

	public int intX() {
		return (int) x;
	}

	public double getY() {
		return y;
	}

	public int intY() {
		return (int) y;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void multiply(double scalar) {
		x = x * scalar;
		y = y * scalar;
		magnitude = magnitude * scalar;
	}

	public void divide(double scalar) {
		x = x / scalar;
		y = y / scalar;
		magnitude = magnitude / scalar;
	}

	public void normalize() {
		x = x / magnitude;
		y = y / magnitude;
		magnitude = 1;
	}

	public void setMagnitude(double number) {
		this.normalize();
		this.multiply(number);
	}

	public static JVector opposite(JVector vector) {
		double invX = vector.y;
		double invY = vector.x;
		return new JVector(invX, invY);
	}

	public static JVector add(JVector vector1, JVector vector2) {
		double sumX = vector1.x + vector2.x;
		double sumY = vector1.y + vector2.y;
		return new JVector(sumX, sumY);
	}

	public static JVector sub(JVector vector1, JVector vector2) {
		double subX = vector1.x - vector2.x;
		double subY = vector1.y - vector2.y;
		return new JVector(subX, subY);
	}
}