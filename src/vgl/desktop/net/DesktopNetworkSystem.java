package vgl.desktop.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import vgl.core.net.HttpRequest;
import vgl.core.net.HttpResponse;
import vgl.core.net.Net;
import vgl.desktop.DesktopSpecific;
import vgl.main.VGL;
import vgl.tools.StringTools;
import vgl.tools.functional.callback.Callback;

public class DesktopNetworkSystem implements Net{

	@Override
	public void browse(String url) {
		DesktopSpecific.Utility.browse(StringTools.urlify(url));
	}

	@Override
	public void dispatch(HttpRequest request, Callback<HttpResponse> response) {
		DesktopSpecific.Tasking.THREAD_POOL.execute(() -> {
			try {
				HttpURLConnection connection = (HttpURLConnection) new URL(request.getUrl()).openConnection();
				request.getRequestHeaders().forEach(connection::addRequestProperty);
				connection.setRequestMethod(request.getMethod().name());
				if(request.getMethod() == HttpRequest.Method.POST || 
				   request.getMethod() == HttpRequest.Method.PUT) {
					//Write
					connection.setDoOutput(true);
					OutputStreamWriter ostream = new OutputStreamWriter(connection.getOutputStream());
					ostream.write(request.getRequestData());
					ostream.flush();
					ostream.close();
					response.invoke(new HttpResponse(connection.getResponseCode(), connection.getResponseMessage(), null));
				}else {
					//Read
					connection.setDoInput(true);
					InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
					BufferedReader bufferedReader = new BufferedReader(streamReader);
					StringBuilder responseBuilder = new StringBuilder();
					bufferedReader.lines()
					              .forEach(line -> responseBuilder.append(line).append('\n'));
					streamReader.close();
					bufferedReader.close();
					response.invoke(new HttpResponse(connection.getResponseCode(),connection.getResponseMessage(), responseBuilder.toString()));
				}
				connection.disconnect();
			} catch (IOException e) {
				VGL.errorChannel.forward(() -> e);
			}
		});
	}

}
