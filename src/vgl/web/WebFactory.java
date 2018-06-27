package vgl.web;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.IFactory;
import vgl.platform.io.FileDetails;
import vgl.web.io.WebFileDetails;

public class WebFactory implements IFactory {

	@Override
	public MemoryBuffer dataBuffer(int byteCapacity) {
		return new WebMemoryBuffer(byteCapacity);
	}

}
