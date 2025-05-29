package traffic.light.ui;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class TrafficLightFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	protected CrossWalkPanel crossing;
	protected ButtonPanel buttonPanel;

	
	protected void init() {
		this.addWindowListener(new WindowAdapter() {			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});		
		
		this.createContents();
	}
	
	protected void createContents() {
		setLayout(new BorderLayout());
		setTitle("Traffic Light Control");
		crossing = new CrossWalkPanel();
		add(BorderLayout.CENTER, crossing);
		buttonPanel = new ButtonPanel();
		add(BorderLayout.SOUTH, buttonPanel);
		setSize(235, 580);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}


}
