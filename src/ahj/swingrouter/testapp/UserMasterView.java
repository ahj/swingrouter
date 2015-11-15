package ahj.swingrouter.testapp;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ahj.swingrouter.router.Route;
import ahj.swingrouter.router.Router;

@SuppressWarnings("serial")
@Route(url="home")
public class UserMasterView extends AbstractView {
	private static final String[] TABLE_COLUMNS = new String[] {
			"Index",
			"First Name",
			"Last Name",
			"Age",
			"Sex"
	};
	
	public static final String[][] TABLE_DATA = new String[][] {
		{ "13423", "John", "Smith", "49", "Male" },
		{ "21211", "Jill", "Jones", "19", "Female" }
	};
	
	public UserMasterView() {
		super();
		
		final JTable table = new JTable(new DefaultTableModel(TABLE_DATA, TABLE_COLUMNS) {
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					int rowViewIndex = table.rowAtPoint(event.getPoint());
					int rowModelIndex = table.convertRowIndexToModel(rowViewIndex);
					
					String index = (String)table.getModel().getValueAt(rowModelIndex, 0);
					
					Router.getController(event.getComponent()).redirectTo("userdetail/" + index);
				}
			}
		});
		
		JButton btn2 = new JButton(new RouteAction("View 2", "view2"));
		JButton btn4 = new JButton(new RouteAction("View 4", "view4"));
		JPanel panel = createButtonPanel(btn2, btn4);
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
	}
}