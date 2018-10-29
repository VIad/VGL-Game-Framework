package vgl.maths.geom.shape2d;

import vgl.core.collision.Collider2D;
import vgl.maths.geom.Size2f;
import vgl.maths.geom.Size2i;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector2i;

public class RectInt implements IRect{

	public int				x, y, width, height;

	public RectInt() {
		this(0, 0, 0, 0);
	}

	public RectInt(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
	}

	public RectInt(RectInt r) {
		this(r.x, r.y, r.width, r.height);
	}

	public RectInt(int width, int height) {
		this(0, 0, width, height);
	}

	public RectInt(Vector2i pos) {
		this(pos.x, pos.y, 0, 0);
	}

	public RectInt(Size2i dim) {
		this(0, 0, dim.width, dim.height);
	}

	public void setBounds(RectInt r) {
		setBounds(r.x, r.y, r.width, r.height);
	}

	public void setBounds(int x, int y, int width, int height) {
		validate(width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	private void validate(int width2, int height2) {
		if (width2 < 0 || height2 < 0)
			throw new IllegalArgumentException("Either width or height is less than 0");
	}

	public Vector2f getLocation() {
		return new Vector2f(x, y);
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setCenter(Vector2i center) {
		setCenter(center.x, center.y);
	}
	
	public void setCenter(int x, int y) {
		this.x = x - width / 2;
		this.y = y - height / 2;		
	}

	public void setLocation(Vector2i pos) {
		this.setLocation(pos.x, pos.y);
	}

	public boolean contains(int X, int Y, int W, int H) {
		int w = this.width;
		int h = this.height;
		int x = this.x;
		int y = this.y;
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

	public boolean contains(int x, int y) {
		int w = this.width;
		int h = this.height;
		if (w < 0 || h < 0)
			return false;

		if (x < this.x || y < this.y)
			return false;

		w += this.x;
		h += this.y;
		return ((w < this.x || w > x) && (h < this.y || h > y));
	}

	public boolean intersects(RectInt other) {
		return intersects(other.x, other.y, other.width, other.height);
	}

	public Size2f getSize() {
		return new Size2f(width, height);
	}

	public Vector2f getCenter() {
		return new Vector2f(x + width / 2, y + height / 2);
	}

	public Polygon toPolygon() {
		return new Polygon(new Vector2f(x, y))
				              .addVertex(x + width, y)
				              .addVertex(x + width, y + height)
				              .addVertex(x, y + height);
				       
	}
	
	public static RectInt from(int x, int y, int width, int height) {
		return new RectInt(x,y,width,height);
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
		RectInt other = (RectInt) obj;
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
		return "RectInt [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}

	@Override
	public RectFloat bounds() {
		return new RectFloat(this);
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

	public static RectInt create(int f, int g, int h, int i) {
		return new RectInt(f, g, h, i);
	}

	@Override
	@SuppressWarnings("unchecked")
	public RectInt copy() {
		return new RectInt(this);
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

	public void translate(int x2, int y2) {
		this.x += x2;
		this.y += y2;
	}

	@Override
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


}
