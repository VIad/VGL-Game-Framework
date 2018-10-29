package vgl.core.net;

import vgl.tools.functional.callback.Callback;

public interface Net {

	void browse(String url);
	
	void dispatch(HttpRequest request, Callback<HttpResponse> response);
	
}
