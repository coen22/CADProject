package obj.parametric_formula;

/**
 *
 * @author Kareem Horstink
 */
public class SphereX extends FormulaAbstract{

    @Override
    public double evaluateAt(double u, double v) {
        return Math.cos(u)*Math.cos(v);
    }
    
}
