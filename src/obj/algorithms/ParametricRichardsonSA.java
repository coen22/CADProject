package obj.algorithms;

import obj.Object3D;
import obj.ParametricSurface;

public class ParametricRichardsonSA implements SurfaceAreaMethod{

	private static final int RICHARDSON_N = 8;

	@Override
	public double getSurfaceArea(Object3D object3d) {
		ParametricSurface para = (ParametricSurface) object3d;
		double sa = richardsonArea(para);
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
	
	private double richardsonArea(ParametricSurface para) {
		double[][] richardsonMatrix = new double[RICHARDSON_N][RICHARDSON_N];
		
		for (int i = 0; i < RICHARDSON_N; i++){
			richardsonMatrix[i][0] = outerTrapezoidU(para, (int)Math.pow(2, i));
		}
		
		for (int i = 1; i < RICHARDSON_N; i++){
			for (int k = i; k < RICHARDSON_N; k++){
				richardsonMatrix[k][i] = richardsonMatrix[k][i-1] + ((richardsonMatrix[k][i-1]-richardsonMatrix[k-1][i-1])/(Math.pow(2, i)-1));
			}
		}
		
		return richardsonMatrix[RICHARDSON_N-1][RICHARDSON_N-1];
	}
}
