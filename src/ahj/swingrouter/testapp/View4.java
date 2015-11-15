package ahj.swingrouter.testapp;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ahj.swingrouter.router.Route;

@SuppressWarnings("serial")
@Route(url="view4")
public class View4 extends AbstractView {
	private static final String[] DATA = { "Toothpaste", "Laundry Detergent", "Conditioner", "Garbage Bags", "Brown Bread", "Fig Newtons", "Soda", "Cheddar Cheese", "Candy" };
	
	public View4() {
		super();
		
		JButton btnHome = new JButton(new RouteAction("Home", "home"));
		JButton btn4 = new JButton(new RouteAction("View 3", "view3"));
		JButton btn5 = new JButton(new RouteAction("View 5", "view5"));
		JPanel panel = createButtonPanel(btnHome, btn4, btn5);
		add(new JScrollPane(new JList<String>(DATA)), BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
	}
}