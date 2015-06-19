package obj;

import java.util.ArrayList;
import java.util.List;

public class SmoothedMesh extends Mesh {
	
	List<Triangle> calcTris = new ArrayList<Triangle>();
	
	public SmoothedMesh(String dir) {
		super(dir);
	}
	
	@Override
	public List<Vertex> getVerts() {
		return vertices;
	}

	@Override
	public List<Triangle> getTris() {
		if (!calcTris.isEmpty())
			return calcTris;

		List<Triangle> adjacentTris = new ArrayList<Triangle>();
		List<Edge> edges = new ArrayList<Edge>();
		List<Vertex> newPoints = new ArrayList<Vertex>();
		
		for (Triangle t : tris) {

			adjacentTris.clear();
			edges.clear();
			newPoints.clear();
			
			for (Triangle t2 : tris) {
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
			r.add(t.getA().getConnectedEdgesMidpoint(tris));
			r.add(t.getB().getConnectedEdgesMidpoint(tris));
			r.add(t.getC().getConnectedEdgesMidpoint(tris));
			
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

				Vertex a;
				Vertex b;
				
				if (t.getA().compareTo(edges.get(i).getA()))
					a = r.get(0);
				else if (t.getB().compareTo(edges.get(i).getA()))
					a = r.get(1);
				else
					a = r.get(2);
				
				if (t.getA().compareTo(edges.get(i).getB()))
					b = r.get(0);
				else if (t.getB().compareTo(edges.get(i).getB()))
					b = r.get(1);
				else
					b = r.get(2);
				
				Edge newEdge = new Edge(a, b);
				
				calcTris.add( new Triangle(t.getMidpoint(), newEdge.getA(), ep));
				calcTris.add( new Triangle(t.getMidpoint(), ep, newEdge.getB()));
			}
		}

		return calcTris;
	}
	
	@Override
	public void setTris(ArrayList<Triangle> tris) {
		super.setTris(tris);
		calcTris.clear();
	}
}
