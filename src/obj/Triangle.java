package obj;

import java.util.ArrayList;

public class Triangle {

	/**
	 * The first vertex of the triangle
	 */
	private Vertex a;

	/**
	 * The second vertex of the triangle
	 */
	private Vertex b;

	/**
	 * The third vertex of the triangle
	 */
	private Vertex c;

	/**
	 * Creates a triangle that
	 */
	public Triangle() {
		this.a = new Vertex();
		this.b = new Vertex();
		this.c = new Vertex();
	}

	/**
	 * Creates a triangle with vertices a, b and c
	 *
	 * @param a
	 * @param b
	 * @param c
	 */
	public Triangle(Vertex a, Vertex b, Vertex c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * get vertex a from the triangle
	 *
	 * @return the a
	 */
	public Vertex getA() {
		return a;
	}

	/**
	 * get vertex b from the triangle
	 *
	 * @return the b
	 */
	public Vertex getB() {
		return b;
	}

	/**
	 * get vertex c from the triangle
	 *
	 * @return the c
	 */
	public Vertex getC() {
		return c;
	}

	/**
	 * set vertex a from the triangle
	 */
	public void setA(Vertex a) {
		this.a = a;
	}

	/**
	 * set vertex b from the triangle
	 */
	public void setB(Vertex b) {
		this.b = b;
	}

	/**
	 * set vertex c from the triangle
	 */
	public void setC(Vertex c) {
		this.c = c;
	}

	public double calcArea() {
		// Using Heron's formula requires the length of each side
		// Therefore the length is calculated first
		double a = (Math.sqrt(Math.pow((this.a.getX() - this.b.getX()), 2)
				+ Math.pow((this.a.getY() - this.b.getY()), 2)
				+ Math.pow((this.a.getZ() - this.b.getZ()), 2)));
		double b = (Math.sqrt(Math.pow((this.b.getX() - this.c.getX()), 2)
				+ Math.pow((this.b.getY() - this.c.getY()), 2)
				+ Math.pow((this.b.getZ() - this.c.getZ()), 2)));
		double c = (Math.sqrt(Math.pow((this.c.getX() - this.a.getX()), 2)
				+ Math.pow((this.c.getY() - this.a.getY()), 2)
				+ Math.pow((this.c.getZ() - this.a.getZ()), 2)));

		// Heron's formula
		double A = (0.25) * Math.sqrt((a + b + c) * (-a + b + c) * (a - b + c)
				* (a + b - c));

		if (Double.isNaN(A)) {
			return 0;
		} else {
			return A;
		}
	}

	/**
	 * calculates the tetrahedron volume of this triangle to the origin 0,0,0
	 * 
	 * @return volume
	 */
	public double calcVolume() {
		double v321 = c.getX() * b.getY() * a.getZ();
		double v231 = b.getX() * c.getY() * a.getZ();
		double v312 = c.getX() * a.getY() * b.getZ();
		double v132 = a.getX() * c.getY() * b.getZ();
		double v213 = b.getX() * a.getY() * c.getZ();
		double v123 = a.getX() * b.getY() * c.getZ();
		double volume = (1.0 / 6.0)
				* (-v321 + v231 + v312 - v132 - v213 + v123);

		if (Double.isNaN(volume)) {
			return 0;
		} else {
			return volume;
		}
	}

	@Override
	public String toString() {
		return "[Triangle:" + a + "" + b + "" + c + "]";
	}

	public Vertex getMidpoint() {
		Vertex m = new Vertex();
		m.setX((a.getX() + b.getX() + c.getX()) / 3);
		m.setY((a.getY() + b.getY() + c.getY()) / 3);
		m.setZ((a.getZ() + b.getZ() + c.getZ()) / 3);
		return m;
	}

	public Edge getAdjacentEdge(Triangle t2) {
		ArrayList<Vertex> edgePoints = new ArrayList<Vertex>();

		if (a.compareTo(t2.a) || a.compareTo(t2.c) || a.compareTo(t2.b))
			edgePoints.add(a);

		if (b.compareTo(t2.a) || b.compareTo(t2.c) || b.compareTo(t2.b))
			edgePoints.add(b);

		if (c.compareTo(t2.a) || c.compareTo(t2.c) || c.compareTo(t2.b))
			edgePoints.add(c);

		if (edgePoints.size() == 2)
			return new Edge(edgePoints.get(0), edgePoints.get(1));
		else
			return null;
	}
}
