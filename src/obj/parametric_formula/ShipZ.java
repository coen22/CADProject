package obj.parametric_formula;

public class ShipZ extends FormulaAbstract{

	@Override
	public double evaluateAt(double u, double v){
		return (v*Math.sin(u)*1.35)/2;
	}
}
