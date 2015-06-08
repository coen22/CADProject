package obj;

public class Vertex {

	/**
	 * The x value of the vertex location
	 */
	private double x;

	/**
	 * The y value of the vertex location
	 */
	private double y;

	/**
	 * The z value of the vertex location
	 */
	private double z;
	
	/**
	 * create a vertex at the origin
	 */
	public Vertex() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	/**
	 * create a vertex at the point x,y,z
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * get x value of the vertex
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * get y value of the vertex
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * get z value of the vertex
	 * @return the z
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * set x value of the vertex
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * set y value of the vertex
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * set z value of the vertex
	 */
	public void setZ(double z) {
		this.z = z;
	}
}
