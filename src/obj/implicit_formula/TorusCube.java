package obj.implicit_formula;

/**
 *
 * @author Imray
 */
public class TorusCube extends FormulaAbstract {

    @Override
    public double evaluateAt(double x, double y, double z) {
        double eps = 0.001;
        double tmp = (pow2(pow2(x) + pow2(y) - pow2(0.85)) + pow2(pow2(z) - 1))
                * (pow2(pow2(y) + pow2(z) - pow2(0.85)) + pow2(pow2(x) - 1))
                * (pow2(pow2(z) + pow2(x) - pow2(0.85)) + pow2(pow2(y) - 1)) - 0.001;
        if (tmp < eps && tmp > -eps) {
            tmp = 0;
        }
        return tmp;
    }

    private double pow2(double t) {
        return Math.pow(t, 2);
    }

}
