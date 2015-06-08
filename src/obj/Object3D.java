package obj;

import java.util.List;

public abstract class Object3D {
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
	public abstract List<Triangle> getTries();
	
	/**
	 * Method to get the surface area of an object
	 * @return the surface area
	 */
	public final double getSurfaceArea() {
		return surfaceAreaMethod.getSurfaceArea(this);
	}
	
	/**
	 * Method to get the volume of an object
	 * @return the volume
	 */
	public final double getVolume() {
		return volumeMethod.getVolume(this);
	}
}
