package obj.curves;

import java.util.List;

import obj.Object3D;
import obj.SpinningMesh;
import obj.Vertex;

import ui.algorithms.SurfaceAreaMethod;

public class CurveSurfaceAreaMethod implements SurfaceAreaMethod {

	public static int resolution = 100;
	
	@Override
	public double getSurfaceArea(Object3D obj) {
		SpinningMesh sMesh = (SpinningMesh) obj;
		return integral(sMesh.getCurve(resolution)) * Math.PI;
	}

	private double integral(List<Vertex> verts) {
		double area = 0;
		
		for (int i = 0; i < verts.size() - 1; i++) {
			double h = Math.abs(verts.get(i).getY() - verts.get(i + 1).getY());
			double r1 = pythagoras(verts.get(i));
			double r2 = pythagoras(verts.get(i + 1));
			area += Math.PI * h * (r1 + r2);
		}
		
		return area;
	}

	private double pythagoras(Vertex v) {
		return Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getZ(), 2));
	}
}
