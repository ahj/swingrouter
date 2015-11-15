package ahj.swingrouter.router;

import java.awt.Container;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.event.EventListenerList;

import org.reflections.Reflections;

import ahj.swingrouter.history.History;
import ahj.swingrouter.history.HistoryEvent;
import ahj.swingrouter.history.HistoryListener;

public class Router {
	private Container owner;
	private String defaultToken;
	private Container defaultContainer;
	private boolean firstTime = true;
	private Map<String, Class<?>> routes = new HashMap<String, Class<?>>();

	/** List of listeners */
    protected EventListenerList listenerList = new EventListenerList();
    
    private final History history;
    
	public Router(Container owner, History history) {
		super();
		this.owner = owner;
		this.history = history;
		
		history.addHistoryListener(new HistoryListener() {
			public void historyChanged(HistoryEvent event) {
				fireRouteChanged(this, event.getUrl());
			}
		});
		
		addRouteListener(new RouteListener() {
			public void routeChanged(RouteEvent event) {
				execute(event.getHash());
			}
		});
		
		scanRoutes();
		
		owner.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				if (firstTime && (defaultToken != null)) {
					redirectTo(defaultToken);
				}
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}
	
	/**
     * Adds a listener to the list that's notified each time a change
     * to the router occurs.
     *
     * @param   listener               the RouteListener
     */
	public void addRouteListener(RouteListener listener) {
		listenerList.add(RouteListener.class, listener);
	}
	
    /**
     * Removes a listener from the list that's notified each time a
     * change to the router occurs.
     *
     * @param   listener               the RouteListener
     */
	public void removeRouteListener(RouteListener listener) {
		listenerList.remove(RouteListener.class, listener);
	}
	
    /**
     * Returns an array of all the route listeners registered on this router.
     *
     * @return all of this router's <code>RouteListener</code>s
     *         or an empty array if no route listeners are currently registered
     *
     * @see #addRouteListener
     * @see #removeRouteListener
     *
     * @since 1.4
     */
    public RouteListener[] getRouteListeners() {
        return listenerList.getListeners(RouteListener.class);
    }
    
    public void fireRouteChanged(Object source, String url) {
    	fireRouteChanged(new RouteEvent(source, url));
    }
    
    public void fireRouteChanged(RouteEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i] == RouteListener.class) {
                ((RouteListener)listeners[i+1]).routeChanged(e);
            }
        }
    }
    
	public Container getOwner() {
		return owner;
	}
	
	public void setDefaultToken(String defaultToken) {
		this.defaultToken = defaultToken;
	}
	
	public void setDefaultContainer(Container defaultContainer) {
		this.defaultContainer = defaultContainer;
	}
	
	public boolean redirectTo(String hash) {
		return redirectTo(hash, false);
	}
	
	public boolean redirectTo(String token, boolean force) {
		 boolean isCurrent = token.equals(history.getToken());
		 boolean done = false;

        if (!isCurrent) {
            done = true;
            history.add(token);
        } else if (force) {
            done = true;
            fireRouteChanged(this, token);
        }
	
        return done;
	}		
		
	public void execute(String hash) {
		//Look up the hash to find a match
		Class<?> viewType = recognize(hash);

		if (viewType == null) {
			viewType = recognize("notFound");
		}

		Object view = null;
		
		try {
			view = viewType.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		defaultContainer.removeAll();
		
		if (view != null) {
			if (view instanceof Container) {
				defaultContainer.add((Container)view);
				defaultContainer.revalidate();
				defaultContainer.repaint();
			}
		} else {
			//TODO error handling
		}
	}

	protected Class<?> recognize(String hash) {
		Class<?> type = routes.get(hash);
		return (type != null) ? type : null;
	}
	
	protected Route getUnmatchedRoute(String hash) {
		return null;
	}
	
	protected void scanRoutes() {
		Reflections reflections = new Reflections("");

		Set<Class<?>> types = reflections.getTypesAnnotatedWith(Route.class);
		
		for (Class<?> type : types) {
			Route route = type.getAnnotation(Route.class);

			System.out.println(route.url() + " > " + type.getName());
			
			routes.put(route.url(), type);
		}
	}
}