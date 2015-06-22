package obj;

import java.util.ArrayList;
import java.util.List;

import obj.algorithms.SurfaceAreaMethod;
import obj.algorithms.VolumeMethod;

public abstract class Object3D {
	
	public static final String MESH_SA = "Mesh triangulation";
	public static final String MESH_VOL = "Signed Volume";
	public static final String CURVE_SA = "Rotational s.a.";
	public static final String CURVE_VOL = "Rotational volume";
	public static final String PARA_APPROX = "General s.a.";
	public static final String PARA_SA_TRAP = "Trapezoid s.a.";
	public static final String PARA_SA_SIMP = "Simpson's s.a.";
	public static final String PARA_SA_RICH = "Richardson s.a.";
	
	protected ArrayList<String> volumeMethods;
	protected ArrayList<String> surfaceAreaMethods;
	
	public Object3D(){
		this.volumeMethods = new ArrayList<String>();
		this.surfaceAreaMethods = new ArrayList<String>();
	}
	
	/**
	 * The strategy used for calculating the surface area
	 */
	protected SurfaceAreaMethod surfaceAreaMethod;
	
	/**
	 * The strategy used for calculating the volume
	 */
	protected VolumeMethod volumeMethod;
	
	/**
	 * Method to get the vertices of a model
	 * @return a list of vertices 
	 */
	public abstract List<Vertex> getVerts();
	
	/**
	 * Method to get the list of triangles of a model
	 * @return a list of Triangles
	 */
	public abstract List<Triangle> getTris();
	
	/**
	 * Method to get the surface area of an object
	 * @return the surface area
	 */
	public final double getSurfaceArea() {
		if (surfaceAreaMethod != null){
			return surfaceAreaMethod.getSurfaceArea(this);
		}
		else {
			return 0.0;
		}
	}
	
	/**
	 * Method to get the volume of an object
	 * @return the volume
	 */
	public final double getVolume() {
		if (volumeMethod != null){
			return volumeMethod.getVolume(this);
		}
		else {
			return 0.0;
		}
	}
	
	/**
	 * sets the surface-area strategy to a different method
	 * @param method
	 */
	public void setSurfaceAreaMethod(SurfaceAreaMethod method){
		this.surfaceAreaMethod = method;
	}
	
	/**
	 * sets the volume strategy to a different method
	 * @param method
	 */
	public void setVolumeMethod(VolumeMethod method){
		this.volumeMethod = method;
	}
	
	public ArrayList<String> getSurfaceAreaMethods(){
		return surfaceAreaMethods;
	}
	
	public ArrayList<String> getVolumeMethods(){
		return volumeMethods;
	}
}
