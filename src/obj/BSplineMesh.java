package obj;

import java.util.ArrayList;
import java.util.List;

public class BSplineMesh extends Object3D {

	public List<Vertex> points;
	public int interval;
	
	public BSplineMesh() {
		super();
		points = new ArrayList<Vertex>();
	}
	
	@Override
	public List<Vertex> getVerts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Triangle> getTris() {
		// TODO Auto-generated method stub
		return null;
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
}
