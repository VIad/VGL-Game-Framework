package vgl.core.gfx.renderable;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallbackI;

import vgl.core.annotation.VGLInternal;
import vgl.maths.geom.Transform2D;

@VGLInternal
@Deprecated
public class PRenderable2D extends Renderable2D {

	private Renderable2D	renderable;
	public float			x, y, width, height;
	public Transform2D		transform;

	@VGLInternal
	public PRenderable2D(Renderable2D ren, float x, float y, float width, float height,
	        Transform2D transform) {
		this.renderable = ren;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.transform = transform;
	}

	@VGLInternal
	public Renderable2D getRenderable() {
		return renderable;
	}

}
