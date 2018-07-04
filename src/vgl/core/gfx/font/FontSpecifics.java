package vgl.core.gfx.font;

import vgl.core.geom.Size2i;

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
	
	Size2i getTextDimensions(String text);
	
	Size2i getFontTextureDimensions();
	
	int getWidth(String text);
	
	int getHeight(String text);
	
	int getWidth(char c);
	
	int getHeight(char c);
	
	int getHeight();
	
	int getAdvance(char c);
	
	int getPixelSize();
	
	boolean isBold();
	
	boolean isItalic();
}
