package obj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.parametric_formula.FormulaAbstract;
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
        ParametricSurface p = new ParametricSurface(new SpiralX(), new SpiralY(), new SpiralZ(), 1, 2, 0, 1, 20, 20);
        MainFrame m = new MainFrame();
        m.setObject(p);
    }

    private void test() {

        try {
            int stepNumberU = (int) (Math.abs(limitMinU - limitMaxU) / intervalU);
            int stepNumberV = (int) (Math.abs(limitMinV - limitMaxV) / intervalU);

            int[][] vertexIndex = new int[stepNumberU + 1][stepNumberV + 1];
            double[][][] vertex = new double[stepNumberU + 1][stepNumberV + 1][3];
            ArrayList<int[]> face = new ArrayList<>();

            int count = 0;
            int counter = 0;
            for (double u = limitMinU; u <= limitMaxU; u += intervalU) {
                int counter2 = 0;
                for (double v = limitMinV; v <= limitMaxV; v += intervalU) {
                    count++;
                    vertexIndex[counter][counter2] = count;
                    vertex[counter][counter2][0] = x.evaluateAt(u, v);
                    vertex[counter][counter2][1] = y.evaluateAt(u, v);
                    vertex[counter][counter2][2] = z.evaluateAt(u, v);
                    counter2++;
                }
                counter++;
            }

            for (int i = 0; i < vertex.length; i++) {
                for (int j = 0; j < vertex[0].length; j++) {
                    if (i == vertex.length - 1 && j == vertex[0].length - 1) {
                        face.add(new int[]{vertexIndex[i][j], vertexIndex[0][j], vertexIndex[i][0]});
                        face.add(new int[]{vertexIndex[0][j], vertexIndex[0][0], vertexIndex[i][0]});
                    } else if (j == vertex[0].length - 1) {
                        face.add(new int[]{vertexIndex[i][j], vertexIndex[i + 1][j], vertexIndex[i][0]});
                        face.add(new int[]{vertexIndex[i + 1][j], vertexIndex[i + 1][0], vertexIndex[i][0]});
                    } else if (i == vertex.length - 1) {
                        face.add(new int[]{vertexIndex[i][j], vertexIndex[0][j], vertexIndex[i][j + 1]});
                        face.add(new int[]{vertexIndex[0][j], vertexIndex[0][j + 1], vertexIndex[i][j + 1]});
                    } else {
                        face.add(new int[]{vertexIndex[i][j], vertexIndex[i + 1][j], vertexIndex[i][j + 1]});
                        face.add(new int[]{vertexIndex[i + 1][j], vertexIndex[i + 1][j + 1], vertexIndex[i][j + 1]});
                    }
                }
            }

            WriteFile w = new WriteFile("C:\\Users\\Kareem\\Desktop\\New Text Document.obj");
            w.writeToFile("");

            w.setAppend(true);
            String tmp = "";
            for (int i = 0; i < stepNumberU; i++) {
                for (int j = 0; j < stepNumberV; j++) {
                    tmp += ("v " + vertex[i][j][0] + " " + vertex[i + 1][j][1] + " " + vertex[i][j + 1][2] + "\n");
                }
            }

            w.writeToFile(tmp);

            tmp = "";
            for (int[] face1 : face) {
                tmp += "f " + face1[0] + " " + face1[1] + " " + face1[2] + "\n";
            }

            w.writeToFile(tmp);

        } catch (IOException ex) {
            Logger.getLogger(ParametricSurface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
        double[][][] vertexDouble = new double[stepNumberU + 1][stepNumberV + 1][3];

        //Creates the vertexs
        int count = -1;
        int counter = 0;
        ver = new ArrayList<>();
        for (double u = limitMinU; u <= limitMaxU; u += intervalU) {
            int counter2 = 0;
            for (double v = limitMinV; v <= limitMaxV; v += intervalV) {
                count++;
                vertexIndex[counter][counter2] = count;
                ver.add(new Vertex(x.evaluateAt(u, v), y.evaluateAt(u, v), z.evaluateAt(u, v)));
                counter2++;
            }
            counter++;
        }

        ArrayList<int[]> face = new ArrayList<>();
        for (int i = 0; i < vertexDouble.length; i++) {
            for (int j = 0; j < vertexDouble[0].length; j++) {
                if (i == vertexDouble.length - 1 && j == vertexDouble[0].length - 1) {
                    if (DEBUG) {
                        System.out.println("Connecting U and V");
                    }
                    face.add(new int[]{vertexIndex[i][j], vertexIndex[0][j], vertexIndex[i][0]});
                    face.add(new int[]{vertexIndex[0][j], vertexIndex[0][0], vertexIndex[i][0]});
                } else if (j == vertexDouble[0].length - 1) {
                    if (DEBUG) {
                        System.out.println("Connecting V");
                    }
                    face.add(new int[]{vertexIndex[i][j], vertexIndex[i + 1][j], vertexIndex[i][0]});
                    face.add(new int[]{vertexIndex[i + 1][j], vertexIndex[i + 1][0], vertexIndex[i][0]});
                } else if (i == vertexDouble.length - 1) {
                    if (DEBUG) {
                        System.out.println("Connecting U");
                    }
                    face.add(new int[]{vertexIndex[i][j], vertexIndex[0][j], vertexIndex[i][j + 1]});
                    face.add(new int[]{vertexIndex[0][j], vertexIndex[0][j + 1], vertexIndex[i][j + 1]});
                } else {
                    face.add(new int[]{vertexIndex[i][j], vertexIndex[i + 1][j], vertexIndex[i][j + 1]});
                    face.add(new int[]{vertexIndex[i + 1][j], vertexIndex[i + 1][j + 1], vertexIndex[i][j + 1]});
                }
            }
        }
        triangle = new ArrayList<>();
        for (int[] face1 : face) {
            triangle.add(new Triangle(ver.get(face1[0]-1), ver.get(face1[1]-1), ver.get(face1[2]-1)));
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
