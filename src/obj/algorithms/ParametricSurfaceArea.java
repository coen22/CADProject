package obj.algorithms;

import obj.Object3D;
import obj.ParametricSurface;
import obj.parametric_formula.FormulaAbstract;

public class ParametricSurfaceArea implements SurfaceAreaMethod {

    @Override
    public double getSurfaceArea(Object3D object3d) {
        ParametricSurface para = (ParametricSurface) object3d;
        double startU = para.getLimitMinU();
        double startV = para.getLimitMinV();
        double endU = para.getLimitMaxU();
        double endV = para.getLimitMaxV();
        double deltaU = para.getIntervalU();
        double deltaV = para.getIntervalV();
        double area = 0;
        FormulaAbstract f = para.getF();
        for (double v = startV; v < endV; v += deltaV) {
            for (double u = startU; u < endU; u += deltaU) {
                double[] applied = f.evaluateAt(u, v);
                double[] deltaUDouble = f.evaluateAt(u + deltaU, v);
                double[] deltaVDouble = f.evaluateAt(u, v + deltaV);
                area += StaticHelper.mag(StaticHelper.crossProduct(StaticHelper.substract(applied, deltaUDouble), StaticHelper.substract(applied, deltaVDouble)));
            }
        }
        return Math.abs(area);
    }

}
