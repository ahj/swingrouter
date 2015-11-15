package ahj.swingrouter.router;

import java.util.EventObject;

@SuppressWarnings("serial")
public class RouteEvent extends EventObject {
    private final String hash;
    
	public RouteEvent(Object source, String hash) {
		super(source);
		this.hash = hash;
	}
	
	public String getHash() {
		return hash;
	}
}