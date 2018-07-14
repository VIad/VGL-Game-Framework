package vgl.maths.geom.shape2d;

import java.io.Serializable;

import vgl.core.collision.Collider2D;
import vgl.maths.geom.Size2f;
import vgl.maths.vector.Vector2f;

public class RectFloat implements IRect, Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3078918919786101243L;

	public float				x, y, width, height;

	public RectFloat() {
		this(0f, 0f, 0f, 0f);
	}

	public RectFloat(float x, float y, float width, float height) {
		setBounds(x, y, width, height);
	}

	public RectFloat(RectFloat r) {
		this(r.x, r.y, r.width, r.height);
	}

	public RectFloat(float width, float height) {
		this(0, 0, width, height);
	}

	public RectFloat(Vector2f pos) {
		this(pos.x, pos.y, 0, 0);
	}

	public RectFloat(Size2f viewportSize) {
		this(0, 0, viewportSize.width, viewportSize.height);
	}

	public void setBounds(RectFloat r) {
		setBounds(r.x, r.y, r.width, r.height);
	}

	public void setBounds(float x, float y, float width, float height) {
		validate(width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	private void validate(float width2, float height2) {
		if (width2 < 0 || height2 < 0)
			throw new IllegalArgumentException("Either width or height is less than 0");
	}

	public Vector2f getLocation() {
		return new Vector2f(x, y);
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setCenter(Vector2f center) {
		setCenter(center.x, center.y);
	}
	
	public void setCenter(float x, float y) {
		this.x = x - width / 2;
		this.y = y - height / 2;		
	}

	public void setLocation(Vector2f pos) {
		this.setLocation(pos.x, pos.y);
	}

	public boolean contains(float X, float Y, float W, float H) {
		float w = this.width;
		float h = this.height;
		float x = this.x;
		float y = this.y;
		if (w < 0 || h < 0 || W < 0 || H < 0)
			return false;
		if (X < x || Y < y) {
			return false;
		}
		w += x;
		W += X;
		if (W <= X) {
			if (w >= x || W > w)
				return false;
		} else {
			if (w >= x && W > w)
				return false;
		}
		h += y;
		H += Y;
		if (H <= Y) {
			if (h >= y || H > h)
				return false;
		} else {
			if (h >= y && H > h)
				return false;
		}
		return true;
	}

	public boolean contains(float x, float y) {
		float w = this.width;
		float h = this.height;
		if (w < 0 || h < 0)
			return false;

		if (x < this.x || y < this.y)
			return false;

		w += this.x;
		h += this.y;
		return ((w < this.x || w > x) && (h < this.y || h > y));
	}

	public boolean intersects(RectFloat other) {
		return intersects(other.x, other.y, other.width, other.height);
	}

	public Size2f getSize() {
		return new Size2f(width, height);
	}

	public Vector2f getCenter() {
		return new Vector2f(x + width / 2, y + height / 2);
	}

	public Polygon toPolygon() {
		return new Polygon(new Vector2f(x, y)).addVertex(new Vector2f(x, y)).addVertex(
		        new Vector2f(x + width, y)).addVertex(new Vector2f(x + width, y + height)).addVertex(
		                new Vector2f(x, y + height));
	}

	public boolean intersects(float x, float y, float w, float h) {
		float tw = this.width;
		float th = this.height;
		float otherw = w;
		float otherh = h;
		if (otherw <= 0 || otherh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}

		float tx = this.x;
		float ty = this.y;
		float rx = x;
		float ry = y;
		otherw += rx;
		otherh += ry;
		tw += tx;
		th += ty;
		return ((otherw < rx || otherw > tx) && (otherh < ry || otherh > ty) && (tw < tx || tw > rx)
		        && (th < ty || th > ry));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(height);
		result = prime * result + Float.floatToIntBits(width);
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
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
		RectFloat other = (RectFloat) obj;
		if (Float.floatToIntBits(height) != Float.floatToIntBits(other.height))
			return false;
		if (Float.floatToIntBits(width) != Float.floatToIntBits(other.width))
			return false;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RectFloat [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}

	@Override
	public RectFloat bounds() {
		return this;
	}

	@Override
	public Vector2f relativeCenter() {
		return this.getCenter();
	}

	@Override
	public boolean contains(Vector2f point) {
		return this.contains(point);
	}

	@Override
	public boolean intersects(Shape2D other) {
		return Collider2D.checkShapeIntersection(this, other);
	}

	public static RectFloat create(float f, float g, float h, float i) {
		return new RectFloat(f, g, h, i);
	}

	@Override
	@SuppressWarnings("unchecked")
	public RectFloat copy() {
		return new RectFloat(this);
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public boolean intersects(IRect other) {
		return intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight());
	}

}
