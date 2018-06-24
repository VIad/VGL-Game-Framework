package vgl.platform;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.io.FileDetails;

abstract public class Factory {

	abstract public MemoryBuffer dataBuffer(int byteCapacity);


}
