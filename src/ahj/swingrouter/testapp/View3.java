package ahj.swingrouter.testapp;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import ahj.swingrouter.router.Route;

@SuppressWarnings("serial")
@Route(url="view3")
public class View3 extends AbstractView {
	public View3() {
		super();
		
		JButton btn2 = new JButton(new RouteAction("View 2", "view2"));
		JButton btn4 = new JButton(new RouteAction("View 4", "view4"));
		JPanel panel = createButtonPanel(btn2, btn4);
		add(new JScrollPane(new JTree()), BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
	}
}