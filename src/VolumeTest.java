import obj.SpinningMesh;
import obj.Vertex;
import obj.algorithms.MeshSurfaceArea;

public class VolumeTest {

	public static void main(String[] args) {
		SpinningMesh inp = new SpinningMesh();
		inp.addPoint(new Vertex(1, 3, 0));
		inp.addPoint(new Vertex(2, 2, 0));
		inp.addPoint(new Vertex(2, 1, 0));
		inp.addPoint(new Vertex(1, 0, 0));
		
		inp.setXYInterval(12);
		// inp.setXZInterval(12);
		
		for (int xy = 3; xy <= 100; xy++) {
			// inp.setXYInterval(xy);
			inp.setXZInterval(xy);
			System.out.print("(" + xy + ", " + inp.getSurfaceArea() + ")");
		}
		
		inp.setSurfaceAreaMethod(new MeshSurfaceArea());
		
		System.out.println("");
		System.out.println("");
		
		for (int xy = 3; xy <= 100; xy++) {
			// inp.setXYInterval(xy);
			inp.setXZInterval(xy);
			System.out.print("(" + xy + ", " + inp.getSurfaceArea() + ")");
		}
	}

}
