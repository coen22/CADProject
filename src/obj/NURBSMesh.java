package obj;

public class NURBSMesh extends Mesh {
	public NURBSMesh() {
		super();
	}
	
	public NURBSMesh(String path) {
		super(path);
	}
	
	private Vertex calcVertex(Vertex[][] vert) {
		Vertex v = new Vertex();
		
		for (int i = 0; i < vert.length; i++) {
			for (int j = 0; j < vert[0].length; j++) {
				v.add(vert[i][j].multiply(r(vert, i, j)));
			}
		}
		
		return v;
	}
	
	private double r(Vertex[][] vert, int i, int j) {
		
		
		return 0;
	}
}