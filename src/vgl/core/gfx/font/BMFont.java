package vgl.core.gfx.font;

import java.util.HashMap;
import java.util.Map;

import vgl.core.exception.VGLFontException;
import vgl.core.geom.Size2i;
import vgl.core.gfx.gl.Texture;
import vgl.core.internal.ProcessManager;
import vgl.main.VGL;
import vgl.maths.Maths;
import vgl.platform.io.FileDetails;
import vgl.tools.IResource;
import vgl.tools.ISpecifier;
import vgl.tools.async.UniContainer;
import vgl.tools.functional.callback.BinaryCallback;
import vgl.utils.FNTParser;

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
						Texture tex = null;
						font.pages.put(page.getId(), tex = new Texture(image));
						VGL.logger.error("Loaded >> " + pageTex + " for page :: " + page);
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
		return pages;
	}

	public Map<Integer, Glyph> getGlyphs() {
		return glyphs;
	}

	private class SpecificsImpl implements FontSpecifics {

		@Override
		public boolean isCharSupported(char c) {
			return BMFont.this.glyphs.containsKey((int) c);
		}

		@Override
		public Size2i getTextDimensions(String text) {
			Size2i result = new Size2i(0, getHeight());
			int x = 0;
			for (char c : text.toCharArray()) {
				if (isCharSupported(c))
					throw new VGLFontException("Character [" + c + "] is not supported by this instance of BMFont");
				if (c == '\n') {
					result.width = Maths.max(x, result.width);
					x = 0;
					continue;
				}
				x += getAdvance(c);
			}
			result.width = Maths.max(result.width, x);
			return result;
		}

		@Override
		public int getWidth(String text) {
			return getTextDimensions(text).width;
		}

		@Override
		public int getHeight(String text) {
			return getTextDimensions(text).height;
		}

		@Override
		public int getWidth(char c) {
			return glyphs.get((int) c).width;
		}

		@Override
		public int getHeight(char c) {
			return glyphs.get((int) c).height;
		}

		@Override
		public int getHeight() {
			return common.lineHeight;
		}

		@Override
		public int getAdvance(char c) {
			return glyphs.get((int) c).xadvance;
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

	}

	public FontSpecifics getFontSpecifics() {
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
		return glyphs.get(charCode);
	}

}
