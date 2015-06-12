package obj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BSplineMesh extends Object3D {

	public List<Vertex> points;
	public int interval;
	public int xzInterval;
	
	private ArrayList<ArrayList<Vertex>> calcVerts = new ArrayList<ArrayList<Vertex>>();
	private ArrayList<Triangle> calcTris = new ArrayList<Triangle>();
	
	public BSplineMesh() {
		super();
		points = new ArrayList<Vertex>();
	}
	
	@Override
	public List<Vertex> getVerts() {
		if (!calcVerts.isEmpty())
			return getVertsArray();
		
		List<Vertex> plot = getPlot();

		if (plot.size() == 0)
			return null;

		for (int j = 0; j < plot.size(); j++) {
			ArrayList<Vertex> curve = new ArrayList<Vertex>();

			for(int i = 0; i < xzInterval; i++) {
				double a = 0;
				Vertex v = new Vertex();
				v.setX(plot.get(j).getX() * Math.cos(a) - plot.get(j).getZ() * Math.sin(a));
				v.setZ(plot.get(j).getX() * Math.sin(a) + plot.get(j).getZ() * Math.cos(a));
				v.setY(plot.get(j).getY());
				curve.add(v);
			}

			calcVerts.add(curve);
		}
		
		return getVertsArray();
	}

	public List<Vertex> getVertsArray() {
		List<Vertex> verts = new ArrayList<Vertex>();
		
		for (List<Vertex> vs : calcVerts)
			verts.addAll(vs);
		
		return verts;
	}
	
	@Override
	public List<Triangle> getTris() {
		if (!calcTris.isEmpty())
			return calcTris;
		
		if (calcVerts.isEmpty())
			getVerts();
		
		for (int j = 0; j < calcVerts.get(0).size(); j++) {
			for (int i = 0; i < calcVerts.size(); i++) {
				if (i != calcVerts.size() - 1) {
					Vertex a = calcVerts.get(i).get(j);
					Vertex b = calcVerts.get(i + 1).get(j);
					Vertex c = calcVerts.get(i + 1).get((j + 1) % calcVerts.get(0).size());
					Vertex d = calcVerts.get(i).get((j + 1) % calcVerts.get(0).size());

					calcTris.add(new Triangle(c, b, a));
					calcTris.add(new Triangle(c, a, d));
				}
			}
		}

		makeCab(-1);
		makeCab(1);
		
		return calcTris;
	}
	
	public void makeCab(int order) {
		int layer;
		int cur;

		if (points.get(0).getY() < points.get(points.size() - 1).getY())
			order *= -1;
			
		if (order == -1) {
			layer = 0;
			cur = calcVerts.get(0).size() - 1;
		} else {
			layer = calcVerts.size() - 1;
			cur = 0;
		}

		Vertex va = calcVerts.get(layer).get(cur);
		cur += order;
		Vertex vb = calcVerts.get(layer).get(cur);
		cur += order;
		Vertex vc = calcVerts.get(layer).get(cur);
		cur += order;

		calcTris.add(new Triangle(va, vb, vc));

		while (cur < calcVerts.get(0).size() - 1 && cur > 0) {
			vb = vc;
			vc = calcVerts.get(layer).get(cur);
			cur += order;

			calcTris.add(new Triangle(va, vb, vc));
		}
	}
	
	public List<Vertex> getPlot() {
		List<Vertex> plottingPoints = new ArrayList<Vertex>();
		
		float tInterval = 1 / ((float) ((points.size() - 1) * interval));
		
		for (float d = 0; d < 1; d+=tInterval){
			plottingPoints.add(deCasteljauSAlgorithm(d));
		}
		
		plottingPoints.add(deCasteljauSAlgorithm(1));
		
		return plottingPoints;
	}
	
	/**
	 * Method which, without polynomial mathematics, calculates the point at a given t. Can be optimised to be seperate class (static), memory improvements possible. 
	 * @param originalPoints original points of the Bezier Curve
	 * @param ratio t at which the point shall be calculated
	 * @return the point for given t
	 */
	public Vertex deCasteljauSAlgorithm(float ratio) {
		List<Vertex> working = new ArrayList<Vertex>();
		
		if (points.size() > 1){
			//calculates the first step and copies from the originalPoints to the working points set
			for (int i = 0; i < points.size() - 1; i++) {
				double px = points.get(i).getX() + ((points.get(i + 1).getX() - points.get(i).getX()) * ratio);
				double py = points.get(i).getY() + ((points.get(i + 1).getY() - points.get(i).getY()) * ratio);
				double pz = points.get(i).getZ() + ((points.get(i + 1).getZ() - points.get(i).getZ()) * ratio);
				working.add(new Vertex(px, py, pz));
			}

			while (working.size() > 1) {
				for (int i = 0; i < working.size() - 1; i++){
					double px = working.get(i).getX() + ((working.get(i + 1).getX() - working.get(i).getX()) * ratio);
					double py = working.get(i).getY() + ((working.get(i + 1).getY() - working.get(i).getY()) * ratio);
					double pz = working.get(i).getZ() + ((working.get(i + 1).getZ() - working.get(i).getZ()) * ratio);
					working.set(i, new Vertex(px, py, pz));
				}

				working.remove(working.size() - 1);
			}

			return working.get(0);
		} else {
			return points.get(0);
		}
	}
	
	public void addPoint(Vertex v) {
		calcTris.clear();
		calcVerts.clear();
		points.add(v);
	}
	
	public void removePoint(Vertex v) {
		calcTris.clear();
		calcVerts.clear();
		points.remove(v);
	}
}
