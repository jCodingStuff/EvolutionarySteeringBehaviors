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
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
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

	public void add(JVector vector) {
		x += vector.x;
		y += vector.y;
		magnitude = getMagnitude();
	}

	public void sub(JVector vector) {
		x -= vector.x;
		y -= vector.y;
		magnitude = getMagnitude();
	}

	public static JVector add2Vecs(JVector vector1, JVector vector2) {
		double sumX = vector1.getX() + vector2.getX();
		double sumY = vector1.getY() + vector2.getY();
		return new JVector(sumX, sumY);
	}

	public static JVector sub2Vecs(JVector vector1, JVector vector2) {
		double subX = vector1.getX() - vector2.getX();
		double subY = vector1.getY() - vector2.getY();
		return new JVector(subX, subY);
	}
}