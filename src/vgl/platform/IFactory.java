package vgl.platform;

import vgl.core.buffers.MemoryBuffer;
import vgl.core.gfx.render.IRenderer2D;
import vgl.core.internal.Tasking;
import vgl.platform.logging.ILogger;
import vgl.tools.IResourceLoader;

public interface IFactory {

	 MemoryBuffer dataBuffer(int byteCapacity);

	 ILogger newLogger(String name);
	 
	 IResourceLoader createResourceLoader();
	 
	 IRenderer2D newPlatformOptimalRenderer2D(int expectedBatches);
	 
	 Internal internalFrameworkFactory();
	 
	 public interface Internal{
		 
		 Tasking platformSpecificTaskingSystem();
		 
	 }
}
