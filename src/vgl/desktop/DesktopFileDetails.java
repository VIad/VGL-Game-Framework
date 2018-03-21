package vgl.desktop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import vgl.platform.FileDetails;

public class DesktopFileDetails extends FileDetails {

	private java.io.File file;

	public DesktopFileDetails(String path) {
		this.path = path;
		this.file = new File(path);
	}

	@Override
	public long size() {
		return file.length();
	}

	@Override
	public boolean isDirectory() {
		return file.isDirectory();
	}

	@Override
	public boolean exists() {
		return file.exists();
	}

	@Override
	public String absolutePath() {
		return file.getAbsolutePath();
	}

	@Override
	public FileDetails getParent() {
		return new DesktopFileDetails(file.getParent());
	}

	@Override
	public String getExtension() {
		return isDirectory() ?
				             null 
				           : path.substring(path.lastIndexOf('.'), path.length());
	}

	@Override
	public List<FileDetails> listFiles() {
		if (!isDirectory())
			return EMPTY_FILE_LIST;
		File[] files = file.listFiles();
		List<FileDetails> details = new ArrayList<>();
		for (File file : files) {
			details.add(new DesktopFileDetails(file.getAbsolutePath()));
		}
		return details;
	}

}
