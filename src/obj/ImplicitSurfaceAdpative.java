package obj;

import java.util.ArrayList;
import java.util.List;
import obj.implicit_formula.FormulaAbstract;
import obj.implicit_formula.Sphere;
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
        ImplicitSurfaceAdpative i = new ImplicitSurfaceAdpative(new Sphere(), 0.02, 2);
//        Voxel f = new Voxel(5, 0, 0, 0);
    }

    public ImplicitSurfaceAdpative(FormulaAbstract formula, double interval, double checkSize) {
        this.formula = formula;
        this.interval = interval;
        numberOfChecks = (int) ((checkSize * 2) / interval);
        listOfChecked = new double[numberOfChecks * 4 + 1][numberOfChecks * 4 + 1][numberOfChecks * 4 + 1];

        face = new ArrayList<>();
        points = new ArrayList<>();

        for (int i = 0; i < listOfChecked.length; i++) {
            for (int j = 0; j < listOfChecked[i].length; j++) {
                for (int k = 0; k < listOfChecked[i][j].length; k++) {
                    listOfChecked[i][j][k] = 0.696969696;
                }
            }
        }

        SheGotABigBootySoICallHerBigBooty(0, 0, 0, checkSize);

        System.out.println("face number " + face.size());
    }

    int numberOfChecks;
    double interval;

    private void SheGotABigBootySoICallHerBigBooty(double x, double z, double y, double size) {
        System.out.println("Hi big booty");
        boolean[][][] shaniquka = new boolean[3][3][3];
        if (size <= interval) {
            if (checkInsideV3(x, z, y)) {
                face.addAll(new Voxel(size, x, y, z).getTris());
            }
        } else {
            for (int i = 0; i < shaniquka.length; i++) {
                for (int j = 0; j < shaniquka.length; j++) {
                    for (int k = 0; k < shaniquka.length; k++) {
                        shaniquka[i][j][k] = checkInsideV3(x - size + i * size, z - size + j * size, y - size + k * size);
                    }
                }
            }
            boolean tmp = true;
            twoChainz:
            {
                for (int i = 0; i < shaniquka.length; i++) {
                    for (int j = 0; j < shaniquka.length; j++) {
                        for (int k = 0; k < shaniquka.length; k++) {
                            if (shaniquka[i][j][k] == false) {
                                tmp = false;
                                break twoChainz;
                            }
                        }
                    }
                }
            }

            if (tmp) {
                face.addAll(new Voxel(size, x, y, z).getTris());
                System.out.println("tmp");
                return;
            } else {
                for (int i = 0; i < shaniquka.length - 1; i++) {
                    for (int j = 0; j < shaniquka.length - 1; j++) {
                        for (int k = 0; k < shaniquka.length - 1; k++) {
                            if (shaniquka[i][j][k] == true) {
                                SheGotABigBootySoICallHerBigBooty(
                                        x - size + i * size,
                                        z - size + j * size,
                                        y - size + k * size,
                                        size / 2);
                                System.out.println("ello");
                            }
                        }
                    }
                }
            }

        }

    }

    private boolean checkInsideV3(double x, double z, double y) {
        return checkInsideV2(x, z, y) < 0;
    }

    private double checkInsideV2(double x, double z, double y) {
        if (listOfChecked[(int) (numberOfChecks * 2 + x * numberOfChecks)][(int) (numberOfChecks * 2 + y * numberOfChecks)][(int) (numberOfChecks * 2 + z * numberOfChecks)]
                != 0.696969696) {
            System.out.println("should not be here");
            return listOfChecked[(int) (numberOfChecks * 2 + x * numberOfChecks)][(int) (numberOfChecks * 2 + y * numberOfChecks)][(int) (numberOfChecks * 2 + z * numberOfChecks)];
        } else {
            double tmp = checkInsideV1(x, y, z);
            listOfChecked[(int) (numberOfChecks * 2 + x * numberOfChecks)][(int) (numberOfChecks * 2 + y * numberOfChecks)][(int) (numberOfChecks * 2 + z * numberOfChecks)] = tmp;
            return tmp;
        }
    }

    private boolean checkOnSurface(double x, double y, double z) {
        return (0 == formula.evaluateAt(x, y, z));
    }

    private double checkInsideV1(double x, double y, double z) {
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
