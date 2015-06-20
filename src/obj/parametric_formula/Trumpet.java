package obj.parametric_formula;

public class Trumpet extends FormulaAbstract {

    @Override
    public double[] evaluateAt(double u, double v) {
        return new double[]{v, 6 / (Math.pow((v + 1), 0.7)) * Math.cos(u), 6 / (Math.pow((v + 1), 0.7)) * Math.sin(u)};
    }

}
