package vgl.web.io;

import com.google.gwt.xhr.client.XMLHttpRequest;
import com.vgl.gwtreq.client.GWTArrayBuffer;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.io.FileDetails;
import vgl.platform.io.IOSystem;
import vgl.platform.io.ReadOption;
import vgl.tools.functional.callback.Callback;
import vgl.web.WebMemoryBuffer;

public class WebIOSystem extends IOSystem {

	@Override
	public void readBytes(FileDetails file, Callback<MemoryBuffer> result) {
		XMLHttpRequest request = XMLHttpRequest.create();
		request.open("GET", file.absolutePath());
		request.setResponseType(XMLHttpRequest.ResponseType.ArrayBuffer);
		request.setOnReadyStateChange(xhr -> {
			if (request.getReadyState() == XMLHttpRequest.DONE) {
				if (request.getStatus() == 200) {
					result.invoke(new WebMemoryBuffer(new GWTArrayBuffer(request.getResponseArrayBuffer())));
				}
			}
		});
		request.send();
	}

	@Override
	public MemoryBuffer readBytes(FileDetails file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readString(FileDetails file, Callback<String> result, ReadOption... options) {
		// TODO Auto-generated method stub

	}

	@Override
	public String readString(FileDetails file, ReadOption... options) {
		// TODO Auto-generated method stub
		return null;
	}

}
