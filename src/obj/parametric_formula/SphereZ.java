package obj.parametric_formula;

/**
 *
 * @author Kareem
 */
public class SphereZ extends FormulaAbstract{

    @Override
    public double evaluateAt(double u, double v) {
        return Math.cos(u)*Math.sin(v);
    }
    
}
