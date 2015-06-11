/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj.implicit_formula;

/**
 *
 * @author Kareem
 */
public class SumOfSins extends FormulaAbstract {

    @Override
    public double evaluateAt(double x, double y, double z) {      
        double eps = 0.01;
        double tmp = sin(x) + sin(y) + sin(z);
        if (tmp < eps && tmp > -eps) {
            tmp = 0;
        }
        return tmp;
    }

    private double sin(double a) {
        return Math.sin(a);
    }

    private double cos(double a) {
        return Math.sin(a);
    }

}
