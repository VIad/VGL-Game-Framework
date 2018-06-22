package vgl.platform;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.io.FileDetails;

abstract public class Factory {

	abstract public MemoryBuffer newMemoryBuffer(int byteCapacity);

	abstract public FileDetails newFileDetails(String file);
}
