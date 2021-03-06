package vgl.desktop;

import vgl.core.buffers.MemoryBuffer;
import vgl.core.gfx.render.IRenderer2D;
import vgl.core.gfx.render.IRenderer2D.OverflowPolicy;
import vgl.core.internal.Tasking;
import vgl.desktop.gfx.renderer.DirectGPUAccessRenderer2D;
import vgl.desktop.system.DesktopTasking;
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

	@Override
	public IRenderer2D newPlatformOptimalRenderer2D(int batchesHint) {
		return new DirectGPUAccessRenderer2D(batchesHint)
					    .usingOverflowPolicy(OverflowPolicy.DO_RENDER);
	}

	@Override
	public IFactory.Internal internalFrameworkFactory() {
		return new DesktopInternal();
	}
	
	private class DesktopInternal implements IFactory.Internal{

		@Override
		public Tasking platformSpecificTaskingSystem() {
			// TODO Auto-generated method stub
			return new DesktopTasking();
		}
		
	}

}
