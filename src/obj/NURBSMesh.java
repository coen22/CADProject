package obj;

import java.util.List;

public class NURBSMesh extends Object3D {
	public NURBSMesh() {
		super();
	}
	
	@Override
	public List<Triangle> getTris() {
		// TODO
		return null;
	}
	
    @Override
    public List<Vertex> getVerts() {
    		// TODO
        return null;
    }
	
	private Vertex calcVertex(Vertex[][] vert, int i, int j, double u, double v) {
		Vertex out = new Vertex();
		
		for (int x = 0; i < vert.length; i++) {
			for (int y = 0; j < vert[0].length; j++) {
				out.add(vert[x][y].multiply(r(vert, x, y, u, v)));
			}
		}
		
		return out;
	}
	
	private double r(Vertex[][] vert, int i, int j, double u, double v) {
		
		
		return 0;
	}
	
	private double n(int i, int j, double uv) {
		
		
		return 0;
	}
	
	private double f(int i, int j, double uv) {
		
		
		return 0;
	}
	
	private double g(int i, int j, double uv) {
		
		
		return 0;
	}
}