package obj;

import java.util.ArrayList;
import java.util.List;

public class Tetrahedron extends Object3D {

	private ArrayList<Vertex> vertices; 
	private ArrayList<Triangle> tris;
	
	public Tetrahedron(Triangle tri1, Triangle tri2, Triangle tri3, Triangle tri4){
		tris = new ArrayList<>();
		tris.add(tri1);
		tris.add(tri2);
		tris.add(tri3);
		tris.add(tri4);
		vertices = new ArrayList<>();
		for(int i = 0; i < tris.size(); i++){
			vertices.add(tris.get(i).getA());
			vertices.add(tris.get(i).getB());
			vertices.add(tris.get(i).getC());
		}
	}
	
	@Override
	public List<Vertex> getVerts() {
		return vertices;
	}

	@Override
	public List<Triangle> getTris() {
		return tris;
	}

	
}
