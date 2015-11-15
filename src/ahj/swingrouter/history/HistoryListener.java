package ahj.swingrouter.history;

import java.util.EventListener;

public interface HistoryListener extends EventListener {
	void historyChanged(HistoryEvent event);
}