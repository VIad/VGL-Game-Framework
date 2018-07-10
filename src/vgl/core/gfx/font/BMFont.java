package vgl.core.gfx.font;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLDebugMessageCallbackI;

import vgl.core.exception.VGLFontException;
import vgl.core.geom.Size2f;
import vgl.core.geom.Size2i;
import vgl.core.gfx.gl.Texture;
import vgl.core.gfx.render.RenderContext;
import vgl.core.internal.ProcessManager;
import vgl.main.VGL;
import vgl.maths.Maths;
import vgl.platform.io.FileDetails;
import vgl.tools.IResource;
import vgl.tools.ISpecifier;
import vgl.tools.async.UniContainer;
import vgl.tools.functional.callback.BinaryCallback;
import vgl.utils.FNTParser;


//TODO 
//Add support for non-alpha font loading
//Add support for kerning
public class BMFont implements IFont, IResource, ISpecifier<IResource.ResourceState> {

	private FontSpecifics.Info	info;
	private Common				common;

	private boolean				disposed;
	private ResourceState		state;

	public static class Common {
		public int lineHeight, base, scaleW, scaleH, pages, chars;

		public Common(int lineHeight, int base, int scaleW, int scaleH, int pages, int chars) {
			super();
			this.lineHeight = lineHeight;
			this.base = base;
			this.scaleW = scaleW;
			this.scaleH = scaleH;
			this.pages = pages;
			this.chars = chars;
		}

	}

	private Map<Integer, Glyph>		glyphs;
	private Map<Integer, Texture>	pages;

	BMFont(FNTParser parser) {
		this.pages = new HashMap<>();
		this.glyphs = new HashMap<>();
		this.state = ResourceState.UNAVAILABLE;
		initFontData(parser);
	}

	private void initFontData(FNTParser fntParser) {
		fntParser.doParse();
		info = new FontSpecifics.Info(
		        fntParser.getFontName(),
		        fntParser.getPxSize(),
		        fntParser.isBold(),
		        fntParser.isItalic(),
		        fntParser.getStretchH());
		common = new Common(
		        fntParser.getLineHeight(),
		        fntParser.getBase(),
		        fntParser.getTexW(),
		        fntParser.getTexH(),
		        fntParser.getPages(),
		        fntParser.getChars());
		fntParser.getGlyphs().stream().forEach(glyph -> this.glyphs.put(glyph.id, glyph));
	}

	public static void load(FileDetails file, BinaryCallback<BMFont, Throwable> result) {
		VGL.io.readString(file, string -> {
			FNTParser parser = new FNTParser(string);
			BMFont font = new BMFont(parser);
			font.specify(ResourceState.LOADING);
			final UniContainer<Integer> container = new UniContainer<>();
			container.put(0);
			parser.getPagesList().forEach(page -> {
				FileDetails pageTex = VGL.io.file(file.getParent().absolutePath() + "/" + page.getFile());
				VGL.io.readImage(pageTex, (image, error) -> {
					if (error != null) {
						result.invoke(null, error);
						return;
					}
					ProcessManager.get().runNextUpdate(() -> {
						container.put(container.get() + 1);
						font.pages.put(page.getId(), new Texture(image));
						if (container.get() == parser.getPages()) {
							font.specify(ResourceState.AVAILABLE);
							result.invoke(font, null);
						}
						image.releaseMemory();
					});
				});
			});
		});
	}

	public Map<Integer, Texture> getPages() {
		validate();
		return pages;
	}

	public Map<Integer, Glyph> getGlyphs() {
		validate();
		return glyphs;
	}

	private class SpecificsImpl implements FontSpecifics {

		@Override
		public boolean isCharSupported(char c) {
			return BMFont.this.glyphs.containsKey((int) c);
		}

		@Override
		public Size2f getTextDimensions(String text, RenderContext context) {
			Size2f result = new Size2f(0, getHeight());
			float x = 0;
			for (char c : text.toCharArray()) {
				if (c == '\n') {
					result.width = Maths.max(x, result.width);
					result.height += getHeight();
					x = 0;
					continue;
				}
				if (!isCharSupported(c))
					c = ' ';
				x += getAdvance(c);
			}
			result.width = Maths.max(result.width, x);
			result.width = result.width / context.user2Device().x;
			result.height = result.height / context.user2Device().y;
			return result;
		}

		@Override
		public int getPixelSize() {
			return info.size;
		}

		@Override
		public boolean isBold() {
			return info.bold;
		}

		@Override
		public boolean isItalic() {
			return info.italic;
		}

		@Override
		public Size2i getFontTextureDimensions() {
			return new Size2i(common.scaleW, common.scaleH);
		}

		@Override
		public Info getInfo() {
			return info;
		}

		@Override
		public float getWidth(String text, RenderContext context) {
			return getTextDimensions(text, context).width;
		}

		@Override
		public float getHeight(String text, RenderContext context) {
			return getTextDimensions(text, context).height;
		}

		@Override
		public float getWidth(char c, RenderContext context) {
			return glyphs.get((int) c).width / context.user2Device().x;
		}

		@Override
		public float getHeight(char c, RenderContext context) {
			return glyphs.get((int) c).height / context.user2Device().y;
		}

		@Override
		public float getHeight(RenderContext context) {
			return common.lineHeight / context.user2Device().y;
		}

		@Override
		public float getAdvance(char c, RenderContext context) {
			return glyphs.get((int) c).xadvance / context.user2Device().x;
		}

	}

	public FontSpecifics getFontSpecifics() {
		validate();
		return new SpecificsImpl();
	}

	@Override
	public void releaseMemory() {
		validate();
		disposed = true;
		for (Texture tex : pages.values())
			tex.releaseMemory();
		state = ResourceState.DISPOSED;
	}

	@Override
	public boolean isDisposed() {
		return disposed;
	}

	@Override
	public synchronized ResourceState getResourceState() {
		return state;
	}

	@Override
	public synchronized void specify(ResourceState t) {
		this.state = t;
	}

	@Override
	public Glyph getGlyph(int charCode) {
		validate();
		return glyphs.get(charCode);
	}

	@Override
	public Texture getFontTextureFor(Glyph character) {
		validate();
		return pages.get(character.page);
	}

}
