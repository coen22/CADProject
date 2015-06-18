package obj.parametric_formula;

/**
 *
 * @author Kareem Horstink
 */
public class SphereX extends FormulaAbstract {

    @Override
    public double evaluateAt(double u, double v) {
        return Math.cos(u) * Math.cos(v);
    }

    @Override
    public double interU(double a, double b) {
        return Math.sin(b) * (Math.cos(a) - a * Math.sin(a));
    }

    @Override
    public double interV(double a, double b) {
        return Math.sin(a) * (Math.sin(b) + b * Math.cos(b));
    }
}
