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
public class Genus2 extends FormulaAbstract {

    @Override
    public double evaluateAt(double x, double y, double z) {
        double eps = 0.001;
        double tmp = 2*y*(Math.pow(y,2)-3*Math.pow(x,2))*(1-Math.pow(z, 2))+Math.pow((Math.pow(x, 2)+Math.pow(y, 2)), 2)-(9*Math.pow(z, 2)-1)*(1-Math.pow(z, 2));
        if(tmp<eps && tmp>-eps){
            tmp = 0;
        }
        return tmp;
    }

}
