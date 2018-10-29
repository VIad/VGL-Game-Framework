package vgl.utils;

import java.util.ArrayList;
import java.util.List;

import vgl.core.gfx.font.Glyph;

//TODO Font kerning
public class FNTParser {

	private String		fntFile;

	private String		fontName;

	private int			pxSize;

	private int			stretchH, lineHeight, base, texW, texH, pages, chars;

	private boolean		bold, italic;

	private List<Page>	pagesList;
	private List<Glyph>	glyphs;
	
	

	public String getFntFile() {
		return fntFile;
	}

	public String getFontName() {
		return fontName;
	}

	public int getPxSize() {
		return pxSize;
	}

	public int getStretchH() {
		return stretchH;
	}

	public int getLineHeight() {
		return lineHeight;
	}

	public int getBase() {
		return base;
	}

	public int getTexW() {
		return texW;
	}

	public int getTexH() {
		return texH;
	}

	public int getPages() {
		return pages;
	}

	public int getChars() {
		return chars;
	}

	public boolean isBold() {
		return bold;
	}

	public boolean isItalic() {
		return italic;
	}

	public List<Page> getPagesList() {
		return pagesList;
	}

	public List<Glyph> getGlyphs() {
		return glyphs;
	}

	public static class Page {
		int		id;
		String	file;

		public String getFile() {
			return file;
		}

		public int getId() {
			return id;
		}

		public Page(int id, String file) {
			super();
			this.id = id;
			this.file = file;
		}

		@Override
		public String toString() {
			return "Page [id=" + id + ", file=" + file + "]";
		}

	}

	public FNTParser(String fntFile) {
		this.fntFile = fntFile;
		this.pagesList = new ArrayList<>();
		this.glyphs = new ArrayList<>();
	
	}

	public void doParse() {
		String[] lines = fntFile.split("\\n");
		String[] fLine = lines[0].split("\\s");
		// this.fontName = fLine[1].substring(5).replaceAll("\"", "");
		this.fontName = from(lines[0], "face=", "size").replaceAll("\"", "").trim();
		this.pxSize = Integer.parseInt(from(lines[0], "size=", "bold").trim());
		this.bold = Integer.parseInt(from(lines[0], "bold=", "itali").trim()) == 1;
		this.italic = Integer.parseInt(from(lines[0], "italic=", "charse").trim()) == 1;
		this.stretchH = Integer.parseInt(from(lines[0], "stretchH=", "smooth").trim());
		this.lineHeight = Integer.parseInt(from(lines[1], "lineHeight=", "base").trim());
		this.base = Integer.parseInt(from(lines[1], "base=", "scaleW").trim());
		this.texW = Integer.parseInt(from(lines[1], "scaleW=", "scaleH").trim());
		this.texH = Integer.parseInt(from(lines[1], "scaleH=", "pages").trim());
		this.pages = Integer.parseInt(from(lines[1], "pages=", "packed").trim());

		for (int i = 2; i < lines.length; i++) {
			if (lines[i].startsWith("page")) {
				parseAndAddPage(lines[i]);
			}
			if (lines[i].startsWith("chars"))
				chars = Integer.parseInt(from(lines[i], "count=", "___EOF").trim());
			if (lines[i].startsWith("char") && !lines[i].startsWith("chars")) {
				parseAndAddGlyph(lines[i]);
			}
		}
//		pagesList.forEach(System.out::println);
//		glyphs.forEach(System.out::println);
//		System.out.println(chars);
//		System.out.println(texW);
//		System.out.println(texH);
//		System.out.println(fontName);
//		System.out.println(pxSize);
//		System.out.println(bold);
//		System.out.println(italic);
//		System.out.println(stretchH);
	}

	private void parseAndAddGlyph(String string) {
		glyphs.add(new Glyph(
		        parse(string, "id=", "x="),
		        parse(string, "x=", "y="),
		        parse(string, "y=", "width="),
		        parse(string, "width=", "height="),
		        parse(string, "height=", "xoffset"),
		        parse(string, "xoffset=", "yoffset="),
		        parse(string, "yoffset=", "xadvance="),
		        parse(string, "xadvance=", "page="),
		        parse(string, "page=", "chnl=")));
	}

	private void parseAndAddPage(String string) {
		pagesList.add(new Page(
		        Integer.parseInt(from(string, "id=", "file").trim()),
		        from(string, "file=", "___EOF").replaceAll("\"", "").trim()));
	}

	private static int parse(String str, String from, String to) {
		return Integer.parseInt(from(str, from, to == null ? "___EOF" : to).trim());
	}

	private static String from(String line, String from, String term) {
		if (term.equals("___EOF"))
			return line.substring(line.indexOf(from) + from.length(), line.length());
		return line.substring(line.indexOf(from) + from.length(), line.indexOf(term));
	}


}
