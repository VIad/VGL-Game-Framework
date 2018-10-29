package vgl.web.networking;

import vgl.core.net.HttpRequest;
import vgl.core.net.HttpResponse;
import vgl.core.net.Net;
import vgl.tools.StringTools;
import vgl.tools.functional.callback.Callback;
import vgl.web.WebSpecific;
import vgl.web.io.XMLHttpRequest;

public class WebNetworkSystem implements Net{

	@Override
	public void browse(String url) {
		WebSpecific.JS.open(StringTools.urlify(url));
	}

	@Override
	public void dispatch(HttpRequest request, Callback<HttpResponse> response) {
		XMLHttpRequest xmlhttp = XMLHttpRequest.create();
		xmlhttp.open(request.getMethod().name(), request.getUrl());
		request.getRequestHeaders().forEach(xmlhttp::setRequestHeader);
		xmlhttp.setResponseType(XMLHttpRequest.ResponseType.Default);
		xmlhttp.setOnReadyStateChange(xhr -> {
			if(xmlhttp.getReadyState() == XMLHttpRequest.DONE) {
				response.invoke(new HttpResponse(xmlhttp.getStatus(),xmlhttp.getStatusText(), xmlhttp.getResponseText()));
			}
		});
		if(request.getMethod() == HttpRequest.Method.POST ||
		   request.getMethod() == HttpRequest.Method.PUT) {
			xmlhttp.send(request.getRequestData());
		}else {
			xmlhttp.send();
		}
	}

}
