package obj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import obj.implicit_formula.FormulaAbstract;
import obj.implicit_formula.Genus2;
import obj.implicit_formula.Sphere;
import obj.implicit_formula.Torus;
import ui.MainFrame;

/**
 *
 * @author Imray
 */
public class implicitSurface extends Object3D {

    private FormulaAbstract formula;
    private ArrayList<Vertex> points;
    private ArrayList<Triangle> face;

    public static void main(String[] args) {
        implicitSurface i = new implicitSurface(new Torus(), 0.01, 2);
        MainFrame m = new MainFrame();
        m.setObject(i);
//        ObjExporter o = new ObjExporter("C:\\Users\\Imray\\Desktop\\sadasd.obj");
//        try {
//            o.export(i.getVerts(), new ArrayList<>());
//        } catch (IOException ex) {
//            Logger.getLogger(implicitSurface.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("end");
    }

    public implicitSurface(FormulaAbstract formula, double interval, double checkSize) {
        this.formula = formula;

        points = new ArrayList<>();
        //creates the vertices
        ArrayList<int[]> al = new ArrayList<>();
        int count = 0;
        int countx = -1;
        for (double x = -checkSize; x < checkSize; x += interval) {
            countx++;
            int county = -1;
            for (double y = -checkSize; y < checkSize; y += interval) {
                county++;
                int countz = -1;
                for (double z = -checkSize; z < checkSize; z += interval) {
                    countz++;
                    if (checkOnSurface(x, y, z)) {
                        count++;
                        al.add(new int[]{count, countx, county, countz});
                        points.add(new Vertex(x, y, z));
                    }
                }
            }
        }
        System.out.println(points.size());
        face = new ArrayList<>();
        //possible surface modeling
        for (int i = 0; i < count-2; i++) {
            face.add(new Triangle(points.get(i), points.get(i+1), points.get(i+2)));
        }
        
        face.add(new Triangle(points.get(count-2), points.get(count-1), points.get(0)));
        face.add(new Triangle(points.get(count-1), points.get(0), points.get(1)));
    }

    private boolean checkOnSurface(double x, double y, double z) {
        return (0 == formula.evaluateAt(x, y, z));
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
