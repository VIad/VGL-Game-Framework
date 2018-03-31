package vgl.desktop;

import vgl.core.buffers.MemoryBuffer;
import vgl.main.VGL;
import vgl.platform.Factory;
import vgl.platform.io.FileDetails;

public class DesktopFactory extends Factory {

	@Override
	public MemoryBuffer newMemoryBuffer(int byteCapacity) {
		return new DesktopMemoryBuffer(byteCapacity);
	}

	@Override
	public FileDetails newFileDetails(String file) {
		return new DesktopFileDetails(file);
	}

}
