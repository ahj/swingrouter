package ahj.swingrouter.testapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class AbstractView extends JPanel {
	public AbstractView() {
		super(new BorderLayout());
	}
	
	public static JPanel createButtonPanel(JButton...buttons) {
		JPanel panel = new JPanel(new FlowLayout());
		for (JButton button : buttons) {
			panel.add(button);
		}
		return panel;
	}
}