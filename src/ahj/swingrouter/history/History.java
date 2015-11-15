package ahj.swingrouter.history;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

public class History {
    private final EventListenerList listenerList = new EventListenerList();

    private final List<String> history = new ArrayList<String>();
	private int historyIndex = -1;

	public String getToken() {
		return (historyIndex >= 0) ? history.get(historyIndex) : null;
	}
	
	public void add(String token) {
		history.add(token);
		historyIndex++;
		
		fireHistoryChange(this, token);
	}
	
	public void back() {
		if (historyIndex > 0) {
			historyIndex--;
			
			fireHistoryChange(this, history.get(historyIndex));
		}
	}
	
	public void forward() {
		if (historyIndex < (history.size() - 1)) {
			historyIndex++;
			
			fireHistoryChange(this, history.get(historyIndex));
		}
	}
	
	public boolean canGoBack() {
		return historyIndex > 0;
	}
	
	public boolean canGoForward() {
		return historyIndex < (history.size() - 1);
	}
	
	/**
     * Adds a listener to the list that's notified each time a change
     * to the history occurs.
     *
     * @param   listener               the HistoryListener
     */
	public void addHistoryListener(HistoryListener listener) {
		listenerList.add(HistoryListener.class, listener);
	}
	
    /**
     * Removes a listener from the list that's notified each time a
     * change to the history occurs.
     *
     * @param   listener               the HistoryListener
     */
	public void removeRouteListener(HistoryListener listener) {
		listenerList.remove(HistoryListener.class, listener);
	}
	
    /**
     * Returns an array of all the history listeners registered on this history.
     *
     * @return all of this history's <code>HistoryListener</code>s
     *         or an empty array if no history listeners are currently registered
     *
     * @see #addHistoryListener
     * @see #removeHistoryListener
     *
     * @since 1.4
     */
    public HistoryListener[] getHistoryListeners() {
        return listenerList.getListeners(HistoryListener.class);
    }
    
    public void fireHistoryChange(Object source, String url) {
    	fireHistoryChanged(new HistoryEvent(source, url));
    }
    
    public void fireHistoryChanged(HistoryEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i] == HistoryListener.class) {
                ((HistoryListener)listeners[i+1]).historyChanged(e);
            }
        }
    }
}