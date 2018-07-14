package vgl.core.gfx.font;

import vgl.core.annotation.VGLInternal;
import vgl.core.gfx.render.RenderContext;
import vgl.maths.geom.Size2f;
import vgl.maths.geom.Size2i;

public interface FontSpecifics {

	public static class Info {
		public String	name;
		public int		size;
		public boolean	bold, italic;
		public int		stretchH;

		public Info(String name, int size, boolean bold, boolean italic, int stretchH) {
			super();
			this.name = name;
			this.size = size;
			this.bold = bold;
			this.italic = italic;
			this.stretchH = stretchH;
		}
	}

	FontSpecifics.Info getInfo();

	boolean isCharSupported(char c);

	default Size2i getTextDimensions(String text) {
		Size2f contextual = getTextDimensions(text, RenderContext.UNSPECIFIED);
		return new Size2i((int) contextual.width, (int) contextual.height);
	}

	Size2f getTextDimensions(String text, RenderContext context);

	Size2i getFontTextureDimensions();


	float getWidth(String text, RenderContext context);
	
	default int getWidth(String text) {
		return (int) getWidth(text, RenderContext.UNSPECIFIED);
	}

	float getHeight(String text, RenderContext context);
	
	default int getHeight(String text) {
		return (int) getHeight(text, RenderContext.UNSPECIFIED);
	}

	float getWidth(char c, RenderContext context);

	default int getWidth(char c) {
		return (int) getWidth(c, RenderContext.UNSPECIFIED);
	}

	float getHeight(char c, RenderContext context);
	
	default int getHeight(char c) {
		return (int) getHeight(c, RenderContext.UNSPECIFIED);
	}


	default int getHeight() {
		return (int) getHeight(RenderContext.UNSPECIFIED);
	}

	@VGLInternal
	@Deprecated
	float getHeight(RenderContext context);

	default int getAdvance(char c) {
		return (int) getAdvance(c, RenderContext.UNSPECIFIED);
	}

	float getAdvance(char c, RenderContext context);

	int getPixelSize();

	boolean isBold();

	boolean isItalic();
}
