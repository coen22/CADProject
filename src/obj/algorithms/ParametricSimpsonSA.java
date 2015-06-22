package obj.algorithms;

import obj.Object3D;
import obj.ParametricSurface;

public class ParametricSimpsonSA implements SurfaceAreaMethod{

	private int SIMPSON_N = 15;

	@Override
	public double getSurfaceArea(Object3D object3d) {
		if (SIMPSON_N % 2 != 0){
			SIMPSON_N += 1;
		}
		
		ParametricSurface para = (ParametricSurface) object3d;
		double sa = outerSimpsonU(para);
		return sa;
	}
	
	private double evaluateSAFunction(ParametricSurface para, double u, double v){
		double[] ru = para.getF().partialU(u, v);
		double[] rv = para.getF().partialV(u, v);
		double[] cross = StaticHelper.crossProduct(ru, rv);
		double result = StaticHelper.mag(cross);
		return result;
	}

	private double outerSimpsonU(ParametricSurface para){
		double higher = para.getLimitMaxU();
		double lower = para.getLimitMinU();
		double h = (higher-lower) / SIMPSON_N;
		
		double result = 0;
		
		result += innerSimpsonV(para, lower);
		result += innerSimpsonV(para, higher);
		for (int i = 1; i < SIMPSON_N; i+=2){
			result += 4*innerSimpsonV(para, lower + (h*i));
		}
		for (int i = 2; i < SIMPSON_N; i+=2){
			result += 2*innerSimpsonV(para, lower + (h*i));
		}
		
		return (result * (h/3));
	}
	
	private double innerSimpsonV(ParametricSurface para, double u){
		double higher = para.getLimitMaxV();
		double lower = para.getLimitMinV();
		double h = (higher-lower) / SIMPSON_N;
		
		double result = 0;
		
		result += evaluateSAFunction(para, u, lower);
		result += evaluateSAFunction(para, u, higher);
		for (int i = 1; i < SIMPSON_N; i+=2){
			result += 4*evaluateSAFunction(para, u, lower + (h*i));
		}
		for (int i = 2; i < SIMPSON_N; i+=2){
			result += 2*evaluateSAFunction(para, u, lower + (h*i));
		}
		
		return (result * (h/3));
	}

}
