package obj;

import java.util.ArrayList;
import java.util.List;

import obj.curves.*;
import ui.algorithms.CurveVolumeMethod;

public class SpinningMesh extends Object3D {

	/**
	 * The control points of the curve
	 */
	private List<Vertex> points;
	
	/**
	 * The detail of the curve in the xy direction
	 */
	private int interval;
	
	/**
	 * The detail of the curve in the xz direction
	 */
	private int xzInterval;
	
	/**
	 * The stored vertices of the mesh
	 */
	private ArrayList<ArrayList<Vertex>> calcVerts = new ArrayList<ArrayList<Vertex>>();
	
	/**
	 * The stored triangles of the mesh
	 */
	private ArrayList<Triangle> calcTris = new ArrayList<Triangle>();
	
	/**
	 * The type of curve used for calculating the mesh
	 */
	private Curve curveType;
	
	// Curves
	
	/**
	 * All types of curves
	 */
	private static Curve bezierCurve = new Bezier();
	
	/**
	 * A mesh made by spinning a curve around the y-axis
	 */
	public SpinningMesh() {
		super();
		interval = 15;
		xzInterval = 100;
		curveType = bezierCurve;
		volumeMethod = new CurveVolumeMethod();
		points = new ArrayList<Vertex>();
	}
	
	@Override
	public List<Vertex> getVerts() {
		if (!calcVerts.isEmpty())
			return getVertsArray();
		
		List<Vertex> plot = getCurve();

		if (plot.size() == 0)
			return null;

		for (int j = 0; j < plot.size(); j++) {
			ArrayList<Vertex> curve = new ArrayList<Vertex>();

			for(int i = 0; i < xzInterval; i++) {
				double a = ((double) i / (double) xzInterval) * Math.PI * 2;
				Vertex v = new Vertex();
				v.setX(plot.get(j).getX() * Math.cos(a));
				v.setZ(plot.get(j).getX() * Math.sin(a));
				v.setY(plot.get(j).getY());
				curve.add(v);
			}
			
			calcVerts.add(curve);
		}
		
		return getVertsArray();
	}

	/**
	 * Method to convert the vertices into an arraylist
	 * @return
	 */
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
	
	/**
	 * Method to make the cab the bottom and top
	 * @param order for the triangles to have the right normals
	 */
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
		
		while (cur < calcVerts.get(0).size() && cur >= 0) {
			vb = vc;
			vc = calcVerts.get(layer).get(cur);
			cur += order;

			calcTris.add(new Triangle(va, vb, vc));
		}
	}
	
	/**
	 * Method to get 3d curve
	 * @return the points of the curve
	 */
	public List<Vertex> getCurve() {
		return curveType.getCurve(points, interval);
	}
	
	/**
	 * Method to get 3d curve with a higher or lower interval
	 * @return the points of the curve
	 */
	public List<Vertex> getCurve(int interval) {
		return curveType.getCurve(points, interval);
	}
	
	public Curve getCurveType() {
		return curveType;
	}
	
	/**
	 * Method to add a control point
	 * @param an input vertex
	 */
	public void addPoint(Vertex v) {
		calcTris.clear();
		calcVerts.clear();
		points.add(v);
	}
	
	/**
	 * Method to remove a control point
	 * @param the index of the vertex to be removed
	 */
	public void removePoint(int idx) {
		calcTris.clear();
		calcVerts.clear();
		points.remove(idx);
	}
	
	public int getXZInterval() {
		return xzInterval;
	}
	
	public void setXZInterval(int val) {
		calcTris.clear();
		calcVerts.clear();
		xzInterval = val;
	}
	
	public int getXYInterval() {
		return interval;
	}
	
	public void setXYInterval(int val) {
		calcTris.clear();
		calcVerts.clear();
		interval = val;
	}
}
