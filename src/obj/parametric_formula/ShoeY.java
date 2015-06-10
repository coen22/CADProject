 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obj.parametric_formula;

import obj.parametric_formula.FormulaAbstract;

/**
 *
 * @author Kareem
 */
public class ShoeY extends FormulaAbstract {

    @Override
    public double evaluateAt(double u, double v) {
        return 0.3333333*Math.pow(u, 3)-0.5*Math.pow(v, 2);
    }

}
