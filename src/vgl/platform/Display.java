package vgl.platform;

public class Display {

	private String	title;
	private int		width, height;

	public Display(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
