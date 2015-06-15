import java.util.ArrayList;

import obj.DisplayObject;
import obj.Mesh;
import ui.MainFrame;


public class Controller {
	
	private static final int PARAMETRIC_TORUS = 0;
	private static final int PARAMETRIC_SHOE = 1;
	private static final int PARAMETRIC_SHIP = 2;
	private static final int PARAMETRIC_SPHERE = 3;
	private static final int PARAMETRIC_SPIRAL = 4;
	private static final int PARAMETRIC_TRUMPET = 5;
	
	private static final int IMPLICIT_DIAMOND = 10;
	private static final int IMPLICIT_GENUS_2 = 11;
	private static final int IMPLICIT_NEOVIUS = 12;
	private static final int IMPLICIT_SPHERE = 13;
	private static final int IMPLICIT_SUM_OF_SINS = 14;
	private static final int IMPLICIT_SWISS_CUBE = 15;
	private static final int IMPLICIT_TORUS = 16;
	private static final int IMPLICIT_TORUS_CUBE = 17;
	private static final int IMPLICIT_TORUS_INTERSECT_SPHERE = 18;
	
	private ArrayList<DisplayObject> objects;
	private MainFrame frame;
	
	public Controller(){
		frame = new MainFrame();
		objects = new ArrayList<DisplayObject>();
		
		objects.add(new DisplayObject(new Mesh("C:\\Users\\David Admin\\Documents\\DKE\\Period 6\\CADProject\\bunny.obj")));
		updateUI();
	}
	
	public void updateUI(){
		frame.setObjects(objects);
	}

}
