package vgl.desktop;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.Factory;
import vgl.platform.io.FileDetails;

public class DesktopFactory extends Factory {

	@Override
	public MemoryBuffer dataBuffer(int byteCapacity) {
		return new DesktopMemoryBuffer(byteCapacity);
	}

}
