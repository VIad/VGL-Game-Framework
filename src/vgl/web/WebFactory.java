package vgl.web;

import vgl.core.buffers.MemoryBuffer;
import vgl.core.gfx.render.IRenderer2D;
import vgl.core.gfx.render.IRenderer2D.OverflowPolicy;
import vgl.core.gfx.render.SpriteBatchRenderer;
import vgl.core.internal.Tasking;
import vgl.platform.IFactory;
import vgl.platform.logging.ILogger;
import vgl.tools.IResourceLoader;
import vgl.web.tools.WebResourceLoader;
import vgl.web.tools.WebTaskingSystem;
import vgl.web.utils.WebLogger;

public class WebFactory implements IFactory {

	@Override
	public MemoryBuffer dataBuffer(int byteCapacity) {
		return new WebMemoryBuffer(byteCapacity);
	}

	@Override
	public ILogger newLogger(String name) {
		return new WebLogger(name);
	}

	@Override
	public IResourceLoader createResourceLoader() {
		return new WebResourceLoader();
	}

	@Override
	public IRenderer2D newPlatformOptimalRenderer2D(int batchesHint) {
		return new SpriteBatchRenderer(batchesHint, SpriteBatchRenderer.STD_BATCH_LAYOUT)
				                           .usingOverflowPolicy(OverflowPolicy.DO_RENDER);
	}

	@Override
	public IFactory.Internal internalFrameworkFactory() {
		return new WebInternalFactory();
	}
	
	private class WebInternalFactory implements IFactory.Internal{

		@Override
		public Tasking platformSpecificTaskingSystem() {
			return new WebTaskingSystem();
		}
		
	}

}
