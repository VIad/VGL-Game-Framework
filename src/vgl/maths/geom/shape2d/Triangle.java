package vgl.maths.geom.shape2d;

import vgl.maths.vector.Vector2f;

public class Triangle implements Shape2D{

	private Vector2f a, b, c;

	public Triangle(Vector2f a, Vector2f b, Vector2f c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public Triangle(Triangle other) {
		this(other.a.copy(), other.b.copy(), other.c.copy());
	}

	public Triangle(float x0, float y0, float x1, float y1, float x2, float y2) {
		this(new Vector2f(x0, y0), new Vector2f(x1, y1), new Vector2f(x2, y2));
	}
	
	public Vector2f getA() {
		return a;
	}
	
	public Vector2f getB() {
		return b;
	}
	
	public Vector2f getC() {
		return c;
	}
	
	public Polygon toPolygon() {
		return new Polygon(a.copy())
				.addVertex(b.copy())
				.addVertex(c.copy());
	}
	
	public Triangle translateTo(float x, float y) {
		RectFloat bounds = bounds();
 		a.add(-bounds.x + x,-bounds.y +  y);
		b.add(-bounds.x + x,-bounds.y +  y);
		c.add(-bounds.x + x,-bounds.y +  y);
		return this;
	}
	
	public boolean contains(Vector2f point) { 
		return toPolygon().contains(point);
	}
	
	public RectFloat bounds() {
		return toPolygon().bounds();
	}

	@Override
	public String toString() {
		return "Triangle [a=" + a + ", b=" + b + ", c=" + c + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result + ((c == null) ? 0 : c.hashCode());
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
		Triangle other = (Triangle) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		if (c == null) {
			if (other.c != null)
				return false;
		} else if (!c.equals(other.c))
			return false;
		return true;
	}

	@Override
	public Vector2f relativeCenter() {
		return bounds().getCenter();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Triangle copy() {
		return new Triangle(this);
	}

	
}
