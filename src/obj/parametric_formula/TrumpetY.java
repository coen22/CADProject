package obj.parametric_formula;

/**
 *
 * @author Imray
 */
public class TrumpetY extends FormulaAbstract{

    @Override
    public double mumble(double u, double v) {
        return 6/(Math.pow((v+1),0.7))*Math.cos(u);
    }
    
}
