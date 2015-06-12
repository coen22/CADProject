package obj.curves;

import java.util.List;
import obj.Vertex;

public abstract class Curve {
	
	/**
	 * Method to get a 3d curve
	 * @param input control points
	 * @param the detail of the output curve
	 * @return a 3d curve
	 */
	public abstract List<Vertex> getCurve(List<Vertex> points, int interval);
}