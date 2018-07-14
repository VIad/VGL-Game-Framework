package vgl.web.io;

import com.google.gwt.core.client.GWT;

import vgl.core.exception.VGLIOException;
import vgl.platform.io.FileDetails;
import vgl.platform.io.Files;

public class WebFiles implements Files{

	@Override
	public FileDetails external(String path) {
		throw new VGLIOException("Cannot access external files while running in a browser");
	}

	@Override
	public FileDetails resource(String path) {
		return new WebFileDetails(GWT.getHostPageBaseURL() + path);
	}

	
	@Override
	public FileDetails absolute(String path) {
		return new WebFileDetails(path);
	}
	
	@Override
	public FileDetails getRootFile() {
		return new WebFileDetails(getRootPath());
	}

	@Override
	public FileDetails getApplicationExecutableFile() {
		return getRootFile();
	}

	@Override
	public String getRootPath() {
		return GWT.getHostPageBaseURL();
	}

	@Override
	public boolean isExternalStorageSupported() {
		return false;
	}

}
