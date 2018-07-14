package vgl.maths.geom;

public class Size2f {

	public float width, height;

	public Size2f(Size2f other) {
		this(other.width, other.height);
	}

	public Size2f() {
		this(0f, 0f);
	}

	public Size2f(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(height);
		result = prime * result + Float.floatToIntBits(width);
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
		Size2f other = (Size2f) obj;
		if (Float.floatToIntBits(height) != Float.floatToIntBits(other.height))
			return false;
		if (Float.floatToIntBits(width) != Float.floatToIntBits(other.width))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Size2f [width=" + width + ", height=" + height + "]";
	}

	public static Size2f identity() {
		return new Size2f(1f,1f);
	}
	
	
}
