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
    private double limitMaxV = 2 * Math.PI;
    private double limitMinV = 0;
    private double interval = Math.PI/30;

    public static void main(String[] args) {
        ParametricSurface p = new ParametricSurface(new TorusX(), new TorusY(), new TorusZ());
        p.test();
    }

    private void test() {

        try {
            ArrayList<double[]> vertex = new ArrayList<>();
            ArrayList<int[]> face = new ArrayList<>();
//            int counter = 0;

            for (double u = limitMinU; u <= limitMaxU; u += interval) {
                for (double v = limitMinV; v <= limitMaxV; v += interval) {
                    vertex.add(new double[]{x.mumble(u, v), y.mumble(u, v), z.mumble(u, v)});
//                    counter++;
//                    if (counter / 3 > 0) {
//                        face.add(new int[]{counter, counter - 1, counter - 2});
//                    }
                }
            }
            System.out.println(Math.abs(limitMinU - limitMaxU) / interval);
            int stepNumber = (int) (Math.abs(limitMinU - limitMaxU) / interval);
            for (int i = 0; i < stepNumber; i++) {
                for (int j = 0; j < stepNumber; j++) {
                    face.add(new int[]{
                        j + (i * stepNumber), (j + 1 + (i * stepNumber)) + stepNumber, j + (i * stepNumber) + 1
                    });
                    face.add(new int[]{
                        j + (i * stepNumber), (j + 1 + (i * stepNumber)) + stepNumber, (j + (i * stepNumber)) + stepNumber
                    });
                }
            }

            //Just need to add last row
            for (int j = 1; j < stepNumber+1; j++) {
                face.add(new int[]{
                    j, (j) + stepNumber*stepNumber, j + 1
                });
                face.add(new int[]{
                    j, (j) + stepNumber*stepNumber, (j-1) + stepNumber*stepNumber
                });
            }
//            face.add(new int[]{1 })

            WriteFile w = new WriteFile("C:\\Users\\Imray\\Desktop\\New Text Document.obj");
            w.writeToFile("");

            w.setAppend(true);
            String tmp = "";
            for (double[] vertex1 : vertex) {
                tmp += ("v " + vertex1[0] + " " + vertex1[1] + " " + vertex1[2] + "\n");
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
