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
public class TrumpetZ extends FormulaAbstract{

    @Override
    public double mumble(double u, double v) {
        return Math.sin(u)*Math.sin(v);
    }
    
}