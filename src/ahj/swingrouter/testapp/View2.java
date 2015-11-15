package ahj.swingrouter.testapp;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ahj.swingrouter.router.Route;

@SuppressWarnings("serial")
@Route(url="view2")
public class View2 extends AbstractView {
	private static final String TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
	
	public View2() {
		super();
		
		JTextArea ta = new JTextArea();
		ta.setWrapStyleWord(true);
		ta.setLineWrap(true);
		ta.setEditable(false);
		ta.setText(TEXT);
		
		JButton btn3 = new JButton(new RouteAction("View 3", "view3"));
		JPanel panel = createButtonPanel(btn3);
		add(new JScrollPane(ta), BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
	}
}