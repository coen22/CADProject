package obj.parametric_formula;

public class ShipX extends FormulaAbstract{

	@Override
	public double evaluateAt(double u, double v){
		return v*2*Math.cos(u)*3;
	}
}
