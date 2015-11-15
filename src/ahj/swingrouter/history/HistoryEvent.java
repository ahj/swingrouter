package ahj.swingrouter.history;

import java.util.EventObject;

@SuppressWarnings("serial")
public class HistoryEvent extends EventObject {
    private final String url;
    
	public HistoryEvent(Object source, String url) {
		super(source);
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
}