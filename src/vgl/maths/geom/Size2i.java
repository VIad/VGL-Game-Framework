package vgl.maths.geom;

public class Size2i {

	public int width, height;

	public Size2i(Size2i other) {
		this(other.width, other.height);
	}

	public Size2i() {
		this(0, 0);
	}

	public Size2i(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
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
		Size2i other = (Size2i) obj;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Size2i [width=" + width + ", height=" + height + "]";
	}

}
