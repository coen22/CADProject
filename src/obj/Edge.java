package obj;

public class Edge {
	private Vertex a;
	private Vertex b;
	
	/**
	 * Constructor to make a new edge based on vertices a and b
	 * @param vertex a
	 * @param vertex b
	 */
	public Edge(Vertex a, Vertex b) {
		this.a = a;
		this.b = b;
	}
	
    /**
     * get vertex a from the edge
     *
     * @return the a
     */
    public Vertex getA() {
        return a;
    }

    /**
     * get vertex b from the edge
     *
     * @return the b
     */
    public Vertex getB() {
        return b;
    }

    /**
     * set vertex a from the edge
     */
    public void setA(Vertex a) {
        this.a = a;
    }

    /**
     * set vertex b from the edge
     */
    public void setB(Vertex b) {
        this.b = b;
    }
    
    /**
     * Method to get the midpoint of an edge
     * @return the midpoint
     */
    public Vertex getMidpoint() {
    		Vertex v = new Vertex();
    		v.setX((a.getX() + b.getX()) / 2);
    		v.setY((a.getY() + b.getY()) / 2);
    		v.setZ((a.getZ() + b.getZ()) / 2);
    		return v;
    }
}
