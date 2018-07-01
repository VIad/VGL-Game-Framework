package vgl.core.gfx.font;

import vgl.core.gfx.gl.Texture;

public interface IFont {

	char[] FONT_SUPPORTED_CHARACTERS = new char[95];

	Glyph getGlyph(char c);

	Texture getFontTexture();

	String getFontName();

	int getOriginalFontSize();

	int getCharHeight();
	
	public static boolean isSupported(char c) {
		return c >= 32 && c < 127;
	}

}
