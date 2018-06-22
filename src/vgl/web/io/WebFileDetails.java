package vgl.web.io;

import java.util.List;

import vgl.platform.io.FileDetails;

public class WebFileDetails extends FileDetails {

	public WebFileDetails(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public long length() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDirectory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String absolutePath() {
		return super.path;
	}

	@Override
	public List<FileDetails> listFiles() {
		return EMPTY_FILE_LIST;
	}

	@Override
	public FileDetails getParent() {
		return null;
	}

	@Override
	public String getExtension() {
		// TODO Auto-generated method stub
		return null;
	}

}
