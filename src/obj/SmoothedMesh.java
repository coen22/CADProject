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
	
	@Override
	public List<Vertex> getVerts() {
		return vertices;
	}

	@Override
	public List<Triangle> getTris() {
		if (!calcTris.isEmpty())
			return calcTris;
		
		if (detail > 0)
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
			
			// f is the average of the 4 face points
			Vertex f = new Vertex();
			
			for (Triangle at : adjacentTris) {
				f.add(at.getMidpoint());
			}
			
			f = 	f.multiply((double) (1 / adjacentTris.size()));
			
			// r is the average of all midpoints of the edges touching a point on triangle t
			List<Vertex> r = new ArrayList<Vertex>();
			r.add(t.getA().getConnectedEdgesMidpoint(inTris));
			r.add(t.getB().getConnectedEdgesMidpoint(inTris));
			r.add(t.getC().getConnectedEdgesMidpoint(inTris));
			
			for (int i = 0; i < 3; i++) {
				// p [1, 2, 3] are the new endpoints of the triangle
				Vertex p = new Vertex();
				p.setX((f.getX() + 2 * r.get(i).getX()) / 3);
				p.setY((f.getY() + 2 * r.get(i).getY()) / 3);
				p.setZ((f.getZ() + 2 * r.get(i).getZ()) / 3);
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
				
				tmpTris.add( new Triangle(t.getMidpoint(), r.get(a), ep));
				tmpTris.add( new Triangle(t.getMidpoint(), ep, r.get(b)));
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