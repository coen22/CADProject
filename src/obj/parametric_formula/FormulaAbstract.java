package obj.parametric_formula;

/**
 *
 * @author Kareem Horstink
 */
public abstract class FormulaAbstract {

    public FormulaAbstract() {

    }
    
    public double evaluateAt(double u, double v) {
        return 0;
    }
    
    public double interU(double a, double b){
        return 0;
    }
    
    public double interV(double a, double b){
        return 0;
    }

}
