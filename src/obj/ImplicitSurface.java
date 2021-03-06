package obj;

import java.util.ArrayList;
import java.util.List;

import obj.implicit_formula.FormulaAbstract;

/**
 * A 3D representation of an object via an implicit function
 *
 * @author Kareem Horstink
 * @version 1.0
 */
public class ImplicitSurface extends Object3D {

    private FormulaAbstract formula;
    private ArrayList<Vertex> points;
    private ArrayList<Triangle> face;
    private ArrayList<double[]> normal;
    private double checkSize;
    private double interval;
    private int voxelCount;
    private boolean voxels;

    /**
     * Use this one for a voxel one
     *
     * @param formula
     * @param interval
     * @param checkSize
     */
    public ImplicitSurface(FormulaAbstract formula, double interval, double checkSize) {
        this.volumeMethods.add(Object3D.IMPLICIT_VOL);
        this.voxels = true;
        
        this.formula = formula;
        this.checkSize = checkSize;
        this.interval = interval;
        face = new ArrayList<>();
        points = new ArrayList<>();

        //creates the vertices
        for (double x = -checkSize; x < checkSize; x += interval) {
            for (double y = -checkSize; y < checkSize; y += interval) {
                for (double z = -checkSize; z < checkSize; z += interval) {
                    if (checkInside(x, y, z)) {
                        points.add(new Vertex(x, y, z, new double[]{partX(x, y, z, interval), partY(x, y, z, interval), partZ(x, y, z, interval)}));
                        voxelCount++;
                        for (Triangle tmp21 : (ArrayList< Triangle>) new Voxel(interval, x, y, z).getTris()) {
                            face.add(tmp21);
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the value of voxelCount
     *
     * @return the value of voxelCount
     */
    public int getVoxelCount() {
        return voxelCount;
    }

    public double getInterval() {
        return interval;
    }

    /**
     * Creates a point on the surface
     */
    private void createPoints() {
        face = null;
        points = new ArrayList<>();
        for (double x = -checkSize; x < checkSize; x += interval) {
            for (double y = -checkSize; y < checkSize; y += interval) {
                for (double z = -checkSize; z < checkSize; z += interval) {
                    if (checkInside(x, y, z)) {
                        points.add(new Vertex(x, y, z, new double[]{partX(x, y, z, interval), partY(x, y, z, interval), partZ(x, y, z, interval)}));
                    }
                }
            }
        }
    }

    /**
     * Creates a voxel
     */
    private void createVoxel() {
        face = new ArrayList<>();
        points = new ArrayList<>();
        for (double x = -checkSize; x < checkSize; x += interval) {
            for (double y = -checkSize; y < checkSize; y += interval) {
                for (double z = -checkSize; z < checkSize; z += interval) {
                    if (checkInside(x, y, z)) {
                        for (Triangle tmp21 : (ArrayList< Triangle>) new Voxel(interval, x, y, z).getTris()) {
                            face.add(tmp21);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if a point lies on the surface
     *
     * @param x The x coordinates
     * @param y The y coordinates
     * @param z The z coordinates
     * @return Returns a boolean if the object on the surface
     */
    private boolean checkOnSurface(double x, double y, double z) {
        return (0 == formula.evaluateAt(x, y, z));
    }

    /**
     * Checks if a point lies inside of the surface
     *
     * @param x The x coordinates
     * @param y The y coordinates
     * @param z The z coordinates
     * @return Returns a boolean if the object on the surface
     */
    private boolean checkInside(double x, double y, double z) {
        return (0 > formula.evaluateAt(x, y, z));
    }

    /**
     * Gets the normal of all the points
     *
     * @return Double {x,y,z}
     */
    public ArrayList<double[]> getNormal() {
        return normal;
    }

    @Override
    public List<Vertex> getVerts() {
        return points;
    }

    @Override
    public List<Triangle> getTris() {
        return face;
    }

    /**
     * Partial derivative on x based on f'(x) = lim(h<-0)((f(x+h)-f(x))\h)
     *
     * @param x The x coordinates
     * @param y The y coordinates
     * @param z The z coordinates
     * @param inter Should be close to 0
     * @return The estimate of the partial derivative
     */
    private double partX(double x, double y, double z, double inter) {
        return formula.evaluateAt(x + inter, y, z) - formula.evaluateAt(x, y, z);
    }

    /**
     * Partial derivative on z based on f'(x) = lim(h<-0)((f(x+h)-f(x))\h)
     *
     * @param x The x coordinates
     * @param y The y coordinates
     * @param z The z coordinates
     * @param inter Should be close to 0
     * @return The estimate of the partial derivative
     */
    private double partZ(double x, double y, double z, double inter) {
        return formula.evaluateAt(x, y, z + inter) - formula.evaluateAt(x, y, z);
    }

    /**
     * Partial derivative on y based on f'(x) = lim(h<-0)((f(x+h)-f(x))\h)
     *
     * @param x The x coordinates
     * @param y The y coordinates
     * @param z The z coordinates
     * @param inter Should be close to 0
     * @return The estimate of the partial derivative
     */
    private double partY(double x, double y, double z, double inter) {
        return formula.evaluateAt(x, y + inter, z) - formula.evaluateAt(x, y, z);
    }

    /**
     * toggles between voxels and surface faces
     */
    public void toggleVoxels(){
    	if (voxels){
    		voxels = false;
    		createPoints();
    	}
    	else {
    		voxels = true;
    		createVoxel();
    	}
    }
}
