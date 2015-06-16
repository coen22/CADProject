package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

//import com.jogamp.opengl.swt.GLCanvas;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.SwingUtilities;

import obj.Object3D;
import obj.Triangle;
import obj.Vertex;

import com.jogamp.opengl.util.awt.TextRenderer;

import static javax.media.opengl.GL2.*;


/**
* This class visualizes the found solution using the JOGL plugin for Java. 
* 
* @author David Roschewitz
*@version 2.1
*@since 2015-01-16
*/
@SuppressWarnings("serial")
public class Visualization3D extends GLCanvas implements GLEventListener, MouseMotionListener, MouseWheelListener, KeyListener, java.awt.event.MouseListener {
	
	private static final float WORLDSIZE = 25;
	private static final int XAXIS = 0;
	private static final int YAXIS = 1;
	private static final int ZAXIS = 2;
	
	private TextRenderer renderer;
	private GLU glu;
	private float angleX = 20;
	private float angleY = 10;
	private float zoomFloat;
	private float translateX = 0.0f;
	private float translateY = -5.0f;
	private float translateZ = 0.0f;
	private float oldX = Integer.MIN_VALUE;
	private float oldY = Integer.MIN_VALUE;
	private float prevX = 0;
	private float prevY = 0;
	
	private DisplayObject tmpActiveObject;
	
	private int activeObject;
	private ArrayList<DisplayObject> objects;
	private boolean translateMode;
	private int activeShift;
	private boolean visibleNormals;
	
	/**
	 * Constructor, requires no parameters, adds all listeners to the GLCanvas. 
	 */
	public Visualization3D(){
		zoomFloat = -20;
		this.addGLEventListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.setFocusable(true);
		this.requestFocus();
		this.setDefaultCloseOperation(getDefaultCloseOperation());
		
		activeObject = 0;
		objects = new ArrayList<DisplayObject>();
		translateMode = false;
		activeShift = XAXIS;
		visibleNormals = false;
	}
	
	/**
	 * This method is called when displaying content on the canvas. Other individual methods are called from this method. 
	 */
	public void display(GLAutoDrawable drawable) { //drawing (paintComponent) 
		GL2 gl = drawable.getGL().getGL2(); //like graphics 2d
		
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		if (objects.size() > 0){
			for (int i = 0; i < objects.size(); i++){
				if (objects.get(i).getTris() != null){
					triFaces(gl, objects.get(i));
					triEdges(gl, objects.get(i));
					if (visibleNormals){
						normals(gl, objects.get(i));
					}
				}
				else{
					normalVerts(gl, objects.get(i));
				}
			}
		}
		
		text(gl);
		gridlines(gl);
	}

	public void setObject(DisplayObject obj){
		this.tmpActiveObject = obj;
	}
	
	public void setObjects(ArrayList<DisplayObject> objs){
		this.objects = objs;
	}
	
	
	/**
	 * The text displayed in the bottom left corner is drawn here. 
	 */
	private void text(GL2 gl){
		Font resultFont = new Font("SansSerif", Font.BOLD, 16);
		Font helpFont = new Font("SansSerif", Font.BOLD, 12);
		renderer = new TextRenderer(resultFont);
	    
	    renderer = new TextRenderer(helpFont);
	    renderer.beginRendering(this.getWidth(), this.getHeight());
	    renderer.setColor(0.0f, 0.0f, 0.0f, 0.9f);
	    renderer.draw("Drag the image to change perspective, scroll to zoom and hold shift while dragging to shift the view.", 5, 5);
	    if (objects.size() > 0){
	    	renderer.draw("vertices: " + objects.get(activeObject).getVerts().size(), 5, this.getHeight()-15);
	    	if (objects.get(activeObject).getTris() != null){
	    		renderer.draw("tris: " + objects.get(activeObject).getTris().size(), 5, this.getHeight()-30);
	    	}
	    	renderer.draw("volume: " + objects.get(activeObject).getVolume() + ", surface area: " + objects.get(activeObject).getSA(), 5, this.getHeight()-45);
	    }
	    renderer.endRendering();
	}
	
	/**
	 * This methods faces of the triangle-meshes 
	 */
	private void triFaces(GL2 gl, DisplayObject obj){
		ArrayList<Triangle> tris = (ArrayList<Triangle>) obj.getTris();
		float dispX = obj.getxDisp();
		float dispY = obj.getyDisp();
		float dispZ = obj.getzDisp();
		
		gl.glLoadIdentity();
		gl.glTranslated(0.0f, 2.0f, zoomFloat);
		gl.glTranslated(translateX, translateY, translateZ);
		gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
		
		gl.glBegin(GL_TRIANGLES);
		gl.glColor3f(obj.getColor().getRed()/255f, obj.getColor().getGreen()/255f, obj.getColor().getBlue()/255f); //has colour
		
		for (int i = 0; i < tris.size(); i++){
			gl.glVertex3d(tris.get(i).getA().getX() + dispX, tris.get(i).getA().getY() + dispY, tris.get(i).getA().getZ() + dispZ);
			gl.glVertex3d(tris.get(i).getB().getX() + dispX, tris.get(i).getB().getY() + dispY, tris.get(i).getB().getZ() + dispZ);
			gl.glVertex3d(tris.get(i).getC().getX() + dispX, tris.get(i).getC().getY() + dispY, tris.get(i).getC().getZ() + dispZ);
		}
		
		gl.glEnd();
		
	}
	
	/**
	 * draws the normals to the faces of a triangle-mesh
	 */
	private void normals(GL2 gl, DisplayObject obj) {
		
		ArrayList<Triangle> tris = (ArrayList<Triangle>) obj.getTris();
		float dispX = obj.getxDisp();
		float dispY = obj.getyDisp();
		float dispZ = obj.getzDisp();
		gl.glLoadIdentity();
		gl.glTranslated(0.0f, 2.0f, zoomFloat);
		gl.glTranslated(translateX, translateY, translateZ);
		gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
		
		gl.glBegin(GL_LINES);
		gl.glColor3f(0.2f, 0.2f, 0.2f);
		gl.glLineWidth(1.0f);
		
		
		for (int i = 0; i < tris.size(); i++){
			double[] u = new double[3];
			double[] v = new double[3];
			u[0] = tris.get(i).getB().getX()-tris.get(i).getA().getX();
			u[1] = tris.get(i).getB().getY()-tris.get(i).getA().getY();
			u[2] = tris.get(i).getB().getZ()-tris.get(i).getA().getZ();
			
			v[0] = tris.get(i).getC().getX()-tris.get(i).getA().getX();
			v[1] = tris.get(i).getC().getY()-tris.get(i).getA().getY();
			v[2] = tris.get(i).getC().getZ()-tris.get(i).getA().getZ();
			
			double crossX = u[1]*v[2] - u[2]*v[1];
			double crossY = u[2]*v[0] - u[0]*v[2];
			double crossZ = u[0]*v[1] - u[1]*v[0];
			
			double normalFactor = 20;
			
			gl.glVertex3d(tris.get(i).getA().getX() + (0.25*u[0] + 0.25*v[0]) + dispX, tris.get(i).getA().getY()+ (0.25*u[1] + 0.25*v[1]) + dispY, tris.get(i).getA().getZ()+ (0.25*u[2] + 0.25*v[2]) + dispZ);
			gl.glVertex3d(tris.get(i).getA().getX() + (0.25*u[0] + 0.25*v[0]) + crossX*normalFactor + dispX, tris.get(i).getA().getY()+ (0.25*u[1] + 0.25*v[1]) + crossY*normalFactor + dispY, tris.get(i).getA().getZ()+ (0.25*u[2] + 0.25*v[2]) + crossZ*normalFactor + dispZ);
		}
		
		gl.glEnd();
		
	}
	
	/**
	 * draws the edge lines of triangle-meshes
	 */
	private void triEdges(GL2 gl, DisplayObject obj){
		ArrayList<Triangle> tris = (ArrayList<Triangle>) obj.getTris();
		float dispX = obj.getxDisp();
		float dispY = obj.getyDisp();
		float dispZ = obj.getzDisp();
		
		gl.glLoadIdentity();
		gl.glTranslated(0.0f, 2.0f, zoomFloat);
		gl.glTranslated(translateX, translateY, translateZ);
		gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
		
		gl.glBegin(GL_LINES);
		gl.glColor3f(0.2f, 0.2f, 0.2f); //has colour
		gl.glLineWidth(1.0f);
		
		for (int i = 0; i < tris.size(); i++){
			gl.glVertex3d(tris.get(i).getA().getX() + dispX, tris.get(i).getA().getY() + dispY, tris.get(i).getA().getZ() + dispZ);
			gl.glVertex3d(tris.get(i).getB().getX() + dispX, tris.get(i).getB().getY() + dispY, tris.get(i).getB().getZ() + dispZ);
			
			gl.glVertex3d(tris.get(i).getB().getX() + dispX, tris.get(i).getB().getY() + dispY, tris.get(i).getB().getZ() + dispZ);
			gl.glVertex3d(tris.get(i).getC().getX() + dispX, tris.get(i).getC().getY() + dispY, tris.get(i).getC().getZ() + dispZ);
			
			gl.glVertex3d(tris.get(i).getC().getX() + dispX, tris.get(i).getC().getY() + dispY, tris.get(i).getC().getZ() + dispZ);
			gl.glVertex3d(tris.get(i).getA().getX() + dispX, tris.get(i).getA().getY() + dispY, tris.get(i).getA().getZ() + dispZ);
		}
		
		gl.glEnd();
		
	}
	
	/**
	 * draws the faces orthogonal to the normals in order to visualize implicit formulas
	 */
	private void normalVerts(GL2 gl, DisplayObject obj) {
		ArrayList<Vertex> verts = (ArrayList<Vertex>) obj.getVerts();
		float dispX = obj.getxDisp();
		float dispY = obj.getyDisp();
		float dispZ = obj.getzDisp();
		
		gl.glLoadIdentity();
		gl.glTranslated(0.0f, 2.0f, zoomFloat);
		gl.glTranslated(translateX, translateY, translateZ);
		gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
		
		for (int i = 0; i < verts.size(); i++){
			double[] n = verts.get(i).getImplicitNormal();
			double normalMag = Math.sqrt(n[0]*n[0] + n[1]*n[1] + n[2]*n[2]);
			
			double[] u1 = {-n[1]/n[0], 1, 0};
			double[] u2 = {-n[2]/n[0], 0, 1};
			double u1Mag = Math.sqrt(u1[0]*u1[0] + u1[1]*u1[1] + u1[2]*u1[2]);
			double u2Mag = Math.sqrt(u2[0]*u2[0] + u2[1]*u2[1] + u2[2]*u2[2]);
			
			u1[0] = u1[0]/u1Mag * normalMag;
			u1[1] = u1[1]/u1Mag * normalMag;
			u1[2] = u1[2]/u1Mag * normalMag;
			u2[0] = u2[0]/u2Mag * normalMag;
			u2[1] = u2[1]/u2Mag * normalMag;
			u2[2] = u2[2]/u2Mag * normalMag;
			
			gl.glBegin(GL_TRIANGLES);
			gl.glColor3f(obj.getColor().getRed()/255f, obj.getColor().getGreen()/255f, obj.getColor().getBlue()/255f); //has colour
			gl.glVertex3d(verts.get(i).getX() + dispX - u1[0], verts.get(i).getY() + dispY - u1[1], verts.get(i).getZ() + dispZ - u1[2]);
			gl.glVertex3d(verts.get(i).getX() + dispX + u1[0], verts.get(i).getY() + dispY + u1[1], verts.get(i).getZ() + dispZ + u1[2]);
			gl.glVertex3d(verts.get(i).getX() + dispX - u2[0], verts.get(i).getY() + dispY - u2[1], verts.get(i).getZ() + dispZ - u2[2]);
			
			gl.glVertex3d(verts.get(i).getX() + dispX - u1[0], verts.get(i).getY() + dispY - u1[1], verts.get(i).getZ() + dispZ - u1[2]);
			gl.glVertex3d(verts.get(i).getX() + dispX + u1[0], verts.get(i).getY() + dispY + u1[1], verts.get(i).getZ() + dispZ + u1[2]);
			gl.glVertex3d(verts.get(i).getX() + dispX + u2[0], verts.get(i).getY() + dispY + u2[1], verts.get(i).getZ() + dispZ + u2[2]);
			gl.glEnd();
			
			gl.glBegin(GL_LINES);
			gl.glColor3f(0.2f, 0.2f, 0.2f); //has colour
			gl.glLineWidth(1.0f);
			
			gl.glVertex3d(verts.get(i).getX() + dispX + u1[0], verts.get(i).getY() + dispY + u1[1], verts.get(i).getZ() + dispZ + u1[2]);
			gl.glVertex3d(verts.get(i).getX() + dispX - u2[0], verts.get(i).getY() + dispY - u2[1], verts.get(i).getZ() + dispZ - u2[2]);
			
			gl.glVertex3d(verts.get(i).getX() + dispX - u2[0], verts.get(i).getY() + dispY - u2[1], verts.get(i).getZ() + dispZ - u2[2]);
			gl.glVertex3d(verts.get(i).getX() + dispX - u1[0], verts.get(i).getY() + dispY - u1[1], verts.get(i).getZ() + dispZ - u1[2]);
			
			gl.glVertex3d(verts.get(i).getX() + dispX - u1[0], verts.get(i).getY() + dispY - u1[1], verts.get(i).getZ() + dispZ - u1[2]);
			gl.glVertex3d(verts.get(i).getX() + dispX + u2[0], verts.get(i).getY() + dispY + u2[1], verts.get(i).getZ() + dispZ + u2[2]);
			
			gl.glVertex3d(verts.get(i).getX() + dispX + u2[0], verts.get(i).getY() + dispY + u2[1], verts.get(i).getZ() + dispZ + u2[2]);
			gl.glVertex3d(verts.get(i).getX() + dispX + u1[0], verts.get(i).getY() + dispY + u1[1], verts.get(i).getZ() + dispZ + u1[2]);
			
			
			gl.glEnd();
		}
	}
	
	/**
	 * draws the basic grid-lines which allow
	 * @param gl
	 */
	private void gridlines(GL2 gl){
		
		gl.glLoadIdentity();
		gl.glTranslated(0.0f, 2.0f, zoomFloat);
		gl.glTranslated(translateX, translateY, translateZ);
		gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
		
		gl.glBegin(GL_LINES);
		gl.glColor3f(0.3f, 0.3f, 0.3f); //has colour
		gl.glLineWidth(1.0f);
		
		//small gridlines
		gl.glColor3f(0.6f, 0.6f, 0.6f); //has colour
		for (float i = -WORLDSIZE; i <= WORLDSIZE; i++){
			gl.glVertex3f(-WORLDSIZE, 0.0f,  i);
			gl.glVertex3f(WORLDSIZE, 0.0f,  i);
			
			gl.glVertex3f(i, 0.0f,  -WORLDSIZE);
			gl.glVertex3f(i, 0.0f,  WORLDSIZE);
		}
		
		//main axes:
		gl.glColor3f(1.0f, 0.3f, 0.3f);
		gl.glVertex3f(0.0f, 0.0f, -3*WORLDSIZE);
		gl.glVertex3f(0.0f, 0.0f, 3*WORLDSIZE);
		
		gl.glColor3f(0.3f, 0.3f, 1.0f);
		gl.glVertex3f(0.0f, -3*WORLDSIZE, 0.0f);
		gl.glVertex3f(0.0f, 3*WORLDSIZE, 0.0f);
		
		gl.glColor3f(0.0f, 0.5f, 0.0f);
		gl.glVertex3f(-3*WORLDSIZE, 0.0f,  0.0f);
		gl.glVertex3f(3*WORLDSIZE, 0.0f,  0.0f);
		
		gl.glEnd();
		
	}
	

	/**
	 * This method is automatically called when the canvas is initialized. Various openGL specific settings are set here. 
	 */
	public void init(GLAutoDrawable drawable) { //initialize (important) //called once when started
		GL2 gl = drawable.getGL().getGL2(); //like graphics 2d
		glu = new GLU(); //creates new GL universe
		
		gl.glClearColor(0.9f, 0.95f, 1.0f, 1.0f);//background colour
		
		gl.glClearDepth(1.0f);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glEnable(GL_POLYGON_SMOOTH);
//		gl.glEnable(GL_LINE_SMOOTH);
		gl.glDepthFunc(GL_LEQUAL);
//		gl.glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
//		gl.glLineWidth(1.5f);
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); //changes line-rendering
		gl.glShadeModel(GL_SMOOTH); 
				
	}

	/**
	 * This method determines how the canvas responds to the user re-sizing the entire window. 
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2(); //like graphics 2d
		
		if (height == 0){
			height = 1; //prevent div0
		}
		
		float aspect = (float)width/height;
		
		//setting the visual area to what was changed
		gl.glViewport(x, y, width, height);
		
		//setting perspective projection mode
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0, aspect, 0.1, 1000); //fovy, aspect, zNear, zFar
		
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();
		
	}

	/**
	 * This methods implements the MouseMotionListener and is called whenever the mouse is dragged. This is used to both shift and pivot the view in the viewport. 
	 * @see #mousePressed(MouseEvent e)
	 */
	public void mouseDragged(MouseEvent e) {
		int newX = e.getX();
		int newY = e.getY();
		
		if (e.isShiftDown()){
			translateY += (oldY - newY) / 100 * (-zoomFloat/10);
			translateX -= (oldX - newX) / 100 * (-zoomFloat/10);
		}
		else if (SwingUtilities.isLeftMouseButton(e)){
			if(oldX > Integer.MIN_VALUE){
				angleY -= (oldX -newX)/2;
				angleX -= (oldY -newY)/2;
				angleX = angleX % 360;
				angleY = angleY % 360;
				if (angleX < 0){
					angleX = 360 + angleX;
				}
				if (angleY < 0){
					angleY = 360 + angleY;
				}
			}
		}
		oldX = newX;
		oldY = newY;
		prevX = e.getX();
		prevY = e.getY();
	}

	
	/**
	 * This method responds to the mousewheel being used. The zoom level is changed subsequently. 
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoomFloat -= e.getWheelRotation()*-zoomFloat/20;
		if (zoomFloat > 0){
			zoomFloat = 0;
		}
		
	}

	/**
	 * This methods responds to keypresses E and G for the gridview and empty spaces. 
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_G){
			toggleTranslate();
		}
		else if (e.getKeyCode() == KeyEvent.VK_X){
			activeShift = XAXIS;
		}
		else if (e.getKeyCode() == KeyEvent.VK_Y){
			activeShift = YAXIS;
		}
		else if (e.getKeyCode() == KeyEvent.VK_Z){
			activeShift = ZAXIS;
		}
	}
	
	protected void toggleTranslate(){
		if (translateMode == false){
			translateMode = true;
		}
		else {
			translateMode = false;
		}
	}
	
	/**
	 * This method resets the coordinates of oldX and oldY whenever the mousebutton is pressed down. This prevents large jumps in the viewport. 
	 */
	public void mousePressed(MouseEvent e) {
		oldX = e.getX();
		oldY = e.getY();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
//		System.out.println("used part: " + Math.abs((angleX % 180)-90) + ", original: " + angleX);
		if (translateMode){
			if (activeShift == XAXIS){
				float difference = -(prevX - e.getX()) *  Math.abs((angleY % 180)-90)/90;
				if (angleY > 90 && angleY < 270){
					difference *= -1;
				}
				
//				float difference2 = (prevY - e.getY()) /  Math.abs((angleX % 180)-90);
//				if (angleX > 90 && angleX < 270){
//					difference2 *= -1;
//				}
				
				objects.get(activeObject).setxDisp(objects.get(activeObject).getxDisp() + difference * (-zoomFloat / 800));
			}
			else if (activeShift == YAXIS){
				float difference = (prevY - e.getY()) *  Math.abs((angleX % 180)-90)/90;
				if (angleX > 90 && angleX < 270){
					difference *= -1;
				}
				
				objects.get(activeObject).setyDisp(objects.get(activeObject).getyDisp() + difference * (-zoomFloat / 800));
			}
			else if (activeShift == ZAXIS){
				float difference = (prevX - e.getX()) *  Math.abs((angleY % 180)-180)/90;
				if (angleY > 0 && angleY < 180){
					difference *= -1;
				}
				
				objects.get(activeObject).setzDisp(objects.get(activeObject).getzDisp() + difference * (-zoomFloat / 800));
			}
			prevX = e.getX();
			prevY = e.getY();
		}
		else {
			prevX = e.getX();
			prevY = e.getY();
		}
	}
	
	@Override
	public void dispose(GLAutoDrawable arg0) { //close the frame / glCanvas
		// TODO Auto-generated method stub
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (translateMode == true){
			toggleTranslate();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void enableNormals() {
		this.visibleNormals = true;
	}

	public void disableNormals() {
		this.visibleNormals = false;
	}

	public void setActiveIndex(int active) {
		this.activeObject = active;
	}

}

