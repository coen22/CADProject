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
import obj.parametric_formula.TorusX;
import obj.parametric_formula.TorusY;
import obj.parametric_formula.TorusZ;
import obj.parametric_formula.TrumpetX;
import obj.parametric_formula.TrumpetY;
import obj.parametric_formula.TrumpetZ;

/**
 *
 * @author Kareem Horstink
 */
public class ParametricSurface extends Object3D {

    private FormulaAbstract x;
    private FormulaAbstract y;
    private FormulaAbstract z;

    private double limitMaxU = Math.PI * 2;
    private double limitMinU = 0;
    private double limitMaxV = Math.PI * 2;
    private double limitMinV = 0;
    private double interval = Math.PI / 30;

    public static void main(String[] args) {
        ParametricSurface p = new ParametricSurface(new TorusX(), new TorusY(), new TorusZ());
        p.test();
    }

    private void test() {

        try {
            int stepNumberU = (int) (Math.abs(limitMinU - limitMaxU) / interval);
            int stepNumberV = (int) (Math.abs(limitMinV - limitMaxV) / interval);

            int[][] vertexIndex = new int[stepNumberU + 1][stepNumberV + 1];
            double[][][] vertex = new double[stepNumberU + 1][stepNumberV + 1][3];
            ArrayList<int[]> face = new ArrayList<>();

            int count = 0;
            int counter = 0;
            for (double u = limitMinU; u <= limitMaxU; u += interval) {
                int counter2 = 0;
                for (double v = limitMinV; v <= limitMaxV; v += interval) {
                    count++;
                    vertexIndex[counter][counter2] = count;
                    vertex[counter][counter2][0] = x.mumble(u, v);
                    vertex[counter][counter2][1] = y.mumble(u, v);
                    vertex[counter][counter2][2] = z.mumble(u, v);
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

    public ParametricSurface(FormulaAbstract x, FormulaAbstract y, FormulaAbstract z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public List<Vertex> getVerts() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Triangle> getTris() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

/*
 http://tutorial.math.lamar.edu/Classes/CalcIII/ParametricSurfaces.aspx
 http://en.wikipedia.org/wiki/Parametric_surface
 */
