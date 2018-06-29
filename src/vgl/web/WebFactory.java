package vgl.web;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.IFactory;
import vgl.platform.io.FileDetails;
import vgl.platform.logging.ILogger;
import vgl.web.io.WebFileDetails;
import vgl.web.utils.WebLogger;

public class WebFactory implements IFactory {

	@Override
	public MemoryBuffer dataBuffer(int byteCapacity) {
		return new WebMemoryBuffer(byteCapacity);
	}

	@Override
	public ILogger newLogger(String name) {
		return new WebLogger(name);
	}

}
