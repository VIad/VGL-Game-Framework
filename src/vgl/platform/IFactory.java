package vgl.platform;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.io.FileDetails;
import vgl.platform.logging.ILogger;

public interface IFactory {

	 public MemoryBuffer dataBuffer(int byteCapacity);

	 public ILogger newLogger(String name);
}
