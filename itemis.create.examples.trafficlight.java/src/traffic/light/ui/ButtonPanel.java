/**
 * Copyright (c) 2016 committers of CREATE and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * 	committers of CREATE - initial API and implementation
 * 
 */
package traffic.light.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton switchBlinkNormal;
	private JButton switchOnOff;

	public ButtonPanel() {
		createContents();
	}

	private void createContents() {
		setLayout(new BorderLayout());
		switchOnOff = new JButton();
		switchOnOff.setText("ON/OFF");
		switchBlinkNormal = new JButton();
		switchBlinkNormal.setText("BLINK/NORMAL");
		add(BorderLayout.CENTER, switchOnOff);
		add(BorderLayout.SOUTH, switchBlinkNormal);
	}

	public JButton getSwitchOnOff() {
		return switchOnOff;
	}

	public JButton getSwitchBlinkNormal() {
		return switchBlinkNormal;
	}

}
