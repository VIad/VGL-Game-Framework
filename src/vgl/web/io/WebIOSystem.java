package vgl.web.io;

import java.util.Arrays;

import com.google.gwt.xhr.client.XMLHttpRequest;
import com.vgl.gwtreq.client.GWTArrayBuffer;

import jdk.nashorn.internal.parser.Lexer.XMLToken;
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
		request.setResponseType(XMLHttpRequest.ResponseType.Default);
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
	public String readString(FileDetails file, ReadOption... options) {
		throw new VGLRuntimeException(
		        "this instance of readString method is not supported on platform : " + GlobalDetails.getPlatform()
		                + ", instead use readString(callback)");
	}

	@Override
	public FileDetails file(String file) {
		return new WebFileDetails(file);
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


}
