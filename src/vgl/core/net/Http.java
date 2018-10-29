package vgl.core.net;

import vgl.main.VGL;
import vgl.tools.functional.callback.Callback;

public class Http {
	
	private Http() {
		
	}
	
	public static void dispatch(HttpRequest request, Callback<HttpResponse> response ) {
		VGL.net.dispatch(request, response);
	}
	
	public static void get(String url, Callback<HttpResponse> response) {
		VGL.net.dispatch(HttpRequest.builder()
				                    .url(url)
				                    .method(HttpRequest.Method.GET)
				                    .build(), response);
	}
	
	public static void post(String url, String data, Callback<HttpResponse> response) {
		VGL.net.dispatch(HttpRequest.builder()
				                    .url(url)
				                    .method(HttpRequest.Method.POST)
				                    .requestData(data)
				                    .build(), response);
	}
	
}
