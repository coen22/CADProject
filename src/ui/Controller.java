package ui;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;

import obj.DisplayObject;
import obj.Mesh;
import obj.ParametricSurface;
import obj.SpinningMesh;
import obj.Vertex;
import obj.parametric_formula.ShipX;
import obj.parametric_formula.ShipY;
import obj.parametric_formula.ShipZ;
import obj.parametric_formula.ShoeX;
import obj.parametric_formula.ShoeY;
import obj.parametric_formula.ShoeZ;
import obj.parametric_formula.SphereX;
import obj.parametric_formula.SphereY;
import obj.parametric_formula.SphereZ;
import obj.parametric_formula.SpiralX;
import obj.parametric_formula.SpiralY;
import obj.parametric_formula.SpiralZ;
import obj.parametric_formula.TorusX;
import obj.parametric_formula.TorusY;
import obj.parametric_formula.TorusZ;


public class Controller {
	
	private static final int PATH_IMPORT = 0;
	
	private static final int PARAMETRIC_SHOE = 1;
	private static final int PARAMETRIC_SHIP = 2;
	private static final int PARAMETRIC_SPHERE = 3;
	private static final int PARAMETRIC_SPIRAL = 4;
	private static final int PARAMETRIC_TRUMPET = 5;
	private static final int PARAMETRIC_TORUS = 6;
	
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
	private int activeObject;
	
	public Controller(){
		frame = new MainFrame(this);
		objects = new ArrayList<DisplayObject>();
		frame.init(objects);
	}
	
	public void createObject(int type){
		if (type == PARAMETRIC_SHIP){
			objects.add(new DisplayObject(new ParametricSurface(new ShipX(), new ShipY(), new ShipZ(), 0, 2*Math.PI, 0, 2*Math.PI, 100, 100, true, false), "parametric ship"));
		}
		else if (type == PARAMETRIC_SHOE){
			objects.add(new DisplayObject(new ParametricSurface(new ShoeX(), new ShoeY(), new ShoeZ(), 0, 2*Math.PI, 0, 2*Math.PI, 100, 100, true, true), "parametric shoe"));
		}
		else if (type == PARAMETRIC_SPHERE){
			objects.add(new DisplayObject(new ParametricSurface(new SphereX(), new SphereY(), new SphereZ(), 0, 2*Math.PI, 0, 2*Math.PI, 100, 100, true, true), "parametric sphere"));
		}
		else if (type == PARAMETRIC_SPIRAL){
			objects.add(new DisplayObject(new ParametricSurface(new SpiralX(), new SpiralY(), new SpiralZ(), 1, 2, 0, 1, 100, 100, false, false), "parametric spiral"));
		}
		else if (type == PARAMETRIC_TORUS){
			objects.add(new DisplayObject(new ParametricSurface(new TorusX(), new TorusY(), new TorusZ(), 0, 2*Math.PI, 0, 2*Math.PI, 100, 100, true, true), "parametric torus"));
		}
		frame.itemsChanged();
	}
	
	public void importObject(File file){
		objects.add(new DisplayObject(new Mesh(file.getPath()), file.getName()));
		frame.itemsChanged();
	}

	public String[] getObjectNameArray() {
		if (objects != null){
			String[] arr = new String[objects.size()];
			for (int i = 0; i < objects.size(); i++){
				arr[i] = objects.get(i).getName();
			}
			return arr;
		}
		else{
			return null;
		}
	}
	
	public void activeSelectionChanged(int active){
		if (objects.size() > 1){
			objects.get(activeObject).deactivate();
			activeObject = active;
		}
		if (active == -1){
			//do nothing
		}
		else{
			objects.get(active).activate();
		}
		
		frame.activeSelectionChanged(active);
	}

	public void deleteCurrent() {
		objects.remove(activeObject);
		frame.itemsChanged();
	}
	

}
