package obj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.implicit_formula.Diamond;
import obj.implicit_formula.FormulaAbstract;
import obj.implicit_formula.Genus2;
import obj.implicit_formula.Neovius;
import obj.implicit_formula.Sphere;
import obj.implicit_formula.SumOfSins;
import obj.implicit_formula.Torus;
import obj.implicit_formula.TorusIntersectSphere;
import ui.MainFrame;

/**
 *
 * @author Imray
 */
public class ImplicitSurface extends Object3D {

    private FormulaAbstract formula;
    private ArrayList<Vertex> points;
    private ArrayList<Triangle> face;
    private ArrayList<double[]> normal;

    public static void main(String[] args) {
        ImplicitSurface i = new ImplicitSurface(new Genus2(), 0.01, 2);
        MainFrame m = new MainFrame();
        m.setObject(i);
//        ObjExporter o = new ObjExporter("C:\\Users\\Kareem\\Desktop\\New Text Document.obj");
//        try {
//            o.export(i.getVerts(), new ArrayList<>());
//        } catch (IOException ex) {
//            Logger.getLogger(ImplicitSurface.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public ImplicitSurface(FormulaAbstract formula, double interval, double checkSize) {
        this.formula = formula;

        points = new ArrayList<>();
        //creates the vertices

        int count = 0;
        normal = new ArrayList<>();
        for (double x = -checkSize; x < checkSize; x += interval) {
            for (double y = -checkSize; y < checkSize; y += interval) {
                for (double z = -checkSize; z < checkSize; z += interval) {
                    if (checkOnSurface(x, y, z)) {
                        count++;
                        points.add(new Vertex(x, y, z, new double[]{partX(x, y, z, interval), partY(x, y, z, interval), partZ(x, y, z, interval)}));
                        normal.add(new double[]{partX(x, y, z, interval), partY(x, y, z, interval), partZ(x, y, z, interval)});
                    }
                }
            }
        }

        face = new ArrayList<>();
        //possible surface modeling
        for (int i = 0; i < count - 2; i++) {
            face.add(new Triangle(points.get(i), points.get(i + 1), points.get(i + 2)));
        }

        face.add(new Triangle(points.get(count - 2), points.get(count - 1), points.get(0)));
        face.add(new Triangle(points.get(count - 1), points.get(0), points.get(1)));
        System.out.println(face.size());
    }

    private boolean checkOnSurface(double x, double y, double z) {
        return (0 == formula.evaluateAt(x, y, z));
    }

    public ArrayList<double[]> getNormal() {
        return normal;
    }

    @Override
    public List<Vertex> getVerts() {
        return points;
    }

    @Override
    public List<Triangle> getTris() {
        return null;
    }

    private double partX(double x, double y, double z, double inter) {
        return formula.evaluateAt(x + inter, y, z) - formula.evaluateAt(x, y, z);
    }

    private double partZ(double x, double y, double z, double inter) {
        return formula.evaluateAt(x, y, z + inter) - formula.evaluateAt(x, y, z);
    }

    private double partY(double x, double y, double z, double inter) {
        return formula.evaluateAt(x, y + inter, z) - formula.evaluateAt(x, y, z);
    }
}
