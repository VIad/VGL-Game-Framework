package vgl.platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract public class FileDetails {

	protected static final List<FileDetails>	EMPTY_FILE_LIST	= Collections.unmodifiableList(new ArrayList<>());

	protected String							path;

	public static final char					SEPARATOR		= '/';

	abstract public long size();

	abstract public boolean isDirectory();

	abstract public boolean exists();

	abstract public String absolutePath();

	abstract public java.util.List<FileDetails> listFiles();

	abstract public FileDetails getParent();

	abstract public String getExtension();

}
