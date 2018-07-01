package vgl.desktop;

import vgl.core.buffers.MemoryBuffer;
import vgl.desktop.tools.DesktopResourceLoader;
import vgl.desktop.utils.DesktopLogger;
import vgl.platform.IFactory;
import vgl.platform.logging.ILogger;
import vgl.tools.IResourceLoader;

public class DesktopFactory implements IFactory {

	@Override
	public MemoryBuffer dataBuffer(int byteCapacity) {
		return new DesktopMemoryBuffer(byteCapacity);
	}

	@Override
	public ILogger newLogger(String name) {
		return new DesktopLogger(name);
	}

	@Override
	public IResourceLoader createResourceLoader() {
		return new DesktopResourceLoader();
	}

}
