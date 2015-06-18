package obj.parametric_formula;

/**
 *
 * @author Kareem
 */
public class SphereZ extends FormulaAbstract {

    @Override
    public double evaluateAt(double u, double v) {
        return Math.cos(u) * Math.sin(v);
    }

    @Override
    public double interU(double a, double b) {
        return Math.sin(a) * Math.sin(b) * -1;
    }

    @Override
    public double interV(double a, double b) {
        return Math.cos(a)*Math.cos(b);
    }

}
