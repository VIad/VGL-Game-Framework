package vgl.platform.io;

import vgl.core.annotation.SupportedPlatforms;
import vgl.platform.Platform;

public interface Files {

	@SupportedPlatforms(values = Platform.DESKTOP)
	FileDetails external(String path);

	FileDetails resource(String path);
	
	FileDetails absolute(String path);
	
	FileDetails getRootFile();
	
	FileDetails getApplicationExecutableFile();
	
	String getRootPath();
	
	boolean isExternalStorageSupported();

}
