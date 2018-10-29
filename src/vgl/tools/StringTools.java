package vgl.tools;

public class StringTools {

	public static String urlify(String url) {
		return (url.startsWith("http://") || url.startsWith("http://")) ? url : "http://" + url;
	}

}
