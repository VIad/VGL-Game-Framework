package vgl.web;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.Factory;
import vgl.platform.io.FileDetails;
import vgl.web.io.WebFileDetails;

public class WebFactory extends Factory {

	@Override
	public MemoryBuffer newMemoryBuffer(int byteCapacity) {
		return new WebMemoryBuffer(byteCapacity);
	}

	@Override
	public FileDetails newFileDetails(String file) {
		return new WebFileDetails(file);
	}

}
