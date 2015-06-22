package ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel {
	
	Controller controller;
	MainFrame mainframe;
	JComboBox<String> activeObjectSelector;
	JComboBox<String> volMethSel;
	JComboBox<String> saMethSel;

	public ControlPanel(Controller ctrl, MainFrame mainframe){
		this.controller = ctrl;
		this.mainframe = mainframe;
		this.setLayout(new FlowLayout());
		
		JFileChooser importMesh = new JFileChooser(System.getProperty("user.dir")+File.separator+"src");
		JButton importOBJ = new JButton("import mesh");
		importOBJ.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int fileReturn = importMesh.showOpenDialog(null);
				if (fileReturn == JFileChooser.APPROVE_OPTION){
					controller.importObject(importMesh.getSelectedFile());
				}
			}
		});
		
		String[] defaultString = {"No objects exist"};
		activeObjectSelector = new JComboBox<String>(defaultString);
		activeObjectSelector.setLightWeightPopupEnabled(false);
		activeObjectSelector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (activeObjectSelector.getSelectedIndex() >= 0){
					activeSelectionChanged();
				}
			}
		});
		
		String[] methodsString = {"No methods exist"};
		saMethSel = new JComboBox<String>(methodsString);
		saMethSel.setLightWeightPopupEnabled(false);
		saMethSel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (saMethSel.getSelectedIndex() >= 0){
					controller.setSAMethod((String)saMethSel.getSelectedItem());
				}
			}
		});
		
		volMethSel = new JComboBox<String>(methodsString);
		volMethSel.setLightWeightPopupEnabled(false);
		volMethSel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (volMethSel.getSelectedIndex() >= 0){
					controller.setVolMethod((String)volMethSel.getSelectedItem());
				}
			}
		});
		
		JButton delete = new JButton("delete current object");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.deleteCurrent();
			}
		});
		
		String[] options = {"parametric torus", "parametric shoe", "parametric ship", "parametric sphere", "parametric spiral", "parametric trumpet", "implicit diamond", "implicit genus2", "implicity neovius", "implicit sphere", "implicity sum of sins", "implicit swiss cube" , "implicit torus", "implicity torus-cube", "implicity torus intersecting sphere", "curve ball", "curve vase", "curve martini glass"};
		JComboBox<String> createFunctionalObject = new JComboBox<String>(options);
		activeObjectSelector.setLightWeightPopupEnabled(false);
		
		JButton create = new JButton("create selected object:");
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createObject(createFunctionalObject.getSelectedIndex());;
			}
		});
		
		JButton color = new JButton("set colour");
		color.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(null, "Select the Color", new Color(0, 0, 0));
				
				if (color != null)
					controller.setColor(color);
			}
		});
		
		BasicArrowButton increaseSubs = new BasicArrowButton(BasicArrowButton.NORTH);
		increaseSubs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("increase");
				controller.increaseSmoothing();
			}
		});
		
		BasicArrowButton decreaseSubs = new BasicArrowButton(BasicArrowButton.SOUTH);
		decreaseSubs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("decrease");
			}
		});
		
		this.add(activeObjectSelector);
		this.add(delete);
		this.add(color);
		this.add(importOBJ);
		this.add(create);
		this.add(createFunctionalObject);
		this.add(saMethSel);
		this.add(volMethSel);
		this.add(decreaseSubs);
		this.add(increaseSubs);
		
	}
	
	public void paintComponent(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		Color bg = new Color(100, 100, 100, 255);

		// fills background of entire window
		graphics2D.setColor(bg);
		graphics2D.fill(this.getVisibleRect());
	}
	
	public void itemsChanged(){
		
		//changes the active-object
		activeObjectSelector.setSelectedIndex(0);
		activeObjectSelector.removeAllItems();
		for (int i = 0; i < controller.getObjectNameArray().length; i++){
			activeObjectSelector.addItem(controller.getObjectNameArray()[i]);
		}
		activeObjectSelector.setSelectedIndex(controller.getObjectNameArray().length-1);
		activeSelectionChanged();
		if (activeObjectSelector.getSelectedIndex() == -1){
			activeObjectSelector.addItem("No objects exist");
		}
		
		//changes the algorithms
		saMethSel.removeAllItems();
		for (int i = 0; i < controller.getSAMeths().size(); i++){
			saMethSel.addItem(controller.getSAMeths().get(i));
		}
		if (controller.getSAMeths().size() == 0){
			saMethSel.addItem("No methods exist");
		}
		saMethSel.setSelectedIndex(0);
		
		volMethSel.removeAllItems();
		for (int i = 0; i < controller.getVolMeths().size(); i++){
			volMethSel.addItem(controller.getVolMeths().get(i));
		}
		if (controller.getVolMeths().size() == 0){
			volMethSel.addItem("No methods exist");
		}
		volMethSel.setSelectedIndex(0);
	}
	
	public void activeSelectionChanged(){
		if (controller.getObjectNameArray().length > 0){
			controller.activeSelectionChanged(activeObjectSelector.getSelectedIndex());
		}
	}
}
