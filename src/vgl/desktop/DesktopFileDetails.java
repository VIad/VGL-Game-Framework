package vgl.desktop;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vgl.platform.io.FileDetails;

public class DesktopFileDetails extends FileDetails {

	private java.io.File file;

	public DesktopFileDetails(String path) {
		this.path = path;
		this.file = new File(path);
	}

	@Override
	public long length() {
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
		return isDirectory() ? null : path.substring(path.lastIndexOf('.') + 1, path.length());
	}

	@Override
	public List<FileDetails> listFiles() {
		if (!isDirectory())
			return EMPTY_FILE_LIST;
		List<FileDetails> details = new ArrayList<>();
		Arrays.stream(file.listFiles()).forEach(f -> details.add(new DesktopFileDetails(f.getAbsolutePath())));
		return details;
	}

}
