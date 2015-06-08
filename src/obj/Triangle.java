package obj;

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
	 * @return the a
	 */
	public Vertex getA() {
		return a;
	}
	
	/**
	 * get vertex b from the triangle
	 * @return the b
	 */
	public Vertex getB() {
		return b;
	}
	
	/**
	 * get vertex c from the triangle
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
}
