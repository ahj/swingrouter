package ahj.swingrouter.testapp;

import ahj.swingrouter.history.History;
import ahj.swingrouter.router.Router;

public class AppContext {
	private final History history;
	private final Router router;
	
	public AppContext(History history, Router router) {
		super();
		this.history = history;
		this.router = router;
	}
	
	public History getHistory() {
		return history;
	}
	
	public Router getRouter() {
		return router;
	}
}
