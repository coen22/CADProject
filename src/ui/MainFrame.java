package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import obj.DisplayObject;

import com.jogamp.opengl.util.FPSAnimator;

public class MainFrame{
	
	private JFrame mainFrame;
	private Visualization3D visualization3D;
	private ControlPanel controlPanel;
	private Controller controller;

	
	/**
	 * Constructor. Requires no parameters. The frame, panels as well as parcels and the container are initialised. 
	 */
	public MainFrame(Controller ctrl) {
		this.controller = ctrl;
		createFrame();
	}
	/**
	 * Creates the JFrame and the the two control panels as well as the JOGL GLCanvas. All are added to the frame. 
	 */
	private void createFrame() {
		mainFrame = new JFrame("The Real World (not exactly...)");
		mainFrame.setLayout(new BorderLayout(1, 1));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // what happens when the window is closed
		mainFrame.setLocationByPlatform(true);
		
		visualization3D = new Visualization3D();
		visualization3D.setSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.7), (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.8)));
		mainFrame.add(visualization3D, BorderLayout.CENTER);
		
		controlPanel = new ControlPanel(controller, this);
		controlPanel.setSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.2), (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.1)));
		mainFrame.add(controlPanel, BorderLayout.SOUTH);
		
		final FPSAnimator animator = new FPSAnimator(visualization3D, 60, true);
		
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new Thread() {
					@Override
					public void run() {
						if (animator.isStarted())
							animator.stop();
						System.exit(0);
					}
				}.start();
			}
		});
		
		mainFrame.pack(); // packs individual components
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		animator.start();
	}	
	
	public void init(ArrayList<DisplayObject> objs){
		visualization3D.setObjects(objs);
	}
	
	public void itemsChanged(){
		controlPanel.itemsChanged();
	}
	
	public void enableNormals(){
		visualization3D.enableNormals();
	}
	
	public void disableNormals(){
		visualization3D.disableNormals();
	}
	public void activeSelectionChanged(int active) {
		visualization3D.setActiveIndex(active);
	}
	
	public void enableLines(){
		visualization3D.enableLines();
	}
	
	public void disableLines(){
		visualization3D.disableLines();
	}
	public void toggleGraphicsMode() {
		visualization3D.toggleGraphicsMode();
	}
	
}


