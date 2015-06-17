package ui;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;

import obj.ImplicitSurface;
import obj.Mesh;
import obj.ParametricSurface;
import obj.SpinningMesh;
import obj.Vertex;
import obj.implicit_formula.Diamond;
import obj.implicit_formula.Genus2;
import obj.implicit_formula.Neovius;
import obj.implicit_formula.Sphere;
import obj.implicit_formula.SumOfSins;
import obj.implicit_formula.SwissCube;
import obj.implicit_formula.Torus;
import obj.implicit_formula.TorusCube;
import obj.implicit_formula.TorusIntersectSphere;
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
import obj.parametric_formula.TrumpetX;
import obj.parametric_formula.TrumpetY;
import obj.parametric_formula.TrumpetZ;

public class Controller {
	private static final int PARAMETRIC_TORUS = 0;
	private static final int PARAMETRIC_SHOE = 1;
	private static final int PARAMETRIC_SHIP = 2;
	private static final int PARAMETRIC_SPHERE = 3;
	private static final int PARAMETRIC_SPIRAL = 4;
	private static final int PARAMETRIC_TRUMPET = 5;
	
	private static final int IMPLICIT_DIAMOND = 6;
	private static final int IMPLICIT_GENUS_2 = 7;
	private static final int IMPLICIT_NEOVIUS = 8;
	private static final int IMPLICIT_SPHERE = 9;
	private static final int IMPLICIT_SUM_OF_SINS = 10;
	private static final int IMPLICIT_SWISS_CUBE = 11;
	private static final int IMPLICIT_TORUS = 12;
	private static final int IMPLICIT_TORUS_CUBE = 13;
	private static final int IMPLICIT_TORUS_INTERSECT_SPHERE = 14;
	
	private ArrayList<DisplayObject> objects;
	private MainFrame frame;
	private int activeObject;
	
	public Controller(){
		frame = new MainFrame(this);
		objects = new ArrayList<DisplayObject>();
		frame.init(objects);
	}
	
	public void createObject(int type){
		if (type == PARAMETRIC_TORUS){
			objects.add(new DisplayObject(new ParametricSurface(new TorusX(), new TorusY(), new TorusZ(), 0, 2*Math.PI, 0, 2*Math.PI, 100, 100), "parametric torus"));
		}
		else if (type == PARAMETRIC_SHOE){
			objects.add(new DisplayObject(new ParametricSurface(new ShoeX(), new ShoeY(), new ShoeZ(), -2, 2, -2, 2, 100, 100), "parametric shoe"));
		}
		else if (type == PARAMETRIC_SHIP){
			objects.add(new DisplayObject(new ParametricSurface(new ShipX(), new ShipY(), new ShipZ(), 0, 2*Math.PI, 0, 2*Math.PI, 100, 100), "parametric ship"));
		}
		else if (type == PARAMETRIC_SPHERE){
			objects.add(new DisplayObject(new ParametricSurface(new SphereX(), new SphereY(), new SphereZ(), -0.5*Math.PI, 0.5*Math.PI, 0, 2*Math.PI, 100, 100), "parametric sphere"));
		}
		else if (type == PARAMETRIC_SPIRAL){
			objects.add(new DisplayObject(new ParametricSurface(new SpiralX(), new SpiralY(), new SpiralZ(), 1, 2, 0, 1, 100, 100), "parametric spiral"));
		}
		else if (type == PARAMETRIC_TRUMPET){
			objects.add(new DisplayObject(new ParametricSurface(new TrumpetX(), new TrumpetY(), new TrumpetZ(), 0, 2*Math.PI, 0, 2*Math.PI, 100, 100), "parametric trumpet"));
		}
		else if (type == IMPLICIT_DIAMOND){
			objects.add(new DisplayObject(new ImplicitSurface(new Diamond(), 0.05, 1.5), "implicit diamond"));
		}
		else if (type == IMPLICIT_GENUS_2){
			objects.add(new DisplayObject(new ImplicitSurface(new Genus2(), 0.05, 2), "implicit genus 2"));
		}
		else if (type == IMPLICIT_NEOVIUS){
			objects.add(new DisplayObject(new ImplicitSurface(new Neovius(), 0.05, 1.5), "implicit neovius"));
		}
		else if (type == IMPLICIT_SPHERE){
			objects.add(new DisplayObject(new ImplicitSurface(new Sphere(), 0.05, 1.5), "implicit sphere"));
		}
		else if (type == IMPLICIT_SUM_OF_SINS){
			objects.add(new DisplayObject(new ImplicitSurface(new SumOfSins(), 0.05, 1.5), "implicit sum of sins"));
		}
		else if (type == IMPLICIT_SWISS_CUBE){
			objects.add(new DisplayObject(new ImplicitSurface(new SwissCube(), 0.05, 1.5), "implicit swiss cube"));
		}
		else if (type == IMPLICIT_TORUS){
			objects.add(new DisplayObject(new ImplicitSurface(new Torus(), 0.05, 1.5), "implicit torus"));
		}
		else if (type == IMPLICIT_TORUS_CUBE){
			objects.add(new DisplayObject(new ImplicitSurface(new TorusCube(), 0.05, 1.5), "implicit torus-cube"));
		}
		else if (type == IMPLICIT_TORUS_INTERSECT_SPHERE){
			objects.add(new DisplayObject(new ImplicitSurface(new TorusIntersectSphere(), 0.05, 2), "implicit torus intersecting sphere"));
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
			objects.get(active).activate();
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
		activeObject = 0;
		frame.itemsChanged();
	}
	
	public void setColor(Color color){
		objects.get(activeObject).setColor(color);
	}

}
