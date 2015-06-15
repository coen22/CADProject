package ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.media.nativewindow.util.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import javafx.*;

public class ControlPanel extends JPanel {

	public ControlPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
        JCheckBox uiOutput = new JCheckBox("Show Result in UI?");
		uiOutput.setSelected(true);
		
		JCheckBox t1 = new JCheckBox("Second test");
		uiOutput.setSelected(true);
		
		this.add(uiOutput);
		this.add(t1);
	}
	
	public void paintComponent(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		Color bg = new Color(100, 100, 100, 255);

		// fills background of entire window
		graphics2D.setColor(bg);
		graphics2D.fill(this.getVisibleRect());
	}
}
