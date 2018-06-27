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
//		return (x + w > this.x) && ()
		return true;
	}
	
}
