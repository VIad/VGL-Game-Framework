package vgl.platform;

import vgl.core.buffers.MemoryBuffer;

abstract public class Factory {
	
	abstract public MemoryBuffer newMemoryBuffer(int byteCapacity);
	
}
