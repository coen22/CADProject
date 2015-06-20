/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj.parametric_formula;

/**
 *
 * @author Kareem
 */
public class Spiral extends FormulaAbstract {

    @Override
    public double[] partialV(double u, double v) {
        return new double[]{-2 * Math.PI * u * Math.sin(2 * Math.PI * v), 2 * Math.PI * u * Math.cos(2 * Math.PI * v), 1};
    }

    @Override
    public double[] partialU(double u, double v) {
        return new double[]{Math.cos(2 * Math.PI * v), Math.sin(2 * Math.PI * v), 0};
    }

    @Override
    public double[] evaluateAt(double u, double v) {
        return new double[]{u * Math.cos(2 * Math.PI * v), u * Math.sin(2 * Math.PI * v), v};
    }

}
