package obj;

import java.util.ArrayList;
import java.util.List;

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
	 * the "normal" of the vertex used to plot implicit objects
	 */
	private double[] implicitNormal;

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
	 * 
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
	 * create a vertex at the point x,y,z with an implicitNormal
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param implicitNormal
	 */
	public Vertex(double x, double y, double z, double[] implicitNormal) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.implicitNormal = implicitNormal;
	}

	/**
	 * get x value of the vertex
	 * 
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * get y value of the vertex
	 * 
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * get z value of the vertex
	 * 
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

	/**
	 * add another vertex to this vertex
	 * @param a vertex
	 */
	public void add(Vertex v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}

	/**
	 * Get a scaler vector of this vertex
	 * @param scaler value
	 * @return the new vertex
	 */
	public Vertex multiply(double val) {
		Vertex v = clone();

		v.x *= val;
		v.y *= val;
		v.z *= val;

		return v;
	}

	@Override
	public Vertex clone() {
		return new Vertex(x, y, z);
	}

	/**
	 * Method to see if a vertex v is the same as this
	 * @param v
	 * @return true or false
	 */
	public boolean equals(Vertex x) {
		if ((this.getX() == x.getX()) && (this.getY() == x.getY())
				&& (this.getZ() == x.getZ())) {
			return true;
		} else
			return false;
	}

	public double[] getImplicitNormal() {
		return this.implicitNormal;
	}

	@Override
	public String toString() {
		return "[(" + x + "),(" + y + "),(" + z + ")]";
	}
	
	/**
	 * Method to get all triangles this vertex is connected to
	 * @param input triangles to search through
	 * @return the triangles the this vertex is adjacent to
	 */
	public List<Triangle> getAdjacentTriangles(List<Triangle> tris) {
		List<Triangle> out = new ArrayList<Triangle>();
		
		for (Triangle t : tris) {
			if (equals(t.getA()) || equals(t.getB()) || equals(t.getC())) {
				out.add(t);
			}
		}
		
		return out;
	}
	
	/**
	 * Method to get all edges that are connected to this vertex
	 * @param the triangles to search from
	 * @return the edges which this vertex is connected to
	 */
	public List<Edge> getConnectedEdges(List<Triangle> tris) {
		List<Edge> out = new ArrayList<Edge>();
		
		// Find all connected triangles
		List<Triangle> ats = getAdjacentTriangles(tris);
		
		// Get connected edges
		for (Triangle at : ats) {
			if (equals(at.getA())) {
				out.add(new Edge(this, at.getB()));
				out.add(new Edge(this, at.getC()));
			} else if (equals(at.getB())) {
				out.add(new Edge(this, at.getA()));
				out.add(new Edge(this, at.getC()));
			} else if (equals(at.getC())) {
				out.add(new Edge(this, at.getA()));
				out.add(new Edge(this, at.getB()));
			}
		}
		
		// Remove double edges
		for (int i = 0; i < out.size() - 1; i++) {
			for (int j = i + 1; j < out.size(); j++) {
				if (out.get(j) != out.get(i) && out.get(j).getB().equals(out.get(i).getB())) {
					out.remove(j);
				}
			}
		}
		
		return out;
	}
	
	/**
	 * Method that returns the midpoint of all the midpoints of all edges that are adjacent to this vertex
	 * @param triangles to search through
	 * @return the midpoint
	 */
	public Vertex getConnectedEdgesMidpoint(List<Triangle> tris) {
		List<Edge> edges = getConnectedEdges(tris);
		
		Vertex v = new Vertex();
		
		for (Edge e : edges) {
			v.add(e.getMidpoint());
		}
		
		return v.multiply((double) (1.0 / edges.size()));
	}
}
