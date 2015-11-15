package ahj.swingrouter.testapp;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ahj.swingrouter.router.Route;

@SuppressWarnings("serial")
@Route(url="notFound")
public class NotFoundView extends JPanel {
	public NotFoundView() {
		super(new BorderLayout());
		
		add(new JLabel("View Not Found", JLabel.CENTER), BorderLayout.CENTER);
	}
}