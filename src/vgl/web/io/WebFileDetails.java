package vgl.web.io;

import java.util.List;

import vgl.main.VGL;
import vgl.platform.io.FileDetails;
import vgl.tools.functional.Result;

public class WebFileDetails extends FileDetails {

	public WebFileDetails(String path) {
		super(path);
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
		return VGL.files.absolute(path.substring(0, path.lastIndexOf(SEPARATOR)));
	}

	@Override
	public String getExtension() {
		return absolutePath().substring(absolutePath().lastIndexOf('.') + 1);
	}

	@Override
	public Result<Long> length() {
		return new Result<>((success, fail) -> {
			XMLHttpRequest request = XMLHttpRequest.create();

            request.open("HEAD", absolutePath(), false);
          
            request.setOnReadyStateChange(xhr ->
            {
                if (request.getStatus() == 404) {
                	success.invoke(-0xffL);
                }

                else if (request.getReadyState() == XMLHttpRequest.DONE && request.getStatus() == 200)
                    try
                    {
                    	success.invoke((long) Long.parseLong(request.getResponseHeader("Content-Length")));
                    }
                    catch (Exception e)
                    {
                    	success.invoke(0L);
                    }
            });
            request.send();
		});
	}

	@Override
	public Result<Boolean> isDirectory() {
		return new Result<>((success, fail) -> success.invoke(true));
	}

	@Override
	public Result<Boolean> exists() {
		return new Result<>((success, fail) -> {

			XMLHttpRequest request = XMLHttpRequest.create();

			request.open("HEAD", absolutePath());

			request.setOnReadyStateChange(xhr -> {
				if (request.getStatus() == 200 || request.getStatus() == 404)
					success.invoke(request.getStatus() != 404);
			});
			request.send();
		});

	}

	@Override
	public Result<Boolean> isFile() {
		return new Result<>((success, fail) -> success.invoke(true));
	}

}
