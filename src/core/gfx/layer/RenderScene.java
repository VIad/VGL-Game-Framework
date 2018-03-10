package vgl.core.gfx.layer;

import java.util.Stack;

import vgl.core.annotation.VGLInternal;
import vgl.core.internal.Checks;

public class RenderScene {
	@VGLInternal
	private Stack<ILayer2D> layerStack;

	public RenderScene() {
		Checks.checkIfInitialized();
		layerStack = new Stack<>();
	}

	@VGLInternal
	public void pushInternal(ILayer2D layer) {
		this.layerStack.push(layer);
	}

	@VGLInternal
	public ILayer2D popInternal() {
		return layerStack.pop();
	}

	public void renderInternal() {
		for (ILayer2D layer : layerStack) {
			layer._renderInternal();
		}
	}

}
