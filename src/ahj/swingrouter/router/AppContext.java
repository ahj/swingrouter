package ahj.swingrouter.router;

import ahj.swingrouter.history.History;

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
