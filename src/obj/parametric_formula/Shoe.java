package obj.parametric_formula;

public class Shoe extends FormulaAbstract {

    @Override
    public double[] evaluateAt(double u, double v) {
        return new double[]{u, 0.3333333 * Math.pow(u, 3) - 0.5 * Math.pow(v, 2), v};
    }

}
