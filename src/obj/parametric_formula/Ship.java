package obj.parametric_formula;

public class Ship extends FormulaAbstract {

    @Override
    public double[] evaluateAt(double u, double v) {
        return new double[]{(v * 2 * Math.cos(u) * 3) / 2, ((Math.pow(v, 2) * 0.4) / 2) / 2, (v * Math.sin(u) * 1.35) / 2};
    }

}
