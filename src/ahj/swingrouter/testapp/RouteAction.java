package ahj.swingrouter.testapp;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class RouteAction extends AbstractAction {
	private final String url;
	
	public RouteAction(String label, String url) {
		super(label);
		this.url = url;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		AppFrame owner = (AppFrame)SwingUtilities.getAncestorOfClass(AppFrame.class, ((Component)event.getSource()));
		if (owner != null) {
			AppContext context = owner.getContext();
			context.getRouter().redirectTo(url);
		}
	}
}