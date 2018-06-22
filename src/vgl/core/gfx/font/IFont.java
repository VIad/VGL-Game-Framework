package vgl.core.gfx.font;

import vgl.platform.gl.GLTexture;

public interface IFont {

	char[] FONT_SUPPORTED_CHARACTERS = new char[95];

	Glyph getGlyph(char c);

	GLTexture getFontTexture();

	String getFontName();

	int getOriginalFontSize();

	int getCharHeight();
	
	public static boolean isSupported(char c) {
		return c >= 32 && c < 127;
	}

}
