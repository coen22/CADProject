package obj.algorithms;

import obj.Object3D;
import obj.ParametricSurface;

public class ParametricTrapezoidSA implements SurfaceAreaMethod{

	private static final int TRAPEZOID_N = 128;

	@Override
	public double getSurfaceArea(Object3D object3d) {
		ParametricSurface para = (ParametricSurface) object3d;
		double sa = outerTrapezoidU(para, TRAPEZOID_N);
		return sa;
	}
	
	private double evaluateSAFunction(ParametricSurface para, double u, double v){
		double[] ru = para.getF().partialU(u, v);
		double[] rv = para.getF().partialV(u, v);
		double[] cross = StaticHelper.crossProduct(ru, rv);
		double result = StaticHelper.mag(cross);
		return result;
	}
	
	private double outerTrapezoidU(ParametricSurface para, int n){
		double higher = para.getLimitMaxU();
		double lower = para.getLimitMinU();
		double h = (higher-lower) / n;
		
		double result = 0;
		
		result += 0.5 * innerTrapezoidV(para, lower, n);
		result += 0.5 * innerTrapezoidV(para, higher, n);
		for (int i = 1; i <= (n-1); i++){
			result += innerTrapezoidV(para, lower + (h*i), n);
		}
		
		return result*h;
	}
	
	private double innerTrapezoidV(ParametricSurface para, double u, int n){
		double higher = para.getLimitMaxV();
		double lower = para.getLimitMinV();
		double h = (higher-lower) / n;
		
		double result = 0;
		
		result += 0.5 * evaluateSAFunction(para, u, lower);
		result += 0.5 * evaluateSAFunction(para, u, higher);
		for (int i = 1; i <= (n-1); i++){
			result += evaluateSAFunction(para, u, lower + (h*i));
		}
		return result*h;
	}
	
}
