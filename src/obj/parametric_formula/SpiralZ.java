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
public class SpiralZ extends FormulaAbstract {

    @Override
    public double evaluateAt(double u, double v) {
        return v;
    }

    @Override
    public double interV(double a, double b) {
        return 1;
    }

    @Override
    public double interU(double a, double b) {
        return 0;
    }

}
