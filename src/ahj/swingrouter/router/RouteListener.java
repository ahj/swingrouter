package ahj.swingrouter.router;

import java.util.EventListener;

public interface RouteListener extends EventListener {
	void routeChanged(RouteEvent event);
}