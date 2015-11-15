package ahj.swingrouter.testapp;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import ahj.swingrouter.router.Route;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
@Route(url="userdetail/:id")
public class UserDetailView extends AbstractView {
	private final JTextField firstName = new JTextField(16);
	private final JTextField lastName = new JTextField(16);
	private final JSpinner age = new JSpinner(new SpinnerNumberModel(30, 1, 120, 1));
	private final JComboBox<String> sex = new JComboBox<String>(new String[] { "Male", "Female" });
	
	public UserDetailView(String id) {
		super();

		JPanel form = new JPanel(new MigLayout("wrap 2"));
		form.add(new JLabel("First Name:"));
		form.add(firstName);
		form.add(new JLabel("Last Name:"));
		form.add(lastName);
		form.add(new JLabel("Age:"));
		form.add(age);
		form.add(new JLabel("Sex:"));
		form.add(sex);
		
		setFormData(getFormData(id));
		
		JButton btn = new JButton(new RouteAction("Master", "home"));
		JPanel panel = createButtonPanel(btn);
		add(form, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
	}

	private int findRow(String id) {
		for (int row = 0; row < UserMasterView.TABLE_DATA.length; row++) {
			String[] rowData = UserMasterView.TABLE_DATA[row];
			if (rowData[0].equals(id)) {
				return row;
			}
		}
		return -1;
	}
	
	public void setFormData(Map<String, String> data) {
		firstName.setText(data.get("firstName"));
		lastName.setText(data.get("lastName"));
		age.setValue(Integer.parseInt(data.get("age")));
		sex.setToolTipText(data.get("sex"));
	}
	
	private Map<String, String> getFormData(String id) {
		String[] rowData = UserMasterView.TABLE_DATA[findRow(id)];
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("id", rowData[0]);
		data.put("firstName", rowData[1]);
		data.put("lastName", rowData[2]);
		data.put("age", rowData[3]);
		data.put("sex", rowData[4]);
		return data;
	}
}