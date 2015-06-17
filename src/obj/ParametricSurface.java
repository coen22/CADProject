package obj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.parametric_formula.FormulaAbstract;
import obj.parametric_formula.ShoeX;
import obj.parametric_formula.ShoeY;
import obj.parametric_formula.ShoeZ;
import obj.parametric_formula.SphereX;
import obj.parametric_formula.SphereY;
import obj.parametric_formula.SphereZ;
import obj.parametric_formula.SpiralX;
import obj.parametric_formula.SpiralY;
import obj.parametric_formula.SpiralZ;
import obj.parametric_formula.TorusX;
import obj.parametric_formula.TorusY;
import obj.parametric_formula.TorusZ;
import obj.parametric_formula.TrumpetX;
import obj.parametric_formula.TrumpetY;
import obj.parametric_formula.TrumpetZ;
import ui.MainFrame;

/**
 *
 * @author Kareem Horstink
 */
public class ParametricSurface extends Object3D {

    private boolean DEBUG = false;
    private FormulaAbstract x;
    private FormulaAbstract y;
    private FormulaAbstract z;
    private static final double PI = Math.PI;
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

    public ParametricSurface(FormulaAbstract x, FormulaAbstract y, FormulaAbstract z,
            double limitLowU, double limitHighU, double limitLowV, double limitHighV, int stepU, int stepV) {
        //sets the formula
        this.x = x;
        this.y = y;
        this.z = z;

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
        for (int i = 0; i < stepNumberU + 1; i++){
            double v = limitMinV;
            for (int k = 0; k < stepNumberV + 1; k++){
            	vertexIndex[i][k] = count;
            	count++;
            	ver.add(new Vertex(x.evaluateAt(u, v), y.evaluateAt(u, v), z.evaluateAt(u, v)));
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

    @Override
    public List<Vertex> getVerts() {
        return ver;
    }

    @Override
    public List<Triangle> getTris() {
        return triangle;
    }

    public double surfaceArea() {
        double startU = limitMinU;
        double startV = limitMinV;
        double endU = limitMaxU;
        double endV = limitMaxV;
        double deltaU = intervalU;
        double deltaV = intervalV;
        double area = 0;
        for (double v = startV; v < endV; v += intervalV) {
            for (double u = startU; u < endU; u += intervalU) {
                area += mag(crossProduct(partU(u, v, deltaU), partV(u, v, deltaV)));
            }
        }
        return area;
    }

    private double[] partU(double u, double v, double interU) {
        double[] ans = new double[3];
        ans[0] = x.evaluateAt(u + interU, v) - x.evaluateAt(u, v);
        ans[1] = y.evaluateAt(u + interU, v) - y.evaluateAt(u, v);
        ans[2] = z.evaluateAt(u + interU, v) - z.evaluateAt(u, v);
        return ans;
    }

    private double[] partV(double u, double v, double interV) {
        double[] ans = new double[3];
        ans[0] = x.evaluateAt(u, v + interV) - x.evaluateAt(u, v);
        ans[1] = y.evaluateAt(u, v + interV) - y.evaluateAt(u, v);
        ans[2] = z.evaluateAt(u, v + interV) - z.evaluateAt(u, v);
        return ans;
    }

    private double[] crossProduct(double[] a, double[] b) {
        double[] c = new double[3];
        c[0] = a[1] * b[2] - a[2] * b[1];
        c[1] = a[2] * b[0] - a[0] * b[2];
        c[2] = a[0] * b[1] - a[1] * b[0];
        return c;
    }

    private double mag(double[] a) {
        return Math.sqrt(Math.pow(a[0], 2) + Math.pow(a[1], 2) + Math.pow(a[2], 2));
    }

}

/*
 http://tutorial.math.lamar.edu/Classes/CalcIII/ParametricSurfaces.aspx
 http://en.wikipedia.org/wiki/Parametric_surface
 */
