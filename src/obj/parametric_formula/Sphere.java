package obj.parametric_formula;

public class Sphere extends FormulaAbstract {

    @Override
    public double[] evaluateAt(double u, double v) {
        return new double[]{Math.cos(u) * Math.cos(v), Math.sin(u), Math.cos(u) * Math.sin(v)};
    }

    @Override
    public double[] partialU(double u, double v) {
        return new double[]{Math.cos(v) * Math.sin(u) * -1, Math.cos(u), Math.sin(u) * Math.sin(v) * -1};
    }

    @Override
    public double[] partialV(double u, double v) {
        return new double[]{Math.cos(u) * Math.sin(v) * -1, 0, Math.cos(u) * Math.cos(v)};
    }

}
