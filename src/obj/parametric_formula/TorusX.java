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
public class TorusX extends FormulaAbstract{

    @Override
    public double evaluateAt(double u, double v) {
        return (1+0.5*Math.cos(u))*Math.cos(v);
    }

    @Override
    public double interU(double a, double b) {
       return -0.5*Math.cos(b)*Math.sin(a);
    }//-0.5 Cos[v] Sin[u]

    @Override
    public double interV(double a, double b) {
        return -((1+0.5%Math.cos(a))*Math.sin(b));
    }//-((1 + 0.5 Cos[u]) Sin[v])
    
    
    
}
