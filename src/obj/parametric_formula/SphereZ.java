package obj.parametric_formula;

/**
 *
 * @author Kareem
 */
public class SphereZ extends FormulaAbstract{

    @Override
    public double mumble(double u, double v) {
        return Math.cos(u)*Math.sin(v);
    }
    
}
