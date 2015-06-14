package obj.curves;

import java.util.ArrayList;
import java.util.List;

import obj.Vertex;

public class Bezier extends Curve {
	
	@Override
	public List<Vertex> getCurve(List<Vertex> points, int interval) {
		List<Vertex> plottingPoints = new ArrayList<Vertex>();
		
		float tInterval = 1 / ((float) ((points.size() - 1) * interval));
		
		for (float d = 0; d < 1; d+=tInterval){
			plottingPoints.add(deCasteljauSAlgorithm(points, d));
		}
		
		plottingPoints.add(deCasteljauSAlgorithm(points, 1));
		
		return plottingPoints;
	}
	
	/**
	 * Method which, without polynomial mathematics, calculates the point at a given t. Can be optimised to be seperate class (static), memory improvements possible. 
	 * @param ratio t at which the point shall be calculated
	 * @return the vertex for given t
	 */
	public Vertex deCasteljauSAlgorithm(List<Vertex> points, float ratio) {
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
