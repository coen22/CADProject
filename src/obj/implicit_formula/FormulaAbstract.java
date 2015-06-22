package obj.implicit_formula;

/**
 * A abstract class representing the implicit formula
 *
 * @author Kareem Horstink
 * @version 1.0
 */
public abstract class FormulaAbstract {

    /**
     * Default constructor
     */
    public FormulaAbstract() {

    }

    /**
     * Evaluates the formula based on the parameter
     *
     * @param x The x points
     * @param y The y points
     * @param z The z points
     * @return Return the results
     */
    public double evaluateAt(double x, double y, double z) {
        return 0;
    }

}
