package obj;

public class TriangleSurfaceArea implements SurfaceAreaMethod{

	@Override
	public double getSurfaceArea(Object3D object3d) {
		double tempArea = 0;
		for(int i = 0; i < object3d.getTris().size(); i++){
			tempArea += (object3d.getTris().get(i)).calcArea();
		}
		
		return tempArea;
	}
	
	

}
