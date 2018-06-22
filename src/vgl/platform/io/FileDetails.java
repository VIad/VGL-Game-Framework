package vgl.platform.io;

import java.util.ArrayList;
import java.util.Collections;

import vgl.main.VGL;

abstract public class FileDetails {

	public FileDetails(String path) {
		if (path.startsWith("http") || path.startsWith("ftp"))
			this.path = path;
		else
			this.path = path.replaceAll("\\\\", "" + SEPARATOR).replaceAll("/+", "" + SEPARATOR).trim();
	}

	protected static final java.util.List<FileDetails>	EMPTY_FILE_LIST	= Collections.unmodifiableList(
	        new ArrayList<>());

	protected String									path;

	public static final char							SEPARATOR		= '/';

	abstract public long length();

	abstract public boolean isDirectory();

	abstract public boolean exists();

	abstract public String absolutePath();

	abstract public java.util.List<FileDetails> listFiles();

	abstract public FileDetails getParent();

	abstract public String getExtension();

}
