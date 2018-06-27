package vgl.platform;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.io.FileDetails;

public interface IFactory {

	 public MemoryBuffer dataBuffer(int byteCapacity);

}
