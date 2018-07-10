package vgl.core.gfx.font;

import vgl.core.gfx.gl.Texture;

public interface IFont {
	
	Glyph getGlyph(int charCode);

	FontSpecifics getFontSpecifics();

	Texture getFontTextureFor(Glyph character);
	
}
