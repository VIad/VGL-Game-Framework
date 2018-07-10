package vgl.core.geom;

import java.io.Serializable;

import vgl.maths.vector.Vector2f;

public class RectFloat implements Serializable {

	public float x, y, width, height;

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
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Vector2f getLocation() {
		return new Vector2f(x, y);
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
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

}
