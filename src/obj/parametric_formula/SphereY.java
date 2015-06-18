package obj.parametric_formula;

public class SphereY extends FormulaAbstract {

    @Override
    public double evaluateAt(double u, double v) {
        return Math.sin(u);
    }

    @Override
    public double interU(double a, double b) {
        return super.interU(a, b); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double interV(double a, double b) {
        return super.interV(a, b); //To change body of generated methods, choose Tools | Templates.
    }
    

}
