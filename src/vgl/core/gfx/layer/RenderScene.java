package vgl.core.gfx.layer;

import java.util.Stack;

import vgl.core.annotation.VGLInternal;
import vgl.core.internal.Checks;

public class RenderScene {
	@VGLInternal
	private Stack<ILayer> layerStack;

	public RenderScene() {
		layerStack = new Stack<>();
	}

	@VGLInternal
	public void pushInternal(ILayer layer) {
		this.layerStack.push(layer);
	}

	@VGLInternal
	public ILayer popInternal() {
		return layerStack.pop();
	}

	public void renderInternal() {
		for (ILayer layer : layerStack) {
			layer._renderInternal();
		}
	}

}
