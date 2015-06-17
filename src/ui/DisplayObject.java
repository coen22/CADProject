package ui;

import java.awt.Color;
import java.util.List;

import obj.Object3D;
import obj.Triangle;
import obj.Vertex;

public class DisplayObject {
	
	private Color defaultColor = new Color(200,200,200);
	private Color activeColor = new Color(200, 150, 150);
	
	private Object3D obj;
	private float xDisp;
	private float yDisp;
	private float zDisp;
	private Color color;
	private boolean visible;
	private String name;
	
	public DisplayObject(Object3D inputObject, String name) {
		this.obj = inputObject;
		this.setxDisp(0);
		this.setyDisp(0);
		this.setzDisp(0);
		this.setColor(defaultColor);
		this.visible = true;
		this.name = name;
	}
	
	public void activate(){
		this.color = activeColor;
	}
	
	public void deactivate(){
		this.color = defaultColor;
	}
	
	public void setVisible(boolean visibility){
		this.visible = visibility;
	}
	
	public boolean isVisible(){
		return visible;
	}

	public List<Vertex> getVerts() {
		return obj.getVerts();
	}

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
		this.defaultColor = color;
//		this.activeColor = color;
	}
	
	public String getName(){
		return this.name;
	}
	
	public double getVolume(){
		return obj.getVolume();
	}
	
	public double getSA(){
		return obj.getSurfaceArea();
	}

}
