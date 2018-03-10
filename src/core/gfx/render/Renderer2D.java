package vgl.core.gfx.render;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import vgl.core.annotation.VGLInternal;
import vgl.core.buffers.IndexBuffer;
import vgl.core.gfx.Color;
import vgl.core.gfx.font.Glyph;
import vgl.core.gfx.font.VFont;
import vgl.core.gfx.layer.GFX2D;
import vgl.core.gfx.layer.ILayer2D;
import vgl.core.gfx.renderable.ColoredSprite;
import vgl.core.gfx.renderable.ImageSprite;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.core.gfx.texture.Texture;
import vgl.core.gl.GLBufferUsage;
import vgl.core.gl.GLTypes;
import vgl.core.internal.Checks;
import vgl.core.main.Window;
import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector3f;

final public class Renderer2D implements IRenderer2D {

	private int					vao;
	private int					vbo;
	private int					indexCount;
	private IndexBuffer			ibo;

	private List<Integer>		textureSlots;

	private static final int	MAX_RENDERABLES				= 100000;
	private static final int	IBO_TOTAL_BUFFER_LENGTH		= 6 * MAX_RENDERABLES;
	private int					RENDERABLE_SIZE;
	private int					VERTEX_ATTRIB_STRIDE;
	private int					RENDERER_BUFFER_SIZE;

	/**
	 * TODO WEBGL shaders don't support more than 8 concurrently bound samplers
	 */
	public static final int		RENDERER_MAX_TEXTURE_UNITS	= 32;

	public Renderer2D() {
		Checks.checkIfInitialized();
		this.textureSlots = new ArrayList<>();
		this.VERTEX_ATTRIB_STRIDE = (Vector3f.SIZE_BYTES + Color.SIZE_BYTES + Vector2f.SIZE_BYTES + Float.BYTES);
		this.RENDERABLE_SIZE = 4 * VERTEX_ATTRIB_STRIDE;
		this.RENDERER_BUFFER_SIZE = MAX_RENDERABLES * RENDERABLE_SIZE;
		init();
	}

	private void init() {
		this.indexCount = 0;
		vao = GL30.glGenVertexArrays();
		vbo = GL15.glGenBuffers();
		GL30.glBindVertexArray(vao);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, RENDERER_BUFFER_SIZE, GLBufferUsage.DYNAMIC_DRAW);

		GL20.glEnableVertexAttribArray(VertexLayout.VERTEX_INDEX);
		GL20.glEnableVertexAttribArray(VertexLayout.COLOR_INDEX);
		GL20.glEnableVertexAttribArray(VertexLayout.UV_INDEX);
		GL20.glEnableVertexAttribArray(VertexLayout.TID_INDEX);

		GL20.glVertexAttribPointer(0, 3, GLTypes.FLOAT, false, VERTEX_ATTRIB_STRIDE, 0);
		GL20.glVertexAttribPointer(1, 4, GLTypes.FLOAT, false, VERTEX_ATTRIB_STRIDE, 12);
		GL20.glVertexAttribPointer(2, 2, GLTypes.FLOAT, false, VERTEX_ATTRIB_STRIDE, 28);
		GL20.glVertexAttribPointer(3, 1, GLTypes.FLOAT, false, VERTEX_ATTRIB_STRIDE, 36);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);

		setupIBO();

	}

	final static private float	scaleX	= (float) Window.getWidth() / 16f;
	final static private float	scaleY	= (float) Window.getHeight() / 9f;

	private void setupIBO() {
		int[] indices = new int[IBO_TOTAL_BUFFER_LENGTH];
		int iOffs = 0;
		for (int i = 0; i < indices.length; i += 6) {
			indices[i] = iOffs + 0;
			indices[i + 1] = iOffs + 1;
			indices[i + 2] = iOffs + 2;
			indices[i + 3] = iOffs + 2;
			indices[i + 4] = iOffs + 3;
			indices[i + 5] = iOffs + 0;
			iOffs += 4;
		}
		ibo = new IndexBuffer(indices);
	}

	private FloatBuffer mappedBuffer;

	public void begin() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		mappedBuffer = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY).asFloatBuffer();
	}

	public void end() {
		mappedBuffer.flip();
		GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private float getTextureSlot(final Texture tex) {
		if (tex == null)
			return 0.0f;
		float ts = 0.0f;
		final int texture = tex.getTextureID();
		boolean found = false;
		for (int i = 0; i < textureSlots.size(); i++) {
			if (textureSlots.get(i) == texture) {
				ts = (float) (i + 1);
				found = true;
				break;
			}
		}
		if (!found) {
			if (textureSlots.size() >= RENDERER_MAX_TEXTURE_UNITS) {
				end();
				render();
				begin();
			}
			textureSlots.add(texture);
			ts = (float) (textureSlots.size());
		}
		return ts;
	}

	public void drawText(String str, float x, float y, VFont font) {
		Texture fontTexture = font.getFontTexture();
		float ts = getTextureSlot(fontTexture);
		float currentX = x;
		for (char ch : str.toCharArray()) {
			if (!VFont.isSupported(ch))
				throw new vgl.core.exception.VGLFontException("Character >> " + ch + " is not supported");
			Color c = Color.DARK_BLUE;
			Glyph g = font.getGlyph(ch);

			float x0 = currentX;
			float y0 = y;
			float x1 = currentX + g.getAdvance() / scaleX;
			float y1 = y + font.getCharHeight() / scaleY;

			float u0 = g.getU0();
			float v0 = g.getV0();
			float u1 = g.getU1();
			float v1 = g.getV1();

			currentX += g.getAdvance() / scaleX;

			/**
			 * VBO LAYOUT
			 */
			// Vertex
			mappedBuffer.put(x0);
			mappedBuffer.put(y0);
			mappedBuffer.put(0f);
			// Color
			mappedBuffer.put(c.getRed());
			mappedBuffer.put(c.getGreen());
			mappedBuffer.put(c.getBlue());
			mappedBuffer.put(c.getAlpha());
			// UVs
			mappedBuffer.put(u0);
			mappedBuffer.put(v0);
			// TID
			mappedBuffer.put(ts);

			// Vertex
			mappedBuffer.put(x0);
			mappedBuffer.put(y1);
			mappedBuffer.put(0f);
			// Color
			mappedBuffer.put(c.getRed());
			mappedBuffer.put(c.getGreen());
			mappedBuffer.put(c.getBlue());
			mappedBuffer.put(c.getAlpha());
			// UVs
			mappedBuffer.put(u0);
			mappedBuffer.put(v1);
			// TID
			mappedBuffer.put(ts);

			// Vertex
			mappedBuffer.put(x1);
			mappedBuffer.put(y1);
			mappedBuffer.put(0f);
			// Color
			mappedBuffer.put(c.getRed());
			mappedBuffer.put(c.getGreen());
			mappedBuffer.put(c.getBlue());
			mappedBuffer.put(c.getAlpha());
			// UVs
			mappedBuffer.put(u1);
			mappedBuffer.put(v1);
			// TID
			mappedBuffer.put(ts);

			// Vertex
			mappedBuffer.put(x1);
			mappedBuffer.put(y0);
			mappedBuffer.put(0f);
			// Color
			mappedBuffer.put(c.getRed());
			mappedBuffer.put(c.getGreen());
			mappedBuffer.put(c.getBlue());
			mappedBuffer.put(c.getAlpha());
			// UVs
			mappedBuffer.put(u1);
			mappedBuffer.put(v0);
			// TID
			mappedBuffer.put(ts);

			indexCount += 6;
		}
	}

	private boolean validateRenderable(Renderable2D renderable) {
		if (renderable == null)
			return false;
		return true;
	}

	private void assignBuffer(final Renderable2D renderable, float x, float y, float width, float height,
	        Matrix4f transformation) {
		if (renderable == null)
			return;
		if (renderable instanceof ImageSprite) {
			if (transformation != null)
				bufferTransformed(renderable, x, y, width, height, transformation);
			else
				buffer(renderable, x, y, width, height);
		} else if (renderable instanceof ColoredSprite) {
			ColoredSprite cs = (ColoredSprite) renderable;
			if (cs.isGradientSprite()) {
				if (transformation != null)
					bufferGradientSpriteTransformed(cs, x, y, transformation);
				else
					bufferGradientSprite(cs, x, y);
			} else {
				if (transformation != null)
					bufferTransformed(renderable, x, y, width, height, transformation);
				else
					buffer(renderable, x, y, width, height);
			}
		}
		// TODO
		// if (renderable instanceof ImageSprite)
		// renderSprite(renderable,
		// x,
		// y,
		// renderable.getWidth() / scaleX,
		// renderable.getHeight() / scaleY,
		// transformation);
		// else {
		// if (((ColoredSprite) renderable).isGradientSprite()) {
		// renderGradientSprite((ColoredSprite) renderable, x, y);
		// } else
		// renderSprite(renderable,
		// x,
		// y,
		// renderable.getWidth(),
		// renderable.getHeight(),
		// transformation);
		// }
	}

	private void buffer(Renderable2D renderable, float x, float y, float width, float height) {
		float ts = 0.0f;
		final Vector2f[] uv = ImageSprite.defaultUVS();
		Color c = null;
		if (renderable instanceof ImageSprite) {
			ts = getTextureSlot(((ImageSprite) renderable).getTexture());
			c = Color.WHITE;
		}
		if (renderable instanceof ColoredSprite) {
			ColoredSprite cs = (ColoredSprite) renderable;
			c = cs.getColor();
		}
		/**
		 * VBO LAYOUT
		 */
		// Vertex
		mappedBuffer.put(x);
		mappedBuffer.put(y);
		mappedBuffer.put(0f);
		// Color -> RGBA
		mappedBuffer.put(c.getRed());
		mappedBuffer.put(c.getGreen());
		mappedBuffer.put(c.getBlue());
		mappedBuffer.put(c.getAlpha());
		// UVs
		mappedBuffer.put(uv[0].x);
		mappedBuffer.put(uv[0].y);
		// TID
		mappedBuffer.put(ts);
		// Vertex
		mappedBuffer.put(x);
		mappedBuffer.put(y + height);
		mappedBuffer.put(0f);
		// Color
		mappedBuffer.put(c.getRed());
		mappedBuffer.put(c.getGreen());
		mappedBuffer.put(c.getBlue());
		mappedBuffer.put(c.getAlpha());
		// UVs
		mappedBuffer.put(uv[1].x);
		mappedBuffer.put(uv[1].y);
		// TID
		mappedBuffer.put(ts);

		// Vertex
		mappedBuffer.put(x + width);
		mappedBuffer.put(y + height);
		mappedBuffer.put(0f);
		// Color
		mappedBuffer.put(c.getRed());
		mappedBuffer.put(c.getGreen());
		mappedBuffer.put(c.getBlue());
		mappedBuffer.put(c.getAlpha());
		// UVs
		mappedBuffer.put(uv[2].x);
		mappedBuffer.put(uv[2].y);
		// TID
		mappedBuffer.put(ts);

		// Vertex
		mappedBuffer.put(x + width);
		mappedBuffer.put(y);
		mappedBuffer.put(0f);
		// Color
		mappedBuffer.put(c.getRed());
		mappedBuffer.put(c.getGreen());
		mappedBuffer.put(c.getBlue());
		mappedBuffer.put(c.getAlpha());
		// UVs
		mappedBuffer.put(uv[3].x);
		mappedBuffer.put(uv[3].y);
		// TID
		mappedBuffer.put(ts);

		indexCount += 6;
	}

	private void bufferTransformed(Renderable2D renderable, float x, float y, float width, float height,
	        Matrix4f transformation) {
		float ts = 0.0f;
		final Vector2f[] uv = ImageSprite.defaultUVS();
		Color c = null;
		if (renderable instanceof ImageSprite) {
			ts = getTextureSlot(((ImageSprite) renderable).getTexture());
			c = Color.WHITE;
		}
		if (renderable instanceof ColoredSprite) {
			ColoredSprite cs = (ColoredSprite) renderable;
			c = cs.getColor();
		}
		/**
		 * VBO LAYOUT
		 */
		// Vertex
		Vector3f vertex = new Vector3f(x, y, 0f);
		vertex.multiply(transformation);
		mappedBuffer.put(vertex.x);
		mappedBuffer.put(vertex.y);
		mappedBuffer.put(0f);
		// Color
		mappedBuffer.put(c.getRed());
		mappedBuffer.put(c.getGreen());
		mappedBuffer.put(c.getBlue());
		mappedBuffer.put(c.getAlpha());
		// UVs
		mappedBuffer.put(uv[0].x);
		mappedBuffer.put(uv[0].y);
		// TID
		mappedBuffer.put(ts);
		// Vertex
		vertex = new Vector3f(x, y + height, 0f);
		vertex.multiply(transformation);
		mappedBuffer.put(vertex.x);
		mappedBuffer.put(vertex.y);
		mappedBuffer.put(0f);
		// Color
		mappedBuffer.put(c.getRed());
		mappedBuffer.put(c.getGreen());
		mappedBuffer.put(c.getBlue());
		mappedBuffer.put(c.getAlpha());
		// UVs
		mappedBuffer.put(uv[1].x);
		mappedBuffer.put(uv[1].y);
		// TID
		mappedBuffer.put(ts);

		// Vertex
		vertex = new Vector3f(x + width, y + height, 0f);
		vertex.multiply(transformation);
		mappedBuffer.put(vertex.x);
		mappedBuffer.put(vertex.y);
		mappedBuffer.put(0f);
		// Color
		mappedBuffer.put(c.getRed());
		mappedBuffer.put(c.getGreen());
		mappedBuffer.put(c.getBlue());
		mappedBuffer.put(c.getAlpha());
		// UVs
		mappedBuffer.put(uv[2].x);
		mappedBuffer.put(uv[2].y);
		// TID
		mappedBuffer.put(ts);

		// Vertex
		vertex = new Vector3f(x + width, y, 0f);
		vertex.multiply(transformation);
		mappedBuffer.put(vertex.x);
		mappedBuffer.put(vertex.y);
		mappedBuffer.put(0f);
		// Color
		mappedBuffer.put(c.getRed());
		mappedBuffer.put(c.getGreen());
		mappedBuffer.put(c.getBlue());
		mappedBuffer.put(c.getAlpha());
		// UVs
		mappedBuffer.put(uv[3].x);
		mappedBuffer.put(uv[3].y);
		// TID
		mappedBuffer.put(ts);

		indexCount += 6;
	}

	private void bufferGradientSpriteTransformed(ColoredSprite renderable, float x, float y, Matrix4f transformation) {
		Color[] grad = renderable.getGrad();
		boolean _doubleGradient = grad.length == 4;
		Vector2f[] uv = ImageSprite.defaultUVS();
		// Vertex -> Color -> UV -> TID
		// putVec(x, y);
		putVec3f(new Vector3f(x, y, 0f).multiply(transformation));
		putColor(grad[0]);
		putUVElement(uv[0]);
		mappedBuffer.put(0.0f);

		putVec3f(new Vector3f(x, y + renderable.getHeight(), 0f).multiply(transformation));
		if (_doubleGradient)
			putColor(grad[0]);
		else
			putColorAvg(grad[0], grad[1]);
		putUVElement(uv[1]);
		mappedBuffer.put(0.0f);

		putVec3f(new Vector3f(x + renderable.getWidth(), y + renderable.getHeight(), 0f).multiply(transformation));
		if (_doubleGradient)
			putColor(grad[2]);
		else
			putColor(grad[1]);
		putUVElement(uv[2]);
		mappedBuffer.put(0.0f);

		putVec3f(new Vector3f(x + renderable.getWidth(), y, 0f).multiply(transformation));
		if (_doubleGradient)
			putColor(grad[3]);
		else
			putColorAvg(grad[0], grad[1]);
		putUVElement(uv[3]);
		mappedBuffer.put(0.0f);

		indexCount += 6;
	}

	private void bufferGradientSprite(ColoredSprite renderable, float x, float y) {
		Color[] grad = renderable.getGrad();
		boolean _doubleGradient = grad.length == 4;
		Vector2f[] uv = ImageSprite.defaultUVS();
		// Vertex -> Color -> UV -> TID
		putVec(x, y);
		putColor(grad[0]);
		putUVElement(uv[0]);
		mappedBuffer.put(0.0f);

		putVec(x, y + renderable.getHeight());
		if (_doubleGradient)
			putColor(grad[0]);
		else
			putColorAvg(grad[0], grad[1]);
		putUVElement(uv[1]);
		mappedBuffer.put(0.0f);

		putVec(x + renderable.getWidth(), y + renderable.getHeight());
		if (_doubleGradient)
			putColor(grad[2]);
		else
			putColor(grad[1]);
		putUVElement(uv[2]);
		mappedBuffer.put(0.0f);

		putVec(x + renderable.getWidth(), y);
		if (_doubleGradient)
			putColor(grad[3]);
		else
			putColorAvg(grad[0], grad[1]);
		putUVElement(uv[3]);
		mappedBuffer.put(0.0f);

		indexCount += 6;
	}

	public void renderSprite(final Renderable2D renderable, final float x, final float y, final float width,
	        final float height, Matrix4f transformation) {
		if (validateRenderable(renderable))
			assignBuffer(renderable, x, y, width, height, transformation);
	}

	@Override
	public void renderSprite(Renderable2D renderable, float x, float y) {
		if (validateRenderable(renderable))
			assignBuffer(renderable, x, y, renderable.getWidth(), renderable.getHeight(), null);
	}

	@Override
	public void renderSprite(Renderable2D renderable, float x, float y, float width, float height) {
		if (validateRenderable(renderable))
			assignBuffer(renderable, x, y, width, height, null);
	}

	public void renderSprite(final Renderable2D renderable, float x, float y, Matrix4f transformation) {
		if (validateRenderable(renderable))
			assignBuffer(renderable, x, y, renderable.getWidth(), renderable.getHeight(), transformation);
	}

	public void renderSprite(final Renderable2D renderable, Vector2f pos) {
		if (validateRenderable(renderable))
			assignBuffer(renderable, pos.x, pos.y, renderable.getWidth(), renderable.getHeight(), null);
	}

	private void putVec3f(Vector3f vec) {
		mappedBuffer.put(vec.x).put(vec.y).put(vec.z);
	}

	private void putVec(float x, float y) {
		mappedBuffer.put(x).put(y).put(0f);
	}

	private void putColorAvg(Color c1, Color c2) {
		if (c1.getAlpha() < 1.0f || c2.getAlpha() < 1.0f)
			throw new vgl.core.exception.VGLRuntimeException("Gradient must be with an alpha value of 1");
		mappedBuffer.put(((c1.getRed() + c2.getRed()) / 2f)).put(((c1.getGreen() + c2.getGreen()) / 2f)).put(
		        ((c1.getBlue() + c2.getBlue()) / 2f)).put(1f);
	}

	private void putColor(Color c) {
		mappedBuffer.put(c.getRed()).put(c.getGreen()).put(c.getBlue()).put(c.getAlpha());
	}

	private void putUVElement(Vector2f uv) {
		mappedBuffer.put(uv.x).put(uv.y);
	}

	public void render() {
		for (int i = 0; i < textureSlots.size(); i++) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureSlots.get(i));
		}
		GL30.glBindVertexArray(vao);
		ibo.bind();
		GL11.glDrawElements(GL11.GL_TRIANGLES, indexCount, GLTypes.UNSIGNED_INT, 0);
		ibo.unbind();
		GL30.glBindVertexArray(0);
		indexCount = 0;
		textureSlots.clear();
	}
}
