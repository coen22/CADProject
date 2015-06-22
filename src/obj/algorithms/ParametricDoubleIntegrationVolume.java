package obj.algorithms;

import obj.Object3D;
import obj.ParametricSurface;

public class ParametricDoubleIntegrationVolume implements VolumeMethod {

	@Override
	public double getVolume(Object3D object3d) {
		ParametricSurface para = (ParametricSurface) object3d;
//		long start = System.nanoTime();
		
		double vol = outerTrapezoidU(para, 1000);
		
//		long end = System.nanoTime();
//		System.out.println("time: " + (end-start)/10E9);
		return vol;
	}
	
	private double evaluateVolFunction(ParametricSurface para, double u, double v){
		double[] ru = para.getF().partialU(u, v);
		double[] rv = para.getF().partialV(u, v);
		double[] cross = StaticHelper.crossProduct(ru, rv);
		double mag = StaticHelper.mag(cross);
		
		double[] f_r = para.getF().evaluateAt(u, v);
//		System.out.println("num: " + (f_r[0] + f_r[1] + f_r[2]));
		double f = (f_r[1]);
//		double f = (f_r[0] + f_r[1] +  f_r[2]);
//		double f = Math.sqrt(f_r[0]*f_r[0] + f_r[1]*f_r[1] +  f_r[2]*f_r[2]);
		
		double result = f * mag;
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
		
		result += 0.5 * evaluateVolFunction(para, u, lower);
		result += 0.5 * evaluateVolFunction(para, u, higher);
		for (int i = 1; i <= (n-1); i++){
			result += evaluateVolFunction(para, u, lower + (h*i));
		}
		return result*h;
	}

}
