package obj.parametric_formula;

public class ShipY extends FormulaAbstract{

	@Override
	public double evaluateAt(double u, double v){
		return (Math.pow(v, 2)*0.4)/2;
	}
}
