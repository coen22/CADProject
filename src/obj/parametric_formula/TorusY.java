/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj.parametric_formula;

/**
 *
 * @author Imray
 */
public class TorusY extends FormulaAbstract {

    @Override
    public double evaluateAt(double u, double v) {
        return 0.5 * Math.sin(u);
    }

    @Override
    public double interU(double a, double b) {
        return 0.5 * Math.cos(a);
    }

    @Override
    public double interV(double a, double b) {
        return 0;
    }

    
}
