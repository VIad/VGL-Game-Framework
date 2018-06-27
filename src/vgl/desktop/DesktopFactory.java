package vgl.desktop;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.IFactory;
import vgl.platform.io.FileDetails;

public class DesktopFactory implements IFactory {

	@Override
	public MemoryBuffer dataBuffer(int byteCapacity) {
		return new DesktopMemoryBuffer(byteCapacity);
	}

}
