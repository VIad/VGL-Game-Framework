package vgl.web.io;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.vgl.gwtreq.client.GWTArrayBuffer;

import vgl.core.buffers.MemoryBuffer;
import vgl.core.exception.VGLIOException;
import vgl.core.exception.VGLRuntimeException;
import vgl.core.gfx.Image;
import vgl.core.internal.GlobalDetails;
import vgl.main.VGL;
import vgl.platform.io.FileDetails;
import vgl.platform.io.IOSystem;
import vgl.platform.io.ReadOption;
import vgl.tools.functional.callback.BinaryCallback;
import vgl.tools.functional.callback.Callback;
import vgl.web.WebMemoryBuffer;
import vgl.web.WebSpecific;
import vgl.web.WebSpecific.JS;

public class WebIOSystem extends IOSystem {
	
	public WebIOSystem() {
	}
	
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
	public void readString(FileDetails file, Callback<String> result, ReadOption... options) {
		boolean ignoreLines = Arrays.stream(options).anyMatch(op -> op == ReadOption.IGNORE_NEWLINES);
		XMLHttpRequest request = XMLHttpRequest.create();
		request.open("GET", file.absolutePath());
		request.setOverrideMimeType("text/plain");
		request.setResponseType(XMLHttpRequest.ResponseType.Default);
		request.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
		request.setOnReadyStateChange(xhr -> {
			if (request.getReadyState() == XMLHttpRequest.DONE) {
				if (request.getStatus() == 200) {
					String res = request.getResponseText();
					if (ignoreLines)
						res = res.replaceAll("\n", "");
					result.invoke(res);
				} else {
					result.invoke("");
				}
			}
		});
		request.send();
	}

	@Override
	public MemoryBuffer readBytes(FileDetails file) {
		throw new VGLRuntimeException(
		        "this instance of readBytes method is not supported on platform : " + GlobalDetails.getPlatform()
		                + ", instead use readBytes(callback)");
	}

	@Override
	@Deprecated
	public String readString(FileDetails file, ReadOption... options) {
		return makeSyncAjaxCall(file.absolutePath(), null, "", "GET");
	}

	@Override
	public void memset(MemoryBuffer buffer, int data) {
		for (int i = 0; i < buffer.capacity(); i++) {
			buffer.putByte(i, data);
		}
	}

	@Override
	public void readImage(FileDetails file, BinaryCallback<Image, Throwable> onResult) {
		VGL.io
		   .readBytes(file, bytes -> {
			   WebSpecific.JS
			                .getImage(JS.cast(bytes), image -> onResult.invoke(image, null),
			                        error -> onResult.invoke(null, new VGLIOException("Something went wrong while reading image")));
		   });
	}


	@Deprecated
	private native String makeSyncAjaxCall(String url, String msgText, String resType,String conType)/*-{
		var xhReq = new XMLHttpRequest();
		xhReq.open(conType, url, false);
		xhReq.responseType = resType;
		if (conType == "POST")
			xhReq.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded');
		xhReq.send(msgText);
		var serverResponse = xhReq.status + xhReq.responseText;
		return serverResponse;
	}-*/;
	
}
