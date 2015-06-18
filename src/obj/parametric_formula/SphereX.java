package obj.parametric_formula;

/**
 *
 * @author Kareem Horstink
 */
public class SphereX extends FormulaAbstract {

    @Override
    public double evaluateAt(double u, double v) {
            return Math.cos(u) * Math.cos(v);
    }//-(Cos[u] Sin[v])

    @Override
    public double interU(double a, double b) {
        return Math.cos(b)*Math.sin(a)*-1;
    }

    @Override
    public double interV(double a, double b) {
        return Math.cos(a)*Math.sin(b)*-1;
    }
}
