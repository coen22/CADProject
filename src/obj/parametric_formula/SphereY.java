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
public class SphereY extends FormulaAbstract{

    @Override
    public double evaluateAt(double u, double v) {
        return Math.sin(u);
    }
    
}
