package vgl.desktop.gfx.renderer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import vgl.core.gfx.Color;
import vgl.core.gfx.font.FontSpecifics;
import vgl.core.gfx.font.Glyph;
import vgl.core.gfx.font.IFont;
import vgl.core.gfx.gl.GPUBuffer;
import vgl.core.gfx.gl.IndexBuffer;
import vgl.core.gfx.gl.Texture;
import vgl.core.gfx.render.IRenderer2D;
import vgl.core.gfx.renderable.ColoredSprite;
import vgl.core.gfx.renderable.ImageSprite;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.core.internal.Checks;
import vgl.main.VGL;
import vgl.maths.geom.Size2i;
import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector3f;
import vgl.platform.gl.GLBufferUsage;
import vgl.platform.gl.Primitive;

final public class DirectGPUAccessRenderer2D implements IRenderer2D {

	private int				vao;
	private int				indexCount;
	private IndexBuffer		ibo;
	
	private IRenderer2D.OverflowPolicy overflowPolicy = OverflowPolicy.UNSPECIFIED;

	private List<Integer>	textureSlots;

	private final int		MAX_RENDERABLES;
	private final int		IBO_TOTAL_BUFFER_LENGTH;
	private int				RENDERABLE_SIZE;
	private int				VERTEX_ATTRIB_STRIDE;
	private int				RENDERER_BUFFER_SIZE;

	private GPUBuffer		vbo;

	/**
	 * TODO WEBGL shaders don't support more than 8 concurrently bound samplers
	 */
	public static final int	RENDERER_MAX_TEXTURE_UNITS	= 32;

	public DirectGPUAccessRenderer2D() {
		Checks.checkIfInitialized();
		this.MAX_RENDERABLES = 100000;
		this.IBO_TOTAL_BUFFER_LENGTH = 6 * MAX_RENDERABLES;
		this.textureSlots = new ArrayList<>();
		this.VERTEX_ATTRIB_STRIDE = (Vector3f.SIZE_BYTES + Color.SIZE_BYTES + Vector2f.SIZE_BYTES + Float.BYTES);
		this.RENDERABLE_SIZE = 4 * VERTEX_ATTRIB_STRIDE;
		this.RENDERER_BUFFER_SIZE = MAX_RENDERABLES * RENDERABLE_SIZE;
		init();
	}

	public DirectGPUAccessRenderer2D(int expectedBatches) {
		this.MAX_RENDERABLES = expectedBatches;
		this.IBO_TOTAL_BUFFER_LENGTH = expectedBatches * 6;
		this.textureSlots = new ArrayList<>();
		this.VERTEX_ATTRIB_STRIDE = (Vector3f.SIZE_BYTES + Color.SIZE_BYTES + Vector2f.SIZE_BYTES + Float.BYTES);
		this.RENDERABLE_SIZE = 4 * VERTEX_ATTRIB_STRIDE;
		this.RENDERER_BUFFER_SIZE = MAX_RENDERABLES * RENDERABLE_SIZE;
		init();
	}

	private void init() {
		this.indexCount = 0;
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		vbo = new GPUBuffer(GLBufferUsage.DYNAMIC_DRAW);
		vbo.bind();
		vbo.resize(RENDERER_BUFFER_SIZE);
		;
		vbo.setLayout(
		        new GPUBuffer.Layout()
		                     .push(Primitive.FLOAT, 3)
		                     .push(Primitive.FLOAT, 4)
		                     .push(Primitive.FLOAT, 2)
		                     .push(Primitive.FLOAT, 1));
		vbo.unbind();

		GL30.glBindVertexArray(0);

		setupIBO();
	}

	@Override
	public void setScaling(float projectionWidth, float projectionHeight) {
		this.projectionWidth = projectionWidth;
		this.projectionHeight = projectionHeight;

		this.scaleX = (float) VGL.display.getWidth() * 1.0f / projectionWidth;
		this.scaleY = (float) VGL.display.getHeight() * 1.0f / projectionHeight;
	}

	private float	projectionWidth	= 16f, projectionHeight = 9f;

	private float	scaleX		= (float) VGL.display.getWidth() / projectionWidth;
	private float	scaleY		= (float) VGL.display.getHeight() / projectionHeight;

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

	private java.nio.FloatBuffer gpuDirect;

	public void begin() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo.getId());
		gpuDirect = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY).asFloatBuffer();
	}

	public void end() {
		gpuDirect.flip();
		GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private float getTextureSlot(final Texture glTexture) {
		if (glTexture == null)
			return 0.0f;
		float ts = 0.0f;
		final int texture = glTexture.getID();
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

	public IRenderer2D drawText(String str, float x, float y, IFont font) {
		checkHasSpace(VERTEX_ATTRIB_STRIDE * str.length());
		final FontSpecifics fs = font.getFontSpecifics();
		float currentX = x;
		float currentY = y;
		for (char ch : str.toCharArray()) {
			Color c = Color.DARK_BLUE;

			if (ch == '\n') {
				currentY += fs.getHeight() / scaleY;
				currentX = x;
				continue;
			}

			Glyph glyph = font.getGlyph((int) ch);
			// if (glyph.page == pages.getKey()) {
			float ts = getTextureSlot(font.getFontTextureFor(glyph));
			float x0 = currentX + glyph.xoffset / scaleX;
			float y0 = currentY + glyph.yoffset / scaleY;
			float x1 = x0 + glyph.width / scaleX;

			float y1 = y0 + glyph.height / scaleY;

			float s0 = glyph.x;
			float t0 = glyph.y;
			float s1 = glyph.x + glyph.width;
			float t1 = glyph.y + glyph.height;
			Size2i fTex = fs.getFontTextureDimensions();
			float u0 = s0 / fTex.width;
			float v0 = t0 / fTex.height;
			float u1 = s1 / fTex.width;
			float v1 = t1 / fTex.height;

			currentX += glyph.xadvance / scaleX;
			/**
			 * VBO LAYOUT
			 */
			// Vertex
			gpuDirect.put(x0);
			gpuDirect.put(y0);
			gpuDirect.put(0f);
			// Color
			gpuDirect.put(c.getRed());
			gpuDirect.put(c.getGreen());
			gpuDirect.put(c.getBlue());
			gpuDirect.put(c.getAlpha());
			// UVs
			gpuDirect.put(u0);
			gpuDirect.put(v0);
			// TID
			gpuDirect.put(ts);

			// Vertex
			gpuDirect.put(x0);
			gpuDirect.put(y1);
			gpuDirect.put(0f);
			// Color
			gpuDirect.put(c.getRed());
			gpuDirect.put(c.getGreen());
			gpuDirect.put(c.getBlue());
			gpuDirect.put(c.getAlpha());
			// UVs
			gpuDirect.put(u0);
			gpuDirect.put(v1);
			// TID
			gpuDirect.put(ts);

			// Vertex
			gpuDirect.put(x1);
			gpuDirect.put(y1);
			gpuDirect.put(0f);
			// Color
			gpuDirect.put(c.getRed());
			gpuDirect.put(c.getGreen());
			gpuDirect.put(c.getBlue());
			gpuDirect.put(c.getAlpha());
			// UVs
			gpuDirect.put(u1);
			gpuDirect.put(v1);
			// TID
			gpuDirect.put(ts);

			// Vertex
			gpuDirect.put(x1);
			gpuDirect.put(y0);
			gpuDirect.put(0f);
			// Color
			gpuDirect.put(c.getRed());
			gpuDirect.put(c.getGreen());
			gpuDirect.put(c.getBlue());
			gpuDirect.put(c.getAlpha());
			// UVs
			gpuDirect.put(u1);
			gpuDirect.put(v0);
			// TID
			gpuDirect.put(ts);

			indexCount += 6;
			// }
		}
		return this;
	}

	private boolean validateRenderable(Renderable2D renderable) {
		if (renderable == null)
			return false;
		return true;
	}

	private void assignBuffer(final Renderable2D renderable, float x, float y, float width, float height,
	        Matrix4f transformation, boolean requestedSize) {
		checkHasSpace(40);
		if (renderable == null)
			return;
		if (renderable instanceof ImageSprite) {
			if (transformation != null)
				if (requestedSize)
					bufferTransformed(renderable, x, y, width, height, transformation);
				else
					bufferTransformed(renderable, x, y, width / scaleX, height / scaleY, transformation);
			else {
				if (requestedSize)
					buffer(renderable, x, y, width, height);
				else
					buffer(renderable, x, y, width / scaleX, height / scaleY);
			}
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
		gpuDirect.put(x);
		gpuDirect.put(y);
		gpuDirect.put(0f);
		// Color -> RGBA
		gpuDirect.put(c.getRed());
		gpuDirect.put(c.getGreen());
		gpuDirect.put(c.getBlue());
		gpuDirect.put(c.getAlpha());
		// UVs
		gpuDirect.put(uv[0].x);
		gpuDirect.put(uv[0].y);
		// TID
		gpuDirect.put(ts);
		// Vertex
		gpuDirect.put(x);
		gpuDirect.put(y + height);
		gpuDirect.put(0f);
		// Color
		gpuDirect.put(c.getRed());
		gpuDirect.put(c.getGreen());
		gpuDirect.put(c.getBlue());
		gpuDirect.put(c.getAlpha());
		// UVs
		gpuDirect.put(uv[1].x);
		gpuDirect.put(uv[1].y);
		// TID
		gpuDirect.put(ts);

		// Vertex
		gpuDirect.put(x + width);
		gpuDirect.put(y + height);
		gpuDirect.put(0f);
		// Color
		gpuDirect.put(c.getRed());
		gpuDirect.put(c.getGreen());
		gpuDirect.put(c.getBlue());
		gpuDirect.put(c.getAlpha());
		// UVs
		gpuDirect.put(uv[2].x);
		gpuDirect.put(uv[2].y);
		// TID
		gpuDirect.put(ts);

		// Vertex
		gpuDirect.put(x + width);
		gpuDirect.put(y);
		gpuDirect.put(0f);
		// Color
		gpuDirect.put(c.getRed());
		gpuDirect.put(c.getGreen());
		gpuDirect.put(c.getBlue());
		gpuDirect.put(c.getAlpha());
		// UVs
		gpuDirect.put(uv[3].x);
		gpuDirect.put(uv[3].y);
		// TID
		gpuDirect.put(ts);

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
		gpuDirect.put(vertex.x);
		gpuDirect.put(vertex.y);
		gpuDirect.put(0f);
		// Color
		gpuDirect.put(c.getRed());
		gpuDirect.put(c.getGreen());
		gpuDirect.put(c.getBlue());
		gpuDirect.put(c.getAlpha());
		// UVs
		gpuDirect.put(uv[0].x);
		gpuDirect.put(uv[0].y);
		// TID
		gpuDirect.put(ts);
		// Vertex
		vertex = new Vector3f(x, y + height, 0f);
		vertex.multiply(transformation);
		gpuDirect.put(vertex.x);
		gpuDirect.put(vertex.y);
		gpuDirect.put(0f);
		// Color
		gpuDirect.put(c.getRed());
		gpuDirect.put(c.getGreen());
		gpuDirect.put(c.getBlue());
		gpuDirect.put(c.getAlpha());
		// UVs
		gpuDirect.put(uv[1].x);
		gpuDirect.put(uv[1].y);
		// TID
		gpuDirect.put(ts);

		// Vertex
		vertex = new Vector3f(x + width, y + height, 0f);
		vertex.multiply(transformation);
		gpuDirect.put(vertex.x);
		gpuDirect.put(vertex.y);
		gpuDirect.put(0f);
		// Color
		gpuDirect.put(c.getRed());
		gpuDirect.put(c.getGreen());
		gpuDirect.put(c.getBlue());
		gpuDirect.put(c.getAlpha());
		// UVs
		gpuDirect.put(uv[2].x);
		gpuDirect.put(uv[2].y);
		// TID
		gpuDirect.put(ts);

		// Vertex
		vertex = new Vector3f(x + width, y, 0f);
		vertex.multiply(transformation);
		gpuDirect.put(vertex.x);
		gpuDirect.put(vertex.y);
		gpuDirect.put(0f);
		// Color
		gpuDirect.put(c.getRed());
		gpuDirect.put(c.getGreen());
		gpuDirect.put(c.getBlue());
		gpuDirect.put(c.getAlpha());
		// UVs
		gpuDirect.put(uv[3].x);
		gpuDirect.put(uv[3].y);
		// TID
		gpuDirect.put(ts);

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
		gpuDirect.put(0.0f);

		putVec3f(new Vector3f(x, y + renderable.getHeight(), 0f).multiply(transformation));
		if (_doubleGradient)
			putColor(grad[0]);
		else
			putColorAvg(grad[0], grad[1]);
		putUVElement(uv[1]);
		gpuDirect.put(0.0f);

		putVec3f(new Vector3f(x + renderable.getWidth(), y + renderable.getHeight(), 0f).multiply(transformation));
		if (_doubleGradient)
			putColor(grad[2]);
		else
			putColor(grad[1]);
		putUVElement(uv[2]);
		gpuDirect.put(0.0f);

		putVec3f(new Vector3f(x + renderable.getWidth(), y, 0f).multiply(transformation));
		if (_doubleGradient)
			putColor(grad[3]);
		else
			putColorAvg(grad[0], grad[1]);
		putUVElement(uv[3]);
		gpuDirect.put(0.0f);

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
		gpuDirect.put(0.0f);

		putVec(x, y + renderable.getHeight());
		if (_doubleGradient)
			putColor(grad[0]);
		else
			putColorAvg(grad[0], grad[1]);
		putUVElement(uv[1]);
		gpuDirect.put(0.0f);

		putVec(x + renderable.getWidth(), y + renderable.getHeight());
		if (_doubleGradient)
			putColor(grad[2]);
		else
			putColor(grad[1]);
		putUVElement(uv[2]);
		gpuDirect.put(0.0f);

		putVec(x + renderable.getWidth(), y);
		if (_doubleGradient)
			putColor(grad[3]);
		else
			putColorAvg(grad[0], grad[1]);
		putUVElement(uv[3]);
		gpuDirect.put(0.0f);

		indexCount += 6;
	}

	public IRenderer2D draw(final Renderable2D renderable, final float x, final float y, final float width,
	        final float height, Matrix4f transformation) {
		if (validateRenderable(renderable))
			assignBuffer(renderable, x, y, width, height, transformation, true);
		return this;
	}

	@Override
	public IRenderer2D draw(Renderable2D renderable, float x, float y) {
		if (validateRenderable(renderable))
			assignBuffer(renderable, x, y, renderable.getWidth(), renderable.getHeight(), null, false);
		return this;
	}

	@Override
	public IRenderer2D draw(Renderable2D renderable, float x, float y, float width, float height) {
		if (validateRenderable(renderable))
			assignBuffer(renderable, x, y, width, height, null, true);
		return this;
	}

	public IRenderer2D draw(final Renderable2D renderable, float x, float y, Matrix4f transformation) {
		if (validateRenderable(renderable))
			assignBuffer(renderable, x, y, renderable.getWidth(), renderable.getHeight(), transformation, false);
		return this;
	}

	public IRenderer2D draw(final Renderable2D renderable, Vector2f pos) {
		if (validateRenderable(renderable))
			assignBuffer(renderable, pos.x, pos.y, renderable.getWidth(), renderable.getHeight(), null, false);
		return this;
	}

	private void putVec3f(Vector3f vec) {
		gpuDirect.put(vec.x).put(vec.y).put(vec.z);
	}

	private void putVec(float x, float y) {
		gpuDirect.put(x).put(y).put(0f);
	}

	private void putColorAvg(Color c1, Color c2) {
		if (c1.getAlpha() < 1.0f || c2.getAlpha() < 1.0f)
			throw new vgl.core.exception.VGLRuntimeException("Gradient must be with an alpha value of 1");
		gpuDirect.put(((c1.getRed() + c2.getRed()) / 2f)).put(((c1.getGreen() + c2.getGreen()) / 2f)).put(
		        ((c1.getBlue() + c2.getBlue()) / 2f)).put(1f);
	}

	private void putColor(Color c) {
		gpuDirect.put(c.getRed()).put(c.getGreen()).put(c.getBlue()).put(c.getAlpha());
	}

	private void putUVElement(Vector2f uv) {
		gpuDirect.put(uv.x).put(uv.y);
	}

	public void render() {
		for (int i = 0; i < textureSlots.size(); i++) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + i);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureSlots.get(i));
		}
		GL30.glBindVertexArray(vao);
		ibo.bind();
		GL11.glDrawElements(GL11.GL_TRIANGLES, indexCount, Primitive.UNSIGNED_INT.toGLType(), 0);
		ibo.unbind();
		GL30.glBindVertexArray(0);
		indexCount = 0;
		textureSlots.clear();
	}

	@Override
	public void dispose() {
		vbo.dispose();
		gpuDirect = null;
	}

	private void checkHasSpace(int floats) {
		if(gpuDirect.position() + floats >= gpuDirect.capacity()) {
			if(overflowPolicy == OverflowPolicy.DO_RENDER) {
				end();
				render();
				begin();
			}
		}
	}
	
	@Override
	public IRenderer2D drawLine(float x0, float y0, float x1, float y1, float thiccness, Color color) {
		checkHasSpace(40);
		Vector2f lineNormal = new Vector2f(y1 - y0, -(x1 - x0)).normalize().multiply(thiccness);
		float ts = 0.0f;
		final Vector2f[] uv = ImageSprite.defaultUVS();
		// gpuDirect.put(x0 + lineNormal.x).put(y0 + lineNormal.y).put(0.0f);
		putVec(x0 + lineNormal.x, y0 + lineNormal.y);
		putColor(color);
		putUVElement(uv[0]);
		gpuDirect.put(ts);

		putVec(x1 + lineNormal.x, y1 + lineNormal.y);
		putColor(color);
		putUVElement(uv[1]);
		gpuDirect.put(ts);

		putVec(x1 - lineNormal.x, y1 - lineNormal.y);
		putColor(color);
		putUVElement(uv[2]);
		gpuDirect.put(ts);

		putVec(x0 - lineNormal.x, y0 - lineNormal.y);
		putColor(color);
		putUVElement(uv[3]);
		gpuDirect.put(ts);

		indexCount += 6;
		return this;
	}

	@Override
	public IRenderer2D usingOverflowPolicy(OverflowPolicy policy) {
		this.overflowPolicy = policy;
		return this;
	}

	@Override
	public IRenderer2D drawTriangle(float x0, float y0, float x1, float y1, float x2, float y2, Color color) {
		Vector2f[] uv = ImageSprite.defaultUVS();
		float ts = 0.0f;
		putVec(x0, y0);
		putColor(color);
		putUVElement(uv[0]);
		gpuDirect.put(ts);
		
		putVec(x1, y1);
		putColor(color);
		putUVElement(uv[1]);
		gpuDirect.put(ts);
		
		putVec(x2, y2);
		putColor(color);
		putUVElement(uv[2]);
		gpuDirect.put(ts);
		
		putVec(x1, y1);
		putColor(color);
		putUVElement(uv[3]);
		gpuDirect.put(ts);
		
		indexCount += 6;
		return this;
	}

}
