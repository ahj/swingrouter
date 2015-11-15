package ahj.swingrouter.router;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RouteCfg {
	private final String hash;
	private final Class<?> type;
	private final String[] parameters;
	private final Pattern pattern;
	
	public RouteCfg(String url, Class<?> type) {
		this.hash = url;
		this.type = type;
		this.parameters = decodeParameterNames(url);
		this.pattern = Router.createPattern(url, parameters);
	}
	
	public String getHash() {
		return hash;
	}
	
	public Class<?> getType() {
		return type;
	}
	
	public Pattern getPattern() {
		return pattern;
	}
	
	public String[] getParameterNames() {
		return parameters;
	}
	
	//http://stackoverflow.com/questions/8754444/convert-javascript-regular-expression-to-java-syntax
	public static final String[] decodeParameterNames(String url) {
		List<String> params = new ArrayList<String>();
		Pattern paramMatchingRegex = Pattern.compile(":([0-9A-Za-z\\_]*)");
		Matcher matcher = paramMatchingRegex.matcher(url);
		
		while (matcher.find()) {
		      String match = matcher.group();
		      params.add(match.trim().substring(1));
	    }
		
		return params.toArray(new String[params.size()]);
	}
	
	public Map<String, String> match(Matcher matcher) {
		Map<String, String> args = new LinkedHashMap<String, String>();
		
		for (int i = 0; i < matcher.groupCount(); i++) {
			String key = parameters[i];
			String value = matcher.group(i + 1).trim();
			
			args.put(key, value);
		}
		
		return args;
	}
}