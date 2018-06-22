package vgl.desktop.gfx.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import vgl.core.gfx.font.Glyph;
import vgl.core.gfx.font.IFont;
import vgl.core.internal.Checks;
import vgl.desktop.gfx.Texture;
import vgl.platform.gl.GLTexture;

public class VFont implements IFont {

	static {
		for (char i = 32; i < 127; i++) {
			FONT_SUPPORTED_CHARACTERS[i - 32] = i;
		}
	}

	private GLTexture					fontTexture;
	private final Map<Character, Glyph>	fontMap;
	private int							originalFontSize;
	private int							charHeight;
	private String						fontName;

	public VFont(final Font f) {
		Checks.checkIfInitialized();
		this.fontName = f.getName();
		int buffImageW = 512;
		int buffImageH = 512;
		if (f.getSize() > 47) {
			buffImageW += 512;
			buffImageH += 512;
		}
		this.fontMap = new HashMap<>();
		BufferedImage atlas = new BufferedImage(buffImageW, buffImageH, BufferedImage.TYPE_INT_ARGB);
		this.initGlyphData(atlas, atlas.createGraphics(), f);
	}

	private void initGlyphData(BufferedImage atlas, Graphics2D g2d, final Font awtFont) {
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		FontMetrics fm = g2d.getFontMetrics(awtFont);
		g2d.setFont(awtFont);
		// g2d.setColor(Color.BLACK);
		// g2d.fillRect(0, 0, atlas.getWidth(), atlas.getHeight());
		g2d.setColor(Color.WHITE);
		final int atlasW = atlas.getWidth();
		final int atlasH = atlas.getHeight();
		final int fontHeight = fm.getHeight() + fm.getMaxDescent();
		float currentX = 0;
		float xOffset = 0;
		int currentRow = fontHeight;
		for (char c : FONT_SUPPORTED_CHARACTERS) {
			float advance = fm.charWidth(c);
			g2d.drawString("" + c, currentX, currentRow - fontHeight / 5);
			fontMap.put(c,
			        new Glyph(
			                atlasW,
			                atlasH,
			                advance,
			                currentX,
			                currentRow - fontHeight,
			                currentX + advance,
			                currentRow));
			// Glyph g = fontMap.get(c);
			// Rectangle rect = new Rectangle(
			// (int) (g.getU0() * atlasW),
			// (int) (g.getV0() * atlasH),
			// (int) (g.getU1() * atlasW) - (int) (g.getU0() * atlasW),
			// (int) (g.getV1() * atlasH) - (int) (g.getV0() * atlasH));
			// g2d.draw(rect);
			if (awtFont.getSize() > 50) {
				xOffset += 1.5f;
			} else
				xOffset += 2f;
			currentX += advance + xOffset;
			if (currentX >= atlasW - fm.getMaxAdvance() / 2f) {
				currentX = 0;
				currentRow += fontHeight;
				xOffset = 1;
			}
		}
		this.charHeight = fm.getHeight();
		this.originalFontSize = awtFont.getSize();
		this.fontTexture = new Texture(atlas);
	}

	public GLTexture getFontTexture() {
		return fontTexture;
	}

	public int getOriginalFontSize() {
		return originalFontSize;
	}

	public Glyph getGlyph(char c) {
		return fontMap.get(c);
	}

	public String getFontName() {
		return fontName;
	}

	public int getCharHeight() {
		return charHeight;
	}
}
