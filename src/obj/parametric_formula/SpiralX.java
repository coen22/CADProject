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
public class SpiralX extends FormulaAbstract {

    @Override
    public double evaluateAt(double u, double v) {
        return u * Math.cos(2 * Math.PI * v);
    }

    @Override
    public double interV(double a, double b) {
        return -2 * Math.PI * a * Math.sin(2 * Math.PI * b);
    }

    @Override
    public double interU(double a, double b) {
        return Math.cos(2 * Math.PI * b);
    }
}
