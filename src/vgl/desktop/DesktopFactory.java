package vgl.desktop;

import vgl.core.buffers.MemoryBuffer;
import vgl.main.VGL;
import vgl.platform.Factory;

public class DesktopFactory extends Factory {

	@Override
	public MemoryBuffer newMemoryBuffer(int byteCapacity) {
		return new DesktopMemoryBuffer(byteCapacity);
	}

}
