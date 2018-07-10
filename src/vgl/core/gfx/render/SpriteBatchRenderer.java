package vgl.core.gfx.render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWJoystickCallbackI;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import vgl.core.annotation.UnusedParameter;
import vgl.core.buffers.MemoryBufferFloat;
import vgl.core.geom.Size2i;
import vgl.core.gfx.Color;
import vgl.core.gfx.font.FontSpecifics;
import vgl.core.gfx.font.Glyph;
import vgl.core.gfx.font.IFont;
import vgl.core.gfx.gl.GPUBuffer;
import vgl.core.gfx.gl.IndexBuffer;
import vgl.core.gfx.gl.Texture;
import vgl.core.gfx.renderable.ColoredSprite;
import vgl.core.gfx.renderable.ImageSprite;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.main.VGL;
import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;
import vgl.platform.gl.GLBufferUsage;
import vgl.platform.gl.GLPrimitiveMode;
import vgl.platform.gl.Primitive;

final public class SpriteBatchRenderer implements IRenderer2D{

	final private static int	RENDERER_MAX_TEXTURE_UNITS	= 8;
	final private int			BUFFER_BYTE_SIZE;
	final private int			MAX_SPRITES;
	final private int           VERTEX_SIZE;
	final private int			IBO_LENGTH;
	
	private IRenderer2D.OverflowPolicy overflowPolicy = OverflowPolicy.UNSPECIFIED;
	
	public final static GPUBuffer.Layout STD_BATCH_LAYOUT = new GPUBuffer.Layout()
			                                                             .push(Primitive.FLOAT, 3)
			                                                             .push(Primitive.FLOAT, 4)
			                                                             .push(Primitive.FLOAT, 2)
			                                                             .push(Primitive.FLOAT, 1);

	final private List<Integer>	textureSlots;

	private int					vao;
	private GPUBuffer			renderer_gpuBuffer;
	private MemoryBufferFloat	local;

	private IndexBuffer			ibo;
	private int indexCount;

	public SpriteBatchRenderer(int maxSprites, GPUBuffer.Layout layout) {
		MAX_SPRITES = maxSprites;
		BUFFER_BYTE_SIZE = maxSprites * 4 * layout.getVertexSizeBytes();
		VERTEX_SIZE = layout.getVertexSizeBytes();
		IBO_LENGTH = maxSprites * 6;
		textureSlots = new ArrayList<>();
		vao = VGL.api_gfx.glGenVertexArray();
		VGL.api_gfx.glBindVertexArray(vao);
		renderer_gpuBuffer = new GPUBuffer(GLBufferUsage.DYNAMIC_DRAW);
		renderer_gpuBuffer.bind();
		renderer_gpuBuffer.resize(BUFFER_BYTE_SIZE);
		renderer_gpuBuffer.setLayout(layout);
		renderer_gpuBuffer.unbind();
		VGL.api_gfx.glBindVertexArray(0);
		this.local = new MemoryBufferFloat(BUFFER_BYTE_SIZE / 4);
		setupIndexBuffer();
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
	
	private void setupIndexBuffer() {
		int[] indices = new int[IBO_LENGTH];
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

	public void begin() {

	}

	public void end() {
		local.flip();
		renderer_gpuBuffer.setData(local.getBuffer(), 0);
		renderer_gpuBuffer.unbind();
	}
	
	public void render() {
		for(int i = 0; i < textureSlots.size(); i++) {
			Texture.setActiveTextureUnit(i);
			VGL.api_gfx.glBindTexture(GL11.GL_TEXTURE_2D, textureSlots.get(i));
		}
		VGL.api_gfx.glBindVertexArray(vao);
		ibo.bind();
		VGL.api_gfx.glDrawElements(GLPrimitiveMode.TRIANGLES, indexCount, Primitive.UNSIGNED_INT.toGLType(), 0);
		ibo.unbind();
		indexCount = 0;
		textureSlots.clear();
		VGL.api_gfx.glBindVertexArray(0);
	}

	private float scaleX, scaleY;

	@Override
	public void setScaling(float projWidth, float projHeight) {
		this.scaleX = (float) VGL.display.getWidth() * 1.0f / projWidth;
		this.scaleY = (float) VGL.display.getHeight() * 1.0f / projHeight;
	}
	
	@Override
	public IRenderer2D draw(Renderable2D renderable, float x, float y, float width, float height,
	        @UnusedParameter(reason = "Not yet implemented") Matrix4f transformation) {
		checkOverflow(40);
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
		local.put(x);
		local.put(y);
		local.put(0f);
		// Color -> RGBA
		local.put(c.getRed());
		local.put(c.getGreen());
		local.put(c.getBlue());
		local.put(c.getAlpha());
		// UVs
		local.put(uv[0].x);
		local.put(uv[0].y);
		// TID
		local.put(ts);
		// Vertex
		local.put(x);
		local.put(y + height);
		local.put(0f);
		// Color
		local.put(c.getRed());
		local.put(c.getGreen());
		local.put(c.getBlue());
		local.put(c.getAlpha());
		// UVs
		local.put(uv[1].x);
		local.put(uv[1].y);
		// TID
		local.put(ts);

		// Vertex
		local.put(x + width);
		local.put(y + height);
		local.put(0f);
		// Color
		local.put(c.getRed());
		local.put(c.getGreen());
		local.put(c.getBlue());
		local.put(c.getAlpha());
		// UVs
		local.put(uv[2].x);
		local.put(uv[2].y);
		// TID
		local.put(ts);

		// Vertex
		local.put(x + width);
		local.put(y);
		local.put(0f);
		// Color
		local.put(c.getRed());
		local.put(c.getGreen());
		local.put(c.getBlue());
		local.put(c.getAlpha());
		// UVs
		local.put(uv[3].x);
		local.put(uv[3].y);
		// TID
		local.put(ts);

		indexCount += 6;
		return this;
	}

	public IRenderer2D drawText(String str, float x, float y, IFont font) {
		checkOverflow(str.length() * VERTEX_SIZE);
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
//				if (glyph.page == pages.getKey()) {
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
					local.put(x0);
					local.put(y0);
					local.put(0f);
					// Color
					local.put(c.getRed());
					local.put(c.getGreen());
					local.put(c.getBlue());
					local.put(c.getAlpha());
					// UVs
					local.put(u0);
					local.put(v0);
					// TID
					local.put(ts);

					// Vertex
					local.put(x0);
					local.put(y1);
					local.put(0f);
					// Color
					local.put(c.getRed());
					local.put(c.getGreen());
					local.put(c.getBlue());
					local.put(c.getAlpha());
					// UVs
					local.put(u0);
					local.put(v1);
					// TID
					local.put(ts);

					// Vertex
					local.put(x1);
					local.put(y1);
					local.put(0f);
					// Color
					local.put(c.getRed());
					local.put(c.getGreen());
					local.put(c.getBlue());
					local.put(c.getAlpha());
					// UVs
					local.put(u1);
					local.put(v1);
					// TID
					local.put(ts);

					// Vertex
					local.put(x1);
					local.put(y0);
					local.put(0f);
					// Color
					local.put(c.getRed());
					local.put(c.getGreen());
					local.put(c.getBlue());
					local.put(c.getAlpha());
					// UVs
					local.put(u1);
					local.put(v0);
					// TID
					local.put(ts);

					indexCount += 6;
//				}
			}
			return this;
	}

	@Override
	public void dispose() {
		ibo.dispose();
		VGL.api_gfx.glDeleteVertexArrays(vao);
		local.getBuffer().free();
		local = null;
		renderer_gpuBuffer.dispose();
	}
	
	private void putColor(Color c) {
		local.put(c.getRed()).put(c.getGreen()).put(c.getBlue()).put(c.getAlpha());
	}

	private void putUVElement(Vector2f uv) {
		local.put(uv.x).put(uv.y);
	}
	
	private void putVec(float x, float y) {
		local.put(x).put(y).put(0f);
	}

	private void checkOverflow(int floats) {
		if (local.pointer() + floats >= local.capacity())
			if (this.overflowPolicy == OverflowPolicy.DO_RENDER) {
				end();
				render();
				begin();
			}
	}
	
	@Override
	public IRenderer2D drawLine(float x0, float y0, float x1, float y1, float thiccness, Color color) {
		checkOverflow(40);
		Vector2f lineNormal = new Vector2f(y1 - y0, -(x1 - x0)).normalize().multiply(thiccness);
		float ts = 0.0f;
		final Vector2f[] uv = ImageSprite.defaultUVS();
		// gpuDirect.put(x0 + lineNormal.x).put(y0 + lineNormal.y).put(0.0f);
		putVec(x0 + lineNormal.x, y0 + lineNormal.y);
		putColor(color);
		putUVElement(uv[0]);
		local.put(ts);

		putVec(x1 + lineNormal.x, y1 + lineNormal.y);
		putColor(color);
		putUVElement(uv[1]);
		local.put(ts);

		putVec(x1 - lineNormal.x, y1 - lineNormal.y);
		putColor(color);
		putUVElement(uv[2]);
		local.put(ts);

		putVec(x0 - lineNormal.x, y0 - lineNormal.y);
		putColor(color);
		putUVElement(uv[3]);
		local.put(ts);

		indexCount += 6;
		return this;
	}

	@Override
	public IRenderer2D usingOverflowPolicy(OverflowPolicy policy) {
		this.overflowPolicy = policy;
		return this;
	}

}
