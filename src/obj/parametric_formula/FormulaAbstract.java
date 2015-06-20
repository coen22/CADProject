package obj.parametric_formula;

/**
 *
 * @author Kareem Horstink
 */
public abstract class FormulaAbstract {

    public abstract double[] evaluateAt(double u, double v);

    public double[] partialU(double u, double v) {
        return null;
    }

    public double[] partialV(double u, double v) {
        return null;
    }
}
