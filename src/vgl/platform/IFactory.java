package vgl.platform;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.io.FileDetails;
import vgl.platform.logging.ILogger;
import vgl.tools.IResourceLoader;

public interface IFactory {

	 MemoryBuffer dataBuffer(int byteCapacity);

	 ILogger newLogger(String name);
	 
	 IResourceLoader createResourceLoader();
}
