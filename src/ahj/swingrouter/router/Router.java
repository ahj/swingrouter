package ahj.swingrouter.router;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;
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
	private Set<RouteCfg> configs = new HashSet<RouteCfg>();
	
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
		RouteCfg cfg = recognize(hash);

		if (cfg == null) {
			cfg = recognize("notFound");
		}

		Object view = null;
		
		try {
			// Pass parameters to the view
			Pattern pattern = cfg.getPattern();
			Matcher matcher = pattern.matcher(hash);
			matcher.matches();
			Map<String, String> parameters = cfg.match(matcher);
			
			String[] argNames = cfg.getParameterNames();
			@SuppressWarnings("unchecked")
			Class<String>[] argClasses = new Class[argNames.length];
			Object[] argValues = new Object[argNames.length];
			for (int i = 0; i < argNames.length; i++) {
				argClasses[i] = String.class;
				argValues[i] = parameters.get(argNames[i]);
			}
			
			Constructor<?> c = cfg.getType().getConstructor(argClasses);
			
			view = c.newInstance(argValues);
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

	protected RouteCfg recognize(String hash) {
		for (RouteCfg cfg : configs) {
			Matcher matcher = cfg.getPattern().matcher(hash);
			if (matcher.matches()) {
				return cfg;
			}
		}
		return null;
	}
	
	protected Route getUnmatchedRoute(String hash) {
		return null;
	}
	
	public static void main(String...args) {
//		String routeHash = "section/:section/user/:id";
//		String hash = "section/4a/user/1234";
		String routeHash = "section/:section";
		String hash = "section/4a";
		
		RouteCfg cfg = new RouteCfg(routeHash, null);
		Matcher routeMatcher = cfg.getPattern().matcher(hash);
		
		if (routeMatcher.matches()) {
			System.out.println("matches " + cfg.getHash());
			
			Map<String, String> data = cfg.match(routeMatcher);
			
			for (Map.Entry<String, String> entry : data.entrySet()) {
				System.out.println("  " + entry.getKey() + " = " + entry.getValue());
			}
		}
	}
	
	/**
     * Takes the configured url string including wild-cards and returns a regex that can be
     * used to match against a url.
     *
     * @private
     * @param {String} url The url string.
     * @return {RegExp} The matcher regex.
     */
    public static Pattern createPattern(String url, String[] paramsInMatchString) {
        for (int i = 0; i < paramsInMatchString.length; i++) {
            String matcher = "([%a-zA-Z0-9\\-\\_\\s,]+)";

            url = url.replace(":" + paramsInMatchString[i], matcher);
        }

        return Pattern.compile(url);
    }

	protected void scanRoutes() {
		Reflections reflections = new Reflections("");

		Set<Class<?>> types = reflections.getTypesAnnotatedWith(Route.class);
		
		for (Class<?> type : types) {
			Route route = type.getAnnotation(Route.class);

			System.out.println(route.url() + " > " + type.getName());
			
			RouteCfg cfg = new RouteCfg(route.url(), type);
			
			configs.add(cfg);
		}
	}
	
	public static RouterRedirect getController(Component source) {
		RouterRedirect rr = (RouterRedirect)SwingUtilities.getAncestorOfClass(RouterRedirect.class, source);
		return rr;
	}
}