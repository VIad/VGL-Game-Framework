package vgl.core.net;

public class HttpResponse {

	private int responseCode;
	
	private String responseData;
	
	private String responseMessage;
	
	public HttpResponse(int responseCode,String responseMessage, String responseData) {
		this.responseCode = responseCode;
		this.responseData = responseData;
		this.responseMessage = responseMessage;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	
	public String getResponseData() {
		return responseData;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}
}
