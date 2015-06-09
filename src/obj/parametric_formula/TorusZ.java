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
public class TorusZ extends FormulaAbstract{

    @Override
    public double mumble(double u, double v) {
        return (1+0.5*Math.cos(u))*Math.sin(v);
    }
    
}
