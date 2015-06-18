package obj.algorithms;

import obj.Object3D;
import obj.ParametricSurface;

public class ParametricDoubleIntegrationSA implements SurfaceAreaMethod{

	private static final int RICHARDSON_N = 5;
	private static final int SIMPSON_N = 64;
	private static final int TRAPEZOID_N = 5;

	@Override
	public double getSurfaceArea(Object3D object3d) {
		ParametricSurface para = (ParametricSurface) object3d;
		long start = System.nanoTime();
		
//		double sa = outerTrapezoidU(para, TRAPEZOID_N);
		double sa = outerSimpsonU(para);
//		double sa = richardsonArea(para);
		
		long end = System.nanoTime();
		System.out.println("time: " + (end-start)/10E9);
		return sa;
	}
	
	private double evaluateSAFunction(ParametricSurface para, double u, double v){
		double[] ru = {para.getX().interU(u, v), para.getY().interU(u, v), para.getZ().interU(u, v)};
		double[] rv = {para.getX().interV(u, v), para.getY().interV(u, v), para.getZ().interV(u, v)};
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
			result += 
					evaluateSAFunction(para, u, lower + (h*i));
		}
		return result*h;
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
