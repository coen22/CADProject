import obj.Mesh;
import obj.Tetrahedron;
import obj.Triangle;
import obj.Vertex;
import ui.MainFrame;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MainFrame frame = new MainFrame();
		
//		Vertex v1 = new Vertex(0.0, 0.0, 0.0);
//		Vertex v2 = new Vertex(0.0, 0.0, 1.0);
//		Vertex v3 = new Vertex(1.0, 0.0, 0.0);
//		Vertex v4 = new Vertex(0.5, 1.0, 0.5);
//		Tetrahedron tetra = new Tetrahedron(v1, v2, v3, v4);
//		
//		frame.setObject(tetra);
		
		Mesh bunny = new Mesh("C:\\Users\\David Admin\\Documents\\DKE\\Period 6\\CADProject\\obj\\bunny2.obj");
		
		frame.setObject(bunny);
	}

}
