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
import obj.implicit_formula.SwissCube;
import obj.implicit_formula.Torus;
import obj.implicit_formula.TorusCube;
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
        ImplicitSurface i = new ImplicitSurface(new Sphere(), 0.05, 1);
        MainFrame m = new MainFrame();
        m.setObject(i);
    }

    public ImplicitSurface(FormulaAbstract formula, double interval, double checkSize) {
        this.formula = formula;

        face = new ArrayList<>();
        points = new ArrayList<>();
        
        System.out.println("Number of function evalution to do " + Math.pow(checkSize/interval, 3));
        //creates the vertices

        for (double x = -checkSize; x < checkSize; x += interval) {
            for (double y = -checkSize; y < checkSize; y += interval) {
                for (double z = -checkSize; z < checkSize; z += interval) {
                    if (checkInside(x, y, z)) {
                        points.add(new Vertex(x, y, z, new double[]{partX(x, y, z, interval), partY(x, y, z, interval), partZ(x, y, z, interval)}));
                        Voxel tmp = new Voxel(interval, x, y, z);
                        ArrayList<Triangle> tmp2 = (ArrayList< Triangle>) tmp.getTris();
                        for (Triangle tmp21 : tmp2) {
                            face.add(tmp21);
                        }
                    }
                }
            }
        }


    }

    private boolean checkOnSurface(double x, double y, double z) {
        return (0 == formula.evaluateAt(x, y, z));
    }

    private boolean checkInside(double x, double y, double z) {
        return (0 > formula.evaluateAt(x, y, z));
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
        return face;
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
