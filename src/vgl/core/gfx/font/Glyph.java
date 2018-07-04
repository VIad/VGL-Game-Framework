package vgl.core.gfx.font;

public class Glyph {

	public int id, x, y, width, height, xoffset, yoffset, xadvance, page;

	public Glyph(int id, int x, int y, int width, int height, int xoffset, int yoffset, int xadvance, int page) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xoffset = xoffset;
		this.yoffset = yoffset;
		this.xadvance = xadvance;
		this.page = page;
	}

	@Override
	public String toString() {
		return "Glyph [id=" + id + ", x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", xoffset="
		        + xoffset + ", yoffset=" + yoffset + ", xadvance=" + xadvance + ", page=" + page + "]";
	}
}
