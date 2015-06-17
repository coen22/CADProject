package obj.algorithms;

import obj.Object3D;
import obj.ParametricSurface;

public class ParametricSurfaceArea implements SurfaceAreaMethod{

	@Override
	public double getSurfaceArea(Object3D object3d) {
		ParametricSurface para = (ParametricSurface) object3d;
		return para.calcSurfaceArea();
	}

}
