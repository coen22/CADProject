package obj.parametric_formula;

/**
 * A abstract class representing the implicit formula
 *
 * @author Kareem Horstink
 * @version 1.0
 */
public abstract class FormulaAbstract {

    /**
     * Evaluates the function based on the parameters
     *
     * @param u The u parameter
     * @param v The v parameter
     * @return The double vector
     */
    public abstract double[] evaluateAt(double u, double v);

    /**
     * The partial derivative if its hard coded in
     *
     * @param u The u parameter
     * @param v The v parameter
     * @return The double vector
     */
    public double[] partialU(double u, double v) {
        return null;
    }

    /**
     * The partial derivative if its hard coded in
     *
     * @param u The u parameter
     * @param v The v parameter
     * @return The double vector
     */
    public double[] partialV(double u, double v) {
        return null;
    }
}
