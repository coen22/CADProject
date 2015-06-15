package obj;

import java.awt.Color;
import java.util.List;

public class DisplayObject extends Object3D {
	
	private static final Color defaultColor = new Color(0.8f, 0.8f, 0.8f);
	private static final Color activeColor = new Color(0.5f, 0.3f, 0.3f);
	
	private Object3D obj;
	private float xDisp;
	private float yDisp;
	private float zDisp;
	private Color color;
	private boolean visible;
	
	public DisplayObject(Object3D inputObject) {
		super();
		this.obj = inputObject;
		this.setxDisp(0);
		this.setyDisp(0);
		this.setzDisp(0);
		this.setColor(defaultColor);
		this.visible = true;
	}
	
	public void activate(){
		this.setColor(activeColor);
	}
	
	public void deactivate(){
		this.setColor(defaultColor);
	}
	
	public void setVisible(boolean visibility){
		this.visible = visibility;
	}
	
	public boolean isVisible(){
		return visible;
	}

	@Override
	public List<Vertex> getVerts() {
		return obj.getVerts();
	}

	@Override
	public List<Triangle> getTris() {
		return obj.getTris();
	}

	public float getxDisp() {
		return xDisp;
	}

	public void setxDisp(float xDisp) {
		this.xDisp = xDisp;
	}

	public float getyDisp() {
		return yDisp;
	}

	public void setyDisp(float yDisp) {
		this.yDisp = yDisp;
	}

	public float getzDisp() {
		return zDisp;
	}

	public void setzDisp(float zDisp) {
		this.zDisp = zDisp;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
