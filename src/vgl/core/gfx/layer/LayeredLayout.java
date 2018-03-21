package vgl.core.gfx.layer;

import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.Stack;

import org.lwjgl.opengl.GL11;

import vgl.core.annotation.VGLInternal;

public class LayeredLayout implements ILayout {

	private Stack<ILayer> layerStack;

	public LayeredLayout() {
		this.layerStack = new Stack<>();
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
			if (!GL11.glGetBoolean(GL11.GL_BLEND)) {
				glEnable(GL11.GL_BLEND);
				glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}
		layerStack.push(layer);
	}

	public ILayer popLayer() {
		return layerStack.pop();
	}
}
