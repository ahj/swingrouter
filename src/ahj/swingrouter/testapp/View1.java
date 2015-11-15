package ahj.swingrouter.testapp;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ahj.swingrouter.router.Route;

@SuppressWarnings("serial")
@Route(url="home")
public class View1 extends AbstractView {
	private static final String[] TABLE_COLUMNS = new String[] {
			"First Name",
			"Last Name",
			"Age",
			"Sex"
	};
	
	private static final String[][] TABLE_DATA = new String[][] {
		{ "John", "Smith", "49", "Male" },
		{ "Jill", "Jones", "19", "Female" }
	};
	
	public View1() {
		super();
		
		JButton btn2 = new JButton(new RouteAction("View 2", "view2"));
		JButton btn4 = new JButton(new RouteAction("View 4", "view4"));
		JPanel panel = createButtonPanel(btn2, btn4);
		add(new JScrollPane(new JTable(new DefaultTableModel(TABLE_DATA, TABLE_COLUMNS))), BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
	}
}