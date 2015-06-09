package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import obj.Object3D;

import com.jogamp.opengl.util.FPSAnimator;

public class MainFrame {
	
	private JFrame mainFrame;
	private Visualization3D visualization3D;

	
	/**
	 * Constructor. Requires no parameters. The frame, panels as well as parcels and the container are initialised. 
	 */
	public MainFrame() {

		createFrame();
	}
	/**
	 * Creates the JFrame and the the two control panels as well as the JOGL GLCanvas. All are added to the frame. 
	 */
	private void createFrame() {
		mainFrame = new JFrame("Cargo Loading Optimization");
		mainFrame.setLayout(new BorderLayout(1, 1));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // what happens when the window is closed
		mainFrame.setLocationByPlatform(true);
		
		visualization3D = new Visualization3D();
		visualization3D.setSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.7), (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.8)));
		mainFrame.add(visualization3D, BorderLayout.CENTER);
		
		final FPSAnimator animator = new FPSAnimator(visualization3D, 30, true);
		
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
	
	public void setObject(Object3D obj){
		visualization3D.setObject(obj);
	}
	
}


