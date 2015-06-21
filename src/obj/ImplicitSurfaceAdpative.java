package obj;

import java.util.ArrayList;
import java.util.List;

import obj.implicit_formula.FormulaAbstract;
import obj.implicit_formula.Sphere;

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

//    public static void main(String[] args) {
//        ImplicitSurfaceAdpative i = new ImplicitSurfaceAdpative(new Sphere(), 0.1, 2);
//        Voxel f = new Voxel(5, 0, 0, 0);
//
//    }

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
                    listOfChecked[i][j][k] = 0.69696966969;
                }
            }
        }

        recursion(0, 0, 0, checkSize);

        System.out.println("face number " + face.size());
    }

    int numberOfChecks;
    double interval;

    private void recursion(double x, double z, double y, double size) {
        boolean[][][] pointChecker = new boolean[3][3][3];
        if (size <= interval) {
            if (checkInsideV3(x, z, y)) {
                face.addAll(new Voxel(size, x, y, z).getTris());
            }
        } else {
            for (int i = 0; i < pointChecker.length; i++) {
                for (int j = 0; j < pointChecker.length; j++) {
                    for (int k = 0; k < pointChecker.length; k++) {
                        pointChecker[i][j][k] = checkInsideV3(x - size + i * size, z - size + j * size, y - size + k * size);
                    }
                }
            }
            boolean tmp = true;
            insideLoop:
            {
                for (int i = 0; i < pointChecker.length; i++) {
                    for (int j = 0; j < pointChecker.length; j++) {
                        for (int k = 0; k < pointChecker.length; k++) {
                            if (pointChecker[i][j][k] == false) {
                                tmp = false;
                                break insideLoop;
                            }
                        }
                    }
                }
            }
            

            if (tmp) {
                face.addAll(new Voxel(size, x + size / 2, y + size / 2, z + size / 2).getTris());
                return;
            } else {
                for (int i = 0; i < pointChecker.length - 0; i++) {
                    for (int j = 0; j < pointChecker.length; j++) {
                        for (int k = 0; k < pointChecker.length; k++) {
                            if (pointChecker[i][j][k] == true) {
                                recursion(
                                        x - size + i * size / 2,
                                        z - size + j * size / 2,
                                        y - size + k * size / 2,
                                        size / 2);
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
        if (listOfChecked
                [(int) (numberOfChecks * 2 + x * numberOfChecks)]
                [(int) (numberOfChecks * 2 + y * numberOfChecks)]
                [(int) (numberOfChecks * 2 + z * numberOfChecks)]
                != 0.69696966969) {
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
