package obj;

import java.util.ArrayList;
import java.util.List;
import obj.implicit_formula.FormulaAbstract;
import obj.implicit_formula.Torus;
import ui.MainFrame;

/**
 *
 * @author Imray
 */
public class ImplicitSurfaceAdpative extends Object3D {

    private FormulaAbstract formula;
    private ArrayList<Vertex> points;
    private ArrayList<Triangle> face;
    private ArrayList<double[]> normal;
    private double[][][] listOfChecked;

    public static void main(String[] args) {
        ImplicitSurfaceAdpative i = new ImplicitSurfaceAdpative(new Torus(), 0.03125, 2);
        MainFrame m = new MainFrame();
        m.setObject(i);
    }

    public ImplicitSurfaceAdpative(FormulaAbstract formula, double interval, double checkSize) {
        this.formula = formula;

        numberOfChecks = (int) (checkSize * 2 / interval);
        listOfChecked = new double[numberOfChecks][numberOfChecks][numberOfChecks];
        
        face = new ArrayList<>();
        points = new ArrayList<>();
        
        boolean solved = false;
        double intervalAct = checkSize;

        //creates the vertices
        while (solved) {
            if (face.isEmpty() && intervalAct == interval) {
                throw new Error("Could not add any voxel \n Check if there is a formula inputted or if the check size is correct");
            }
            
        }
    }

    int numberOfChecks;

    public double checkInsideV2(double x, double z, double y) {

        if (listOfChecked[(int) x * numberOfChecks][(int) y * numberOfChecks][(int) z * numberOfChecks] != Double.NaN) {
            return listOfChecked[(int) x * numberOfChecks][(int) y * numberOfChecks][(int) z * numberOfChecks];
        } else {
            double tmp = checkInside(x, y, z);
            listOfChecked[(int) x * numberOfChecks][(int) y * numberOfChecks][(int) z * numberOfChecks] = tmp;
            return tmp;
        }

    }

    private boolean checkOnSurface(double x, double y, double z) {
        return (0 == formula.evaluateAt(x, y, z));
    }

    private double checkInside(double x, double y, double z) {
        return (formula.evaluateAt(x, y, z));
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
}
