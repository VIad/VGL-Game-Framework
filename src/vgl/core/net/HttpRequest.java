package vgl.core.net;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

	private Map<String, String> requestHeaders;

	private String url;
	
	private Method method;
	
	private String data;
	
	private HttpRequest(Builder builder) {
		this.requestHeaders = builder.requestHeaders;
		this.url = builder.url;
		this.method = builder.method;
		this.data = builder.data;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public Map<String, String> getRequestHeaders() {
		return requestHeaders;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public String getUrl() {
		return url;
	}
	
	public static class Builder{
		
		private Map<String, String> requestHeaders;

		private String url;
		
		private Method method;
		
		private String data;
		
		private Builder() {
			this.requestHeaders = new HashMap<>();
			this.method = Method.GET;
		}
		
		public HttpRequest build() {
			return new HttpRequest(this);
		}
		
		public Builder url(String url) {
			this.url = url;
			return this;
		}
		
		public Builder method(Method method) {
			this.method = method;
			return this;
		}
		
		public Builder requestHeader(String header, String value){
			this.requestHeaders.put(header, value);
			return this;
		}
		
		public Builder requestData(String data) {
			this.data = data;
			return this;
		}
		
	}
	
	public static enum Method{
		POST,
		PUT,
		HEAD,
		GET;
	}

	public String getRequestData() {
		return data;
	}
}
