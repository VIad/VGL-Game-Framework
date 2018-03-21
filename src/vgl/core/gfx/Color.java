package vgl.core.gfx;

import vgl.maths.vector.Vector4f;

public final class Color implements java.io.Serializable, Comparable<Color> {

	/**
	 *
	 */
	private static final long serialVersionUID = -5968556428516551716L;
	/**
	 * <font color="ff0000"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="ff0000"><strong> 1234567890 </strong> </font>
	 */
	public static final Color RED = new Color(0xff0000);
	/**
	 * <font color="00ff00"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="00ff00"><strong> 1234567890 </strong> </font>
	 */
	public static final Color GREEN = new Color(0x00ff00);
	/**
	 * <font color="0000ff"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="0000ff"><strong> 1234567890 </strong> </font>
	 */
	public static final Color BLUE = new Color(0x0000ff);
	/**
	 * <font color="ffe119"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="ffe119"><strong> 1234567890 </strong> </font>
	 */
	public static final Color YELLOW = new Color(0xffe119);
	/**
	 * <font color="f58231"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="f58231"><strong> 1234567890 </strong> </font>
	 */
	public static final Color ORANGE = new Color(0xf58231);
	/**
	 * <font color="911eb4"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="911eb4"><strong> 1234567890 </strong> </font>
	 */
	public static final Color PURPLE = new Color(0x911eb4);
	/**
	 * <font color="46f0f0"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="46f0f0"><strong> 1234567890 </strong> </font>
	 */
	public static final Color CYAN = new Color(0x46f0f0);
	/**
	 * <font color="f032e6"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="f032e6"><strong> 1234567890 </strong> </font>
	 */
	public static final Color MAGENTA = new Color(0xf032e6);
	/**
	 * <font color="d2f53c"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="d2f53c"><strong> 1234567890 </strong> </font>
	 */
	public static final Color LIME = new Color(0xd2f53c);
	/**
	 * <font color="fabebe"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="fabebe"><strong> 1234567890 </strong> </font>
	 */
	public static final Color PINK = new Color(0xfabebe);
	/**
	 * <font color="008080"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="008080"><strong> 1234567890 </strong> </font>
	 */
	public static final Color TEAL = new Color(0x008080);
	/**
	 * <font color="e6beff"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="e6beff"><strong> 1234567890 </strong> </font>
	 */
	public static final Color LAVENDER = new Color(0xe6beff);
	/**
	 * <font color="aa6e28"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="aa6e28"><strong> 1234567890 </strong> </font>
	 */
	public static final Color BROWN = new Color(0xaa6e28);
	/**
	 * <font color="fffac8"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="fffac8"><strong> 1234567890 </strong> </font>
	 */
	public static final Color BEIGE = new Color(0xfffac8);
	/**
	 * <font color="808000"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="808000"><strong> 1234567890 </strong> </font>
	 */
	public static final Color OLIVE = new Color(0x808000);
	/**
	 * <font color="000080"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="000080"><strong> 1234567890 </strong> </font>
	 */
	public static final Color DARK_BLUE = new Color(0x000080);
	/**
	 * <font color="808080"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="808080"><strong> 1234567890 </strong> </font>
	 */
	public static final Color GREY = new Color(0x808080);
	/**
	 * <font color="FFFFFF"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="FFFFFF"><strong> 1234567890 </strong> </font>
	 */
	public static final Color WHITE = new Color(0xFFFFFF);
	/**
	 * <font color="000000"><strong> Color Sample </strong> </font>
	 * <br>
	 * <font color="000000"><strong> 1234567890 </strong> </font>
	 */
	public static final Color BLACK = new Color(0x000000);
	
	public static final Color TRANSPARENT = new Color(0xffffff00, true);

	public static final byte SIZE_BYTES = 4 * Float.BYTES;

	private float r, g, b, a;

	public Color(int r, int g, int b, int a) {
		checkIllegalColor(r, g, b, a);
		this.r = (float) (r / 255f);
		this.g = (float) (g / 255f);
		this.b = (float) (b / 255f);
		this.a = (float) (a / 255f);
	}

	public Color(int r, int g, int b) {
		checkIllegalColor(r, g, b);
		this.r = (float) (r / 255f);
		this.g = (float) (g / 255f);
		this.b = (float) (b / 255f);
		this.a = (float) 1f;
	}

	public Color() {
		this.r = this.g = this.b = 0f;
		this.a = 1f;
	}

	public Color(int rgb) {
		checkIllegalColor(rgb);
		short red = (short) ((rgb >> 16) & 0x0000ff);
		short green = (short) ((rgb >> 8) & 0x00ff);
		short blue = (short) ((rgb >> 0) & 0xff);
		this.r = (float) (red / 255f);
		this.g = (float) (green / 255f);
		this.b = (float) (blue / 255f);
		this.a = 1f;
	}

	public Color(int rgba, boolean hasAlpha) {
		if (hasAlpha) {

			checkIllegalColorAlpha(rgba);
			short alpha = (short) ((rgba >> 24) & 0x000000ff);
			short red = (short) ((rgba >> 16) & 0x0000ff);
			short green = (short) ((rgba >> 8) & 0x00ff);
			short blue = (short) ((rgba >> 0) & 0xff);
			this.r = (float) (red / 255f);
			this.g = (float) (green / 255f);
			this.b = (float) (blue / 255f);
			this.a = (float) (alpha / 255f);
		} else {
			int val = rgba | 0xff000000;
			checkIllegalColor(val);
			short red = (short) ((val >> 16) & 0x0000ff);
			short green = (short) ((val >> 8) & 0x00ff);
			short blue = (short) ((val >> 0) & 0xff);
			this.r = (float) (red / 255f);
			this.g = (float) (green / 255f);
			this.b = (float) (blue / 255f);
			this.a = 1f;
		}
	}

	public Color(float r, float g, float b) {
		checkIllegalColor(r, g, b);
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1f;
	}

	public Color(float r, float g, float b, float a) {
		checkIllegalColor(r, g, b, a);
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;

	}

	public Color(Color layerBackground) {
		this.a = layerBackground.a;
		this.r = layerBackground.r;
		this.g = layerBackground.g;
		this.b = layerBackground.b;
	}

	public static int toRGB(int r, int g, int b) {
		return r << 16 | g << 8 | b;
	}

	public static int toRGB(float r, float g, float b) {
		return toRGB((int) (r * 255f), (int) (g * 255f), (int) (b * 255f));
	}

	private static void checkIllegalColor(int rgb) {
		if (rgb < 0 && rgb > 0xffffff)
			throw new IllegalArgumentException("RGB Color code cannot be less than 0");
		checkIllegalColor(
				((rgb >> 16) & 0x0000ff),
				((rgb >> 8) & 0x00ff),
				((rgb >> 0) & 0xff),
				0xff
		);
	}

	private static void checkIllegalColorAlpha(int rgba) {
		checkIllegalColor(
				(short) ((rgba >> 16) & 0x0000ff),
				(short) ((rgba >> 8) & 0x00ff),
				(short) ((rgba >> 0) & 0xff),
				(short) ((rgba >> 24) & 0x000000ff)
		);
	}

	private static void checkIllegalColor(int r, int g, int b) {
		checkIllegalColor(r, g, b, 255);
	}

	private static void checkIllegalColor(int red, int green, int blue, int alpha) {
		String excMesage = null;
		if (alpha > 255 || alpha < 0) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			excMesage += " alpha : " + alpha;
		}
		if (red > 255 || red < 0) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			else
				excMesage += ", ";
			excMesage += " red : " + red;
		}
		if (green > 255 || green < 0) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			else
				excMesage += ", ";
			excMesage += " green : " + green;
		}
		if (blue > 255 || blue < 0) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			else
				excMesage += ", ";
			excMesage += " green : " + green;
		}
		if (excMesage != null)
			throw new IllegalArgumentException(excMesage);
	}

	private static void checkIllegalColor(float r, float g, float b) {
		checkIllegalColor(r, g, b, 1f);
	}

	private static void checkIllegalColor(float r, float g, float b, float a) {
		String excMesage = null;
		if (r > 1f || r < 0f) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			excMesage += "red : " + r;
		}
		if (g > 1f || g < 0f) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			else
				excMesage += ", ";
			excMesage += "green : " + g;
		}
		if (b > 1f || b < 0f) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			else
				excMesage += ", ";
			excMesage += "blue : " + b;
		}
		if (a > 1f || a < 0f) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			else
				excMesage += ", ";
			excMesage += "alpha : " + a;
		}
		if (excMesage != null)
			throw new IllegalArgumentException(excMesage);
	}

	private static final float BRIGHTNESS_ALTER_FACTOR = 0.2f;

	public Color brighter() {
		float r = vgl.maths.Maths.clamp(this.r + BRIGHTNESS_ALTER_FACTOR, 1f, 0f);
		float g = vgl.maths.Maths.clamp(this.g + BRIGHTNESS_ALTER_FACTOR, 1f, 0f);
		float b = vgl.maths.Maths.clamp(this.b + BRIGHTNESS_ALTER_FACTOR, 1f, 0f);
		return new Color(r, g, b);
	}

	public Color darker() {
		float r = vgl.maths.Maths.clamp(this.r - BRIGHTNESS_ALTER_FACTOR, 1f, 0f);
		float g = vgl.maths.Maths.clamp(this.g - BRIGHTNESS_ALTER_FACTOR, 1f, 0f);
		float b = vgl.maths.Maths.clamp(this.b - BRIGHTNESS_ALTER_FACTOR, 1f, 0f);
		return new Color(r, g, b);
	}

	public float getAlpha() {
		return a;
	}

	public float getRed() {
		return r;
	}

	public float getBlue() {
		return b;
	}

	public float getGreen() {
		return g;
	}

	public int getRGB() {
		int r = (int) (this.r * 255f);
		int g = (int) (this.g * 255f);
		int b = (int) (this.b * 255f);
		return ((r & 0xff) << 16)
				| ((g & 0xff) << 8)
				| ((b & 0xff) << 0);
	}

	public int getRGBA() {
		int a = (int) (this.a * 255f);
		int r = (int) (this.r * 255f);
		int g = (int) (this.g * 255f);
		int b = (int) (this.b * 255f);
		return ((a & 0xff) << 24)
				| ((r & 0xff) << 16)
				| ((g & 0xff) << 8)
				| ((b & 0xff) << 0);
	}

	public vgl.maths.vector.Vector4f toVector() {
		return new vgl.maths.vector.Vector4f(r, g, b, a);
	}
	
	public static boolean isValid(float r, float g, float b, float a) {		
		return r >= 0f && g >= 0f && b >= 0f && a >= 0f && r <= 1f && g <= 1f
				&& b <= 1f && a <= 1f;
	}
	
	public static boolean isValid(Vector4f color) {
		return isValid(color.x, color.y, color.z, color.w);
	}

	@Override
	public String toString() {
		return "Color [r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(a);
		result = prime * result + Float.floatToIntBits(b);
		result = prime * result + Float.floatToIntBits(g);
		result = prime * result + Float.floatToIntBits(r);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		if (Float.floatToIntBits(a) != Float.floatToIntBits(other.a))
			return false;
		if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b))
			return false;
		if (Float.floatToIntBits(g) != Float.floatToIntBits(other.g))
			return false;
		if (Float.floatToIntBits(r) != Float.floatToIntBits(other.r))
			return false;
		return true;
	}

	@Override
	public int compareTo(Color o) {
		int rgb = getRGB();
		int rgbo = o.getRGB();
		return rgb >= rgbo ?
				1 : rgb == rgbo ?
				0 : -1;
	}
}
