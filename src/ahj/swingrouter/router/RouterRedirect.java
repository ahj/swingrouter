package ahj.swingrouter.router;

public interface RouterRedirect {
	boolean redirectTo(String token);
	boolean redirectTo(String token, boolean force);
}
