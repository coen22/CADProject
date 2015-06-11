/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj.implicit_formula;

/**
 *
 * @author Imray
 */
public class Torus extends FormulaAbstract {

    @Override
    public double evaluateAt(double x, double y, double z) {
        double eps = 0.0001;
        double tmp = Math.pow((Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2) + 1 - 0.25), 2) - 4 * 1 * (Math.pow(x, 2) + Math.pow(y, 2));
        if (tmp < eps && tmp > -eps) {
            tmp = 0;
        }
        return tmp;
    }

}
