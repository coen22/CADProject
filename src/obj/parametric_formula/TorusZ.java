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
public class TorusZ extends FormulaAbstract {

    @Override
    public double evaluateAt(double u, double v) {
        return (1 + 0.5 * Math.cos(u)) * Math.sin(v);
    }

    @Override
    public double interU(double a, double b) {//-0.5 Sin[u] Sin[v]
        return -0.5*Math.sin(a)*Math.sin(b);
    }

    @Override
    public double interV(double a, double b) {
        return (1 + 0.5*Math.cos(a)) *Math.cos(b);
    }

}
