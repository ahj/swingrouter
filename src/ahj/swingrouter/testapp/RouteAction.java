package ahj.swingrouter.testapp;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ahj.swingrouter.router.Router;

@SuppressWarnings("serial")
public class RouteAction extends AbstractAction {
	private final String token;
	
	public RouteAction(String label, String token) {
		super(label);
		this.token = token;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Router.getController((Component)event.getSource()).redirectTo(token);
	}
}