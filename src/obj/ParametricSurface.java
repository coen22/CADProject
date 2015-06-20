package obj;

import java.util.ArrayList;
import java.util.List;

import obj.parametric_formula.FormulaAbstract;

/**
 *
 * @author Kareem Horstink
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

    public int getStepNumberU() {
        return stepNumberU;
    }

    public int getStepNumberV() {
        return stepNumberV;
    }

    public double getLimitMaxU() {
        return limitMaxU;
    }

    public double getLimitMinU() {
        return limitMinU;
    }

    public double getLimitMaxV() {
        return limitMaxV;
    }

    public double getLimitMinV() {
        return limitMinV;
    }

    public double getIntervalU() {
        return intervalU;
    }

    public double getIntervalV() {
        return intervalV;
    }

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
