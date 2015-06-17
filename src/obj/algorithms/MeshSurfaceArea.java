package obj.algorithms;

import java.util.ArrayList;

import obj.Object3D;
import obj.Triangle;

public class MeshSurfaceArea implements SurfaceAreaMethod{

	@Override
	public double getSurfaceArea(Object3D object3d) {
		double sa = 0;
    	ArrayList<Triangle> tris = (ArrayList<Triangle>) object3d.getTris();
    	if (tris != null){
    		for (int i = 0; i < tris.size(); i++){
    			sa += tris.get(i).calcArea();
        	}
    	}
    	return Math.abs(sa);
	}
}
