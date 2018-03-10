package vgl.core.gfx.render;

import org.lwjgl.opengl.GL11;

import vgl.core.annotation.VSInputAttribute;
import vgl.core.system.Size;
import vgl.maths.vector.Vector3f;
import vgl.maths.vector.Vector4f;

public class VertexLayoutIMPL implements VertexLayout {
	@VSInputAttribute(location = VertexLayout.VERTEX_INDEX)
	Vector3f	vec;
	@VSInputAttribute(location = VertexLayout.COLOR_INDEX)
	Vector4f	color;

	@Override
	public GLSize size(int index) {
		switch (index)
		{
			case VERTEX_INDEX:
				return new GLSize(GL11.GL_FLOAT, 3, Size.of(Vector3f.class));
			default:
				return new GLSize(GL11.GL_FLOAT, 4, Size.of(Vector4f.class));
		}
	}

}
