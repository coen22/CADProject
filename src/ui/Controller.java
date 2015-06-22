package ui;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import obj.DisplayObject;
import obj.ImplicitSurface;
import obj.Mesh;
import obj.Object3D;
import obj.ParametricSurface;
import obj.SmoothedMesh;
import obj.SpinningMesh;
import obj.Vertex;
import obj.Triangle;
import obj.algorithms.CurveSurfaceAreaMethod;
import obj.algorithms.CurveVolumeMethod;
import obj.algorithms.MeshSurfaceArea;
import obj.algorithms.MeshVolume;
import obj.algorithms.ParametricRichardsonSA;
import obj.algorithms.ParametricSimpsonSA;
import obj.algorithms.ParametricSurfaceArea;
import obj.algorithms.ParametricTrapezoidSA;
import obj.implicit_formula.Diamond;
import obj.implicit_formula.Genus2;
import obj.implicit_formula.Neovius;
import obj.implicit_formula.Sphere;
import obj.implicit_formula.SumOfSins;
import obj.implicit_formula.SwissCube;
import obj.implicit_formula.Torus;
import obj.implicit_formula.TorusCube;
import obj.implicit_formula.TorusIntersectSphere;
import obj.parametric_formula.PSphere;
import obj.parametric_formula.PTorus;
import obj.parametric_formula.Ship;
import obj.parametric_formula.Shoe;
import obj.parametric_formula.Spiral;
import obj.parametric_formula.Trumpet;

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

    private static final int CURVE_BALL = 15;
    private static final int CURVE_VASE = 16;
    private static final int CURVE_GLASS = 17;

    private ArrayList<DisplayObject> objects;
    private MainFrame frame;
    private int activeObject;

    public Controller() {
        frame = new MainFrame(this);
        objects = new ArrayList<DisplayObject>();
        frame.init(objects);
    }

    public void createObject(int type) {
        if (type == PARAMETRIC_TORUS) {
            objects.add(new DisplayObject(new ParametricSurface(new PTorus(), 0, 2*Math.PI, 0, 2 * Math.PI, 100, 100), "parametric torus"));
        } else if (type == PARAMETRIC_SHOE) {
            objects.add(new DisplayObject(new ParametricSurface(new Shoe(), -2, 2, -2, 2, 100, 100), "parametric shoe"));
        } else if (type == PARAMETRIC_SHIP) {
            objects.add(new DisplayObject(new ParametricSurface(new Ship(), 0, 2 * Math.PI, 0, 2 * Math.PI, 100, 100), "parametric ship"));
        } else if (type == PARAMETRIC_SPHERE) {
            objects.add(new DisplayObject(new ParametricSurface(new PSphere(), -0.5 * Math.PI, 0.5 * Math.PI, 0, 2 * Math.PI, 100, 100), "parametric sphere"));
        } else if (type == PARAMETRIC_SPIRAL) {
            objects.add(new DisplayObject(new ParametricSurface(new Spiral(), 1, 2, 0, 1, 100, 100), "parametric spiral"));
        } else if (type == PARAMETRIC_TRUMPET) {
            objects.add(new DisplayObject(new ParametricSurface(new Trumpet(), 0, 2 * Math.PI, 0, 2 * Math.PI, 100, 100), "parametric trumpet"));
        } else if (type == IMPLICIT_DIAMOND) {
            objects.add(new DisplayObject(new ImplicitSurface(new Diamond(), 0.05, 1.5), "implicit diamond"));
        } else if (type == IMPLICIT_GENUS_2) {
            objects.add(new DisplayObject(new ImplicitSurface(new Genus2(), 0.05, 2), "implicit genus 2"));
        } else if (type == IMPLICIT_NEOVIUS) {
            objects.add(new DisplayObject(new ImplicitSurface(new Neovius(), 0.05, 1.5), "implicit neovius"));
        } else if (type == IMPLICIT_SPHERE) {
            objects.add(new DisplayObject(new ImplicitSurface(new Sphere(), 0.05, 1.5), "implicit sphere"));
        } else if (type == IMPLICIT_SUM_OF_SINS) {
            objects.add(new DisplayObject(new ImplicitSurface(new SumOfSins(), 0.05, 1.5), "implicit sum of sins"));
        } else if (type == IMPLICIT_SWISS_CUBE) {
            objects.add(new DisplayObject(new ImplicitSurface(new SwissCube(), 0.05, 1.5), "implicit swiss cube"));
        } else if (type == IMPLICIT_TORUS) {
            objects.add(new DisplayObject(new ImplicitSurface(new Torus(), 0.05, 1.5), "implicit torus"));
        } else if (type == IMPLICIT_TORUS_CUBE) {
            objects.add(new DisplayObject(new ImplicitSurface(new TorusCube(), 0.05, 1.5), "implicit torus-cube"));
        } else if (type == IMPLICIT_TORUS_INTERSECT_SPHERE) {
            objects.add(new DisplayObject(new ImplicitSurface(new TorusIntersectSphere(), 0.05, 2), "implicit torus intersecting sphere"));
        } else if (type == CURVE_BALL) {
            SpinningMesh ball = new SpinningMesh();
            ball.addPoint(new Vertex(0, 0, 0));
            ball.addPoint(new Vertex(1, 0.01, 0));
            ball.addPoint(new Vertex(2, 1, 0));
            ball.addPoint(new Vertex(1, 1.99, 0));
            ball.addPoint(new Vertex(0, 2, 0));
            objects.add(new DisplayObject(ball, "ball"));
        } else if (type == CURVE_VASE) {
            SpinningMesh vase = new SpinningMesh();
            vase.addPoint(new Vertex(0, 0, 0));
            vase.addPoint(new Vertex(1, 0.01, 0));
            vase.addPoint(new Vertex(0.3, 0.2, 0));
            vase.addPoint(new Vertex(0.15, 0.5, 0));
            vase.addPoint(new Vertex(0.3, 3, 0));
            vase.addPoint(new Vertex(0.3, 3.1, 0));
            vase.addPoint(new Vertex(0.3, 3.1, 0));
            vase.addPoint(new Vertex(0.5, 3.29, 0));
            vase.addPoint(new Vertex(1, 3.295, 0));
            vase.addPoint(new Vertex(0.1, 3.3, 0));
            vase.addPoint(new Vertex(0.5, 3.01, 0));
            vase.addPoint(new Vertex(0.1, 2.5, 0));
            vase.addPoint(new Vertex(0, 2, 0));
            objects.add(new DisplayObject(vase, "vase"));
        } else if (type == CURVE_GLASS) {
            SpinningMesh glass = new SpinningMesh();
            glass.addPoint(new Vertex(0, 0, 0));
            glass.addPoint(new Vertex(1.5, 0.01, 0));
            glass.addPoint(new Vertex(0.1, 0.1, 0));
            glass.addPoint(new Vertex(0.03, 0.14, 0));
            glass.addPoint(new Vertex(0.03, 0.15, 0));
            glass.addPoint(new Vertex(0.03, 0.16, 0));
            glass.addPoint(new Vertex(0.03, 1, 0));
            glass.addPoint(new Vertex(0.06, 1.99, 0));
            glass.addPoint(new Vertex(0.06, 2, 0));
            glass.addPoint(new Vertex(0.06, 2.01, 0));
            glass.addPoint(new Vertex(0.1, 2.05, 0));
            glass.addPoint(new Vertex(0.5, 2.1, 0));
            glass.addPoint(new Vertex(1.5, 2.4, 0));
            glass.addPoint(new Vertex(1.5, 2.41, 0));
            glass.addPoint(new Vertex(0.5, 2.4, 0));
            glass.addPoint(new Vertex(0.1, 1.8, 0));
            glass.addPoint(new Vertex(0, 1.8, 0));
            objects.add(new DisplayObject(glass, "martini glass"));
        }
        frame.itemsChanged();
    }

    public void importObject(File file) {
        objects.add(new DisplayObject(new Mesh(file.getPath()), file.getName()));
        frame.itemsChanged();
    }

    public String[] getObjectNameArray() {
        if (objects != null) {
            String[] arr = new String[objects.size()];
            for (int i = 0; i < objects.size(); i++) {
                arr[i] = objects.get(i).getName();
            }
            return arr;
        } else {
            return null;
        }
    }

    public void activeSelectionChanged(int active) {
        if (objects.size() > 1) {
            objects.get(activeObject).deactivate();
            activeObject = active;
            objects.get(active).activate();
        }
        if (active == -1) {
            //do nothing
        } else {
            objects.get(active).activate();
        }

        frame.activeSelectionChanged(active);
    }

    public void deleteCurrent() {
        objects.remove(activeObject);
        activeObject = 0;
        frame.itemsChanged();
    }

    public void setColor(Color color) {
        objects.get(activeObject).setColor(color);
    }

    public void toggleGraphicsMode() {
        frame.toggleGraphicsMode();
    }
    
    public void setSAMethod(String methodName){
    	if (methodName.equals(Object3D.MESH_SA)){
    		objects.get(activeObject).getObject().setSurfaceAreaMethod(new MeshSurfaceArea());
    	}
    	else if (methodName.equals(Object3D.CURVE_SA)){
    		objects.get(activeObject).getObject().setSurfaceAreaMethod(new CurveSurfaceAreaMethod());
    	}
    	else if (methodName.equals(Object3D.PARA_APPROX)){
    		objects.get(activeObject).getObject().setSurfaceAreaMethod(new ParametricSurfaceArea());
    	}
    	else if (methodName.equals(Object3D.PARA_SA_RICH)){
    		objects.get(activeObject).getObject().setSurfaceAreaMethod(new ParametricRichardsonSA());
    	}
    	else if (methodName.equals(Object3D.PARA_SA_SIMP)){
    		objects.get(activeObject).getObject().setSurfaceAreaMethod(new ParametricSimpsonSA());
    	}
    	else if (methodName.equals(Object3D.PARA_SA_TRAP)){
    		objects.get(activeObject).getObject().setSurfaceAreaMethod(new ParametricTrapezoidSA());
    	}
    	frame.updateInfo();
    }
    
    public void setVolMethod(String methodName){
    	if (methodName.equals(Object3D.MESH_VOL)){
    		objects.get(activeObject).getObject().setVolumeMethod(new MeshVolume());;
    	}
    	else if (methodName.equals(Object3D.CURVE_VOL)){
    		objects.get(activeObject).getObject().setVolumeMethod(new CurveVolumeMethod());;
    	}
    	frame.updateInfo();
    }
    
    public ArrayList<String> getVolMeths(){
    	if (objects.size() > 0){
    		return objects.get(activeObject).getObject().getVolumeMethods();
    	}
    	else {
    		return new ArrayList<String>();
    	}
    	
    }
    
    public ArrayList<String> getSAMeths(){
    	if (objects.size() > 0){
    		return objects.get(activeObject).getObject().getSurfaceAreaMethods();
    	}
    	else {
    		return new ArrayList<String>();
    	}
    }
    
    public void increaseSmoothing(){
    	if (objects.size() > 0){
    		if (!(objects.get(activeObject).getObject() instanceof SmoothedMesh)){
        		System.out.println("first smoothing");
        		SmoothedMesh newSmooth = new SmoothedMesh((ArrayList<Triangle>)objects.get(activeObject).getTris(), (ArrayList<Vertex>)objects.get(activeObject).getVerts());
        		newSmooth.setDetail(1);
        		objects.add(new DisplayObject(newSmooth, objects.get(activeObject).getName() + " smooth"));
        		frame.itemsChanged();
        	}
    		else {
    			System.out.println("increasing detail");
    			SmoothedMesh obj = (SmoothedMesh) objects.get(activeObject).getObject();
    			System.out.println("prev detail: " + obj.getDetail());
    			int newDetail = obj.getDetail() + 1;
    			obj.setDetail(newDetail);
    			System.out.println("done");
    		}
    	}
    }

}
