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
public class Torus extends FormulaAbstract {

    @Override
    public double[] evaluateAt(double u, double v) {
        return new double[]{(1 + 0.5 * Math.cos(u)) * Math.cos(v), 0.5 * Math.sin(u), (1 + 0.5 * Math.cos(u)) * Math.sin(v)};
    }

    @Override
    public double[] partialU(double u, double v) {
        return new double[]{-0.5 * Math.cos(v) * Math.sin(u), 0.5 * Math.cos(u), -0.5 * Math.sin(u) * Math.sin(v)};
    }

    @Override
    public double[] partialV(double u, double v) {
        return new double[]{-((1 + 0.5 * Math.cos(u)) * Math.sin(v)), 0, (1 + 0.5 * Math.cos(u)) * Math.cos(v)};
    }

}
