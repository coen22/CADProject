package obj;

import java.util.ArrayList;
import java.util.List;

public class SmoothedMesh extends Mesh {
	
	/**
	 * The number of iterations that the algorithm will run
	 */
	private int detail;
			
	private List<Triangle> calcTris = new ArrayList<Triangle>();
	
	/**
	 * Class that smoothes a mesh object
	 * @param location of the obj mesh file to smooth
	 */
	public SmoothedMesh(String dir) {
		super(dir);
		detail = 1;
	}
	
	/**
	 * Class that smoothes a mesh object
	 * @param location of the obj mesh file to smooth
	 */
	public SmoothedMesh(String dir, int detail) {
		super(dir);
		this.detail = detail;
	}
	
	/**
	 * Class that smoothes an object in 3D containing vertices and triangles
	 * @param tris
	 * @param verts
	 */
	public SmoothedMesh(ArrayList<Triangle> tris, ArrayList<Vertex> verts) {
		super(verts,tris);
		detail = 1;
	}
	
	/**
	 * Class that smoothes an object in 3D containing vertices and triangles
	 * @param tris
	 * @param verts
	 */
	public SmoothedMesh() {
		super("src/tetrahedron.obj");
		detail = 1;
	}
	
	@Override
	public List<Vertex> getVerts() {
		return vertices;
	}

	@Override
	public List<Triangle> getTris() {
		if (!calcTris.isEmpty())
			return calcTris;
		
		if (detail == 0)
			return tris;
		
		getTris(tris);
		
		for (int i = 0; i < detail - 1; i++) {
			getTris(calcTris);
		}
		
		return calcTris;
	}
	
	private  List<Triangle> getTris(List<Triangle> inTris) { 

		List<Triangle> adjacentTris = new ArrayList<Triangle>();
		List<Edge> edges = new ArrayList<Edge>();
		List<Vertex> newPoints = new ArrayList<Vertex>();
		
		List<Triangle> tmpTris = new ArrayList<Triangle>();
		
		for (Triangle t : inTris) {

			adjacentTris.clear();
			edges.clear();
			newPoints.clear();
			
			for (Triangle t2 : inTris) {
				Edge e = t.getAdjacentEdge(t2);

				if (e != null) {
					adjacentTris.add(t2);
					edges.add(e);
					
					if (adjacentTris.size() == 3)
						break;
				}
			}
			
			// op is the list of original points
			List<Vertex> op = new ArrayList<Vertex>();
			op.add(t.getA());
			op.add(t.getB());
			op.add(t.getC());
			
			// r is the average of all midpoints of the edges touching a point on triangle t
			List<Vertex> r = new ArrayList<Vertex>();
			
			// f is the list of face midpoints
			List<Vertex> f = new ArrayList<Vertex>();
			
			// get the adjacent triangles to a point
			List<List<Triangle>> pats = new ArrayList<List<Triangle>>();
			
			for (int i = 0; i < op.size(); i++) {
				r.add(op.get(i).getConnectedEdgesMidpoint(inTris));
				
				pats.add(op.get(i).getAdjacentTriangles(inTris));
				
				Vertex fp = new Vertex();
				
				for (Triangle pat : pats.get(i)) {
					fp.add(pat.getMidpoint());
				}
				
				fp = fp.multiply((1.0 / (double) pats.get(i).size()));
				
				f.add(fp);
				
				Vertex p = new Vertex();
				
				double n = pats.get(i).size();
				
				p.setX((f.get(i).getX() + 2 * r.get(i).getX() + (n - 3.0) * op.get(i).getX()) / n);
				p.setY((f.get(i).getY() + 2 * r.get(i).getY() + (n - 3.0) * op.get(i).getY()) / n);
				p.setZ((f.get(i).getZ() + 2 * r.get(i).getZ() + (n - 3.0) * op.get(i).getZ()) / n);
				newPoints.add(p);
			}
			
			for (int i = 0; i < edges.size(); i++) {
				// ep is a newly created edge point
				Vertex ep = new Vertex();
				ep.setX((t.getMidpoint().getX()
						+ adjacentTris.get(i).getMidpoint().getX()
						+ edges.get(i).getA().getX() + edges.get(i).getB()
						.getX()) / 4);
				ep.setY((t.getMidpoint().getY()
						+ adjacentTris.get(i).getMidpoint().getY()
						+ edges.get(i).getA().getY() + edges.get(i).getB()
						.getY()) / 4);
				ep.setZ((t.getMidpoint().getZ()
						+ adjacentTris.get(i).getMidpoint().getZ()
						+ edges.get(i).getA().getZ() + edges.get(i).getB()
						.getZ()) / 4);

				int a;
				int b;
				
				if (t.getA().equals(edges.get(i).getA()))
					a = 0;
				else if (t.getB().equals(edges.get(i).getA()))
					a = 1;
				else
					a = 2;
				
				
				if (t.getA().equals(edges.get(i).getB()))
					b = 0;
				else if (t.getB().equals(edges.get(i).getB()))
					b = 1;
				else
					b = 2;
				
				if (b != (a + 1) % 3) {
					int tmp = a;
					a = b;
					b = tmp;
				}
				
				tmpTris.add( new Triangle(t.getMidpoint(), newPoints.get(a), ep));
				tmpTris.add( new Triangle(t.getMidpoint(), ep, newPoints.get(b)));
			}
		}

		calcTris = tmpTris;
		return calcTris;
	}
	
	@Override
	public void setTris(ArrayList<Triangle> tris) {
		super.setTris(tris);
		calcTris.clear();
	}
	
	/**
	 * Method to get the number of iterations that algorithm goes through
	 * @return the detail
	 */
	public int getDetail() {
		return detail;
	}
	
	/**
	 * Method to set the number of iterations that algorithm goes through
	 * @param the new detail
	 */
	public void setDetail(int detail) {
		this.detail = detail;
		calcTris.clear();
	}
}