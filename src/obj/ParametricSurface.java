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

    private boolean DEBUG = true;
    private FormulaAbstract x;
    private FormulaAbstract y;
    private FormulaAbstract z;

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

    public static void main(String[] args) {
        ParametricSurface p = new ParametricSurface(new SpiralX(), new SpiralY(), new SpiralZ(), 1, 2, 1, 2, 1, 100, false, false);
        MainFrame m = new MainFrame();
        m.setObject(p);
    }

    public ParametricSurface(FormulaAbstract x, FormulaAbstract y, FormulaAbstract z,
            double limitLowU, double limitHighU, double limitLowV, double limitHighV, int stepU, int stepV, boolean connectEnd, boolean connectSide) {
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
        int counter = 0;
        ver = new ArrayList<>();
        for (double u = limitMinU; u <= limitMaxU; u += intervalU) {
            int counter2 = 0;
            for (double v = limitMinV; v <= limitMaxV; v += intervalV) {
                vertexIndex[counter][counter2] = count;
                count++;
                ver.add(new Vertex(x.evaluateAt(u, v), y.evaluateAt(u, v), z.evaluateAt(u, v)));
                counter2++;
            }
            counter++;
        }

        ArrayList<int[]> face = new ArrayList<>();
        for (int U = 0; U < vertexIndex.length; U++) {
            for (int V = 0; V < vertexIndex[0].length; V++) {
                if (U == vertexIndex.length - 1 && V == vertexIndex[0].length - 1 && connectEnd) {
                    if (DEBUG) {
                        System.out.println("Connecting U and V");
                    }
                    face.add(new int[]{vertexIndex[U][V], vertexIndex[0][V], vertexIndex[U][0]});
                    face.add(new int[]{vertexIndex[0][V], vertexIndex[0][0], vertexIndex[U][0]});
                } else if (V == vertexIndex[0].length - 1 && !(U == vertexIndex.length - 1) && connectSide) {
                    if (DEBUG) {
                        System.out.println("Connecting V");
                    }
                    face.add(new int[]{vertexIndex[U][V], vertexIndex[U + 1][V], vertexIndex[U][0]});
                    face.add(new int[]{vertexIndex[U + 1][V], vertexIndex[U + 1][0], vertexIndex[U][0]});
                } else if (U == vertexIndex.length - 1 && !(V == vertexIndex[0].length - 1) && connectSide) {
                    if (DEBUG) {
                        System.out.println("Connecting U");
                    }
                    face.add(new int[]{vertexIndex[U][V], vertexIndex[0][V], vertexIndex[U][V + 1]});
                    face.add(new int[]{vertexIndex[0][V], vertexIndex[0][V + 1], vertexIndex[U][V + 1]});
                } else if (!(U == vertexIndex.length - 1) && !(V == vertexIndex[0].length - 1)) {
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

}

/*
 http://tutorial.math.lamar.edu/Classes/CalcIII/ParametricSurfaces.aspx
 http://en.wikipedia.org/wiki/Parametric_surface
 */
