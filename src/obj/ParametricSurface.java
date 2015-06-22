package obj;

import java.util.ArrayList;
import java.util.List;

import obj.parametric_formula.FormulaAbstract;

/**
 * A 3D representation of an object via an parametric function
 *
 * @author Kareem Horstink
 * @version 1.0
 */
public class ParametricSurface extends Object3D {

    private FormulaAbstract f;
    private int stepNumberU;
    private int stepNumberV;
    private int[][] vertexIndex;
    private ArrayList<Vertex> ver;
    private ArrayList<Triangle> triangle;

    private double limitMaxU;
    private double limitMinU;
    private double limitMaxV;
    private double limitMinV;
    private double intervalU;
    private double intervalV;

    /**
     * Default constructor
     *
     * @param f The function
     * @param limitLowU The lower limit for U
     * @param limitHighU The higher limit for U
     * @param limitLowV The lower limit for V
     * @param limitHighV The higher limit for V
     * @param stepU The number of steps between the lower and upper for U
     * @param stepV The number of steps between the lower and upper for V
     */
    public ParametricSurface(FormulaAbstract f, double limitLowU, double limitHighU, double limitLowV, double limitHighV, int stepU, int stepV) {
        //sets the formula
        this.f = f;

        //sets the variable
        this.limitMaxU = limitHighU;
        this.limitMinU = limitLowU;
        this.limitMaxV = limitHighV;
        this.limitMinV = limitLowV;
        this.stepNumberU = stepU;
        this.stepNumberV = stepV;
        //sets the rest of the interval
        intervalU = Math.abs(limitMaxU - limitMinU) / stepNumberU;
        intervalV = Math.abs(limitMaxV - limitMinV) / stepNumberV;

        vertexIndex = new int[stepNumberU + 1][stepNumberV + 1];

        //Creates the vertexs
        int count = 0;
        ver = new ArrayList<>();
        double u = limitMinU;
        for (int i = 0; i < stepNumberU + 1; i++) {
            double v = limitMinV;
            for (int k = 0; k < stepNumberV + 1; k++) {
                vertexIndex[i][k] = count;
                count++;
                double[] tmp = f.evaluateAt(u, v);
                ver.add(new Vertex(tmp[0], tmp[1], tmp[2]));
                v += intervalV;
            }
            u += intervalU;
        }

        ArrayList<int[]> face = new ArrayList<>();
        for (int U = 0; U < vertexIndex.length; U++) {
            for (int V = 0; V < vertexIndex[0].length; V++) {
                if (!(U == vertexIndex.length - 1) && !(V == vertexIndex[0].length - 1)) {
                    face.add(new int[]{vertexIndex[U][V], vertexIndex[U + 1][V], vertexIndex[U][V + 1]});
                    face.add(new int[]{vertexIndex[U + 1][V], vertexIndex[U + 1][V + 1], vertexIndex[U][V + 1]});
                }
            }
        }
        triangle = new ArrayList<>();
        for (int[] face1 : face) {
            triangle.add(new Triangle(ver.get(face1[0]), ver.get(face1[1]), ver.get(face1[2])));
        }
    }

    /**
     * Gets the step Number based on U
     *
     * @return Return an int
     */
    public int getStepNumberU() {
        return stepNumberU;
    }

    /**
     * Gets the step Number based on V
     *
     * @return Return an int
     */
    public int getStepNumberV() {
        return stepNumberV;
    }

    /**
     * Gets the limit, the max, of U
     *
     * @return Double of the limit
     */
    public double getLimitMaxU() {
        return limitMaxU;
    }

    /**
     * Gets the limit, the min, of U
     *
     * @return Double of the limit
     */
    public double getLimitMinU() {
        return limitMinU;
    }

    /**
     * Gets the limit, the max, of V
     *
     * @return Double of the limit
     */
    public double getLimitMaxV() {
        return limitMaxV;
    }

    /**
     * Gets the limit, the min, of V
     *
     * @return Double of the limit
     */
    public double getLimitMinV() {
        return limitMinV;
    }

    /**
     * Gets the step size U
     *
     * @return A double to represent the step size
     */
    public double getIntervalU() {
        return intervalU;
    }

    /**
     * Gets the step size V
     *
     * @return A double to represent the step size
     */
    public double getIntervalV() {
        return intervalV;
    }

    /**
     * Gets the formula of the parametric surface
     * @return
     */
    public FormulaAbstract getF() {
        return f;
    }

    @Override
    public List<Vertex> getVerts() {
        return ver;
    }

    @Override
    public List<Triangle> getTris() {
        return triangle;
    }

}

/*
 http://tutorial.math.lamar.edu/Classes/CalcIII/ParametricSurfaces.aspx
 http://en.wikipedia.org/wiki/Parametric_surface
 */
