package vgl.desktop.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import vgl.platform.io.FileDetails;
import vgl.tools.functional.Result;
import vgl.tools.functional.SynchronousHold;

public class DesktopFileDetails extends FileDetails {

	public DesktopFileDetails(String path) {
		super(path);
		this.file = new File(path);
	}

	private java.io.File file;


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
		return isDirectory().get() ? 
				              null :
					          path.substring(path.lastIndexOf('.') + 1, path.length());
	}

	@Override
	public List<FileDetails> listFiles() {
		if (!isDirectory().get())
			return EMPTY_FILE_LIST;
		if (file.listFiles() == null)
			return EMPTY_FILE_LIST;
		return Arrays.stream(file.listFiles())
				     .map(file -> new DesktopFileDetails(file.getAbsolutePath()))
				     .collect(Collectors.toList());
	}

	@Override
	public Result<Long> length() {
		return new Result<>((resolve, reject) -> {
			try {
				resolve.invoke(file.length());
			}catch(Throwable t) {
				reject.invoke(t);
			}
		});
	}

	@Override
	public Result<Boolean> isFile() {
		return new Result<>((success, fail) -> {
			try {
				success.invoke(file.isFile());
			}catch(Throwable th) {
				fail.invoke(th);
			}
		}); 
	}

	@Override
	public Result<Boolean> isDirectory() {
		return new Result<>((success, fail) -> {
			try {
				success.invoke(file.isDirectory());
			}catch(Throwable th) {
				fail.invoke(th);
			}
		}); 
	}

	@Override
	public Result<Boolean> exists() {
		return new Result<>((success, fail) -> {
			try {
				success.invoke(file.exists());
			}catch(Throwable th) {
				fail.invoke(th);
			}
		}); 
	}

}
