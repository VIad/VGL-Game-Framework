package vgl.desktop.io;

import java.net.URISyntaxException;

import vgl.main.VGL;
import vgl.platform.io.FileDetails;
import vgl.platform.io.Files;

public class DesktopFiles implements Files{

	@Override
	public FileDetails external(String path) {
		return new DesktopFileDetails(path);
	}

	@Override
	public FileDetails resource(String path) {
		return new DesktopFileDetails(path);
	}
	
	@Override
	public FileDetails absolute(String path) {
		return external(path);
	}

	@Override
	public FileDetails getRootFile() {
		return new DesktopFileDetails("");
	}

	@Override
	public String getRootPath() {
		return "";
	}

	@Override
	public boolean isExternalStorageSupported() {
		return true;
	}

	@Override
	public FileDetails getApplicationExecutableFile() {
		try {
			return new DesktopFileDetails(VGL.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		} catch (URISyntaxException e) {
			return null;
		}
	}


}
