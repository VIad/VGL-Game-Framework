package vgl.web;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.Factory;
import vgl.platform.io.FileDetails;
import vgl.web.io.WebFileDetails;

public class WebFactory extends Factory {

	@Override
	public MemoryBuffer dataBuffer(int byteCapacity) {
		return new WebMemoryBuffer(byteCapacity);
	}

}
