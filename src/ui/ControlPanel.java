package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.media.nativewindow.util.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import javafx.*;

public class ControlPanel extends JPanel{
	
	Controller controller;
	MainFrame mainframe;
	JComboBox<String> activeObjectSelector;

	public ControlPanel(Controller ctrl, MainFrame mainframe){
		this.controller = ctrl;
		this.mainframe = mainframe;
		this.setLayout(new FlowLayout());
		
        JCheckBox normals = new JCheckBox("Show Normals");
        normals.setSelected(false);
        normals.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (normals.isSelected()){
					mainframe.enableNormals();
				}
				else{
					mainframe.disableNormals();
				}
			}
		});
        
        JCheckBox lines = new JCheckBox("Show Lines");
        lines.setSelected(true);
        lines.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (lines.isSelected()){
					mainframe.enableLines();
				}
				else{
					mainframe.disableLines();
				}
			}
		});
		
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
		
		JButton delete = new JButton("delete current object");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.deleteCurrent();
			}
		});
		
		String[] options = {"parametric torus", "parametric shoe", "parametric ship", "parametric sphere", "parametric spiral", "parametric trumpet", "implicit diamond", "implicit genus2", "implicity neovius", "implicit sphere", "implicity sum of sins", "implicit swiss cube" , "implicit torus", "implicity torus-cube", "implicity torus intersecting sphere"};
		JComboBox<String> createFunctionalObject = new JComboBox<String>(options);
		activeObjectSelector.setLightWeightPopupEnabled(false);
		
		JButton create = new JButton("create selected object:");
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createObject(createFunctionalObject.getSelectedIndex());;
			}
		});
		
		JColorChooser colorChooser = new JColorChooser();
		JButton color = new JButton("set colour");
		color.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = colorChooser.showDialog(null, "Select the Color", new Color(0, 0, 0));
				controller.setColor(color);
			}
		});
		
		JButton graphics = new JButton("Toggle Mode");
		graphics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.toggleGraphicsMode();
			}
		});
		
		this.add(activeObjectSelector);
		this.add(delete);
		this.add(color);
		this.add(importOBJ);
		this.add(create);
		this.add(createFunctionalObject);
		this.add(lines);
		this.add(normals);
		this.add(graphics);
		
	}
	
	public void paintComponent(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		Color bg = new Color(100, 100, 100, 255);

		// fills background of entire window
		graphics2D.setColor(bg);
		graphics2D.fill(this.getVisibleRect());
	}
	
	public void itemsChanged(){
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
	}
	
	public void activeSelectionChanged(){
		if (controller.getObjectNameArray().length > 0){
			controller.activeSelectionChanged(activeObjectSelector.getSelectedIndex());
		}
	}
}
