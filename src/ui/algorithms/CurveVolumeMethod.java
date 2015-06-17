package ui.algorithms;

import java.util.List;

import obj.Object3D;
import obj.SpinningMesh;
import obj.Vertex;

public class CurveVolumeMethod implements VolumeMethod {

	public static int resolution = 100;
	
	@Override
	public double getVolume(Object3D obj) {
		SpinningMesh sMesh = (SpinningMesh) obj;
		return integral(sMesh.getCurve(resolution)) * Math.PI;
	}

	private double integral(List<Vertex> verts) {
		double size = 0;

		for (int i = 0; i < verts.size() - 2; i += 2) {
			double dSize = pythagoras(verts.get(i).getX(), verts.get(i).getZ());
			dSize += 4 * pythagoras(verts.get(i + 1).getX(), verts.get(i + 1).getZ());
			dSize += pythagoras(verts.get(i + 2).getX(), verts.get(i + 2).getZ());

			size += dSize * (verts.get(i + 2).getY() - verts.get(i).getY()) / 6;
		}
		
		if (verts.size() % 2 == 1) {
			double dSize = (verts.get(verts.size() - 1).getY() - verts.get(verts.size() - 2).getY());
			double fa = pythagoras(verts.get(verts.size() - 2).getX(), verts.get(verts.size() - 2).getZ());
			double fb = pythagoras(verts.get(verts.size() - 1).getX(), verts.get(verts.size() - 1).getZ());
			size += dSize * (fb - fa) / 2;
		}
		
		return Math.abs(size);
	}

	private double pythagoras(double x, double z) {
		return x*x + z*z;
	}
}
