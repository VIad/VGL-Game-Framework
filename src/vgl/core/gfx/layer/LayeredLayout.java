package vgl.core.gfx.layer;

import java.util.Stack;

import com.shc.webgl4j.client.WebGL10;

import vgl.core.annotation.VGLInternal;
import vgl.core.internal.ProcessManager;
import vgl.main.VGL;

public class LayeredLayout implements ILayout {

	private Stack<ILayer> layerStack;

	public LayeredLayout() {
		this.layerStack = new Stack<>();
		VGL.states.addOnActiveStateRender(this::render)
				  .addOnActiveStateUpdate(this::update);
	}

	@Override
	@VGLInternal
	public void render() {
		for (ILayer layer : layerStack) {
			layer._renderInternal();
		}
	}

	public void pushLayer(ILayer layer) {
		if (layerStack.size() > 0)
			if (!VGL.api_gfx.glGetBoolean(WebGL10.GL_BLEND)) {
				VGL.api_gfx.glEnable(WebGL10.GL_BLEND);
				VGL.api_gfx.glBlendFunc(WebGL10.GL_SRC_ALPHA, WebGL10.GL_ONE_MINUS_SRC_ALPHA);
			}
		layerStack.push(layer);
	}

	public ILayer popLayer() {
		return layerStack.pop();
	}

	@Override
	public void update() {
		layerStack.stream().filter(l -> l instanceof ILayer2D).forEach(l -> ((ILayer2D) l).update());
	}
}
