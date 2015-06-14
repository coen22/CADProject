package obj.implicit_formula;

/**
 *
 * @author Imray
 */
public class SwissCube extends FormulaAbstract {

    @Override
    public double evaluateAt(double x, double y, double z) {
        double eps = 0.001;
        double tmp = pow2(2.92 * pow2(x - 1) * (x + 1) + 1.7 * pow2(y)) * pow2(pow2(y) - 0.88)
                + pow2(2.92 * pow2(y - 1) * (y + 1) + 1.7 * pow2(z)) * pow2(pow2(z) - 0.88)
                + pow2(2.92 * pow2(z - 1) * (z + 1) + 1.7 * pow2(x)) * pow2(pow2(x) - 0.88) - 0.02;
        if (tmp < eps && tmp > -eps) {
            tmp = 0;
        }
        return tmp;
    }

    private double pow2(double t) {
        return Math.pow(t, 2);
    }

}
