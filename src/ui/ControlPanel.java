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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import obj.DisplayObject;
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
			
		this.add(normals);
		this.add(activeObjectSelector);
		this.add(importOBJ);
		this.add(delete);
		
	}
	
	public void paintComponent(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		Color bg = new Color(100, 100, 100, 255);

		// fills background of entire window
		graphics2D.setColor(bg);
		graphics2D.fill(this.getVisibleRect());
	}
	
	public void itemsChanged(){
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
