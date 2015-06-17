package ui.algorithms;

import java.util.ArrayList;

import obj.Object3D;
import obj.Triangle;

public class MeshVolume implements VolumeMethod{

	@Override
	public double getVolume(Object3D object3d) {
		double volume = 0;
    	ArrayList<Triangle> tris = (ArrayList<Triangle>) object3d.getTris();
    	if (tris != null){
    		for (int i = 0; i < tris.size(); i++){
        		volume += tris.get(i).calcVolume();
        	}
    	}
    	return Math.abs(volume);
	}

}
