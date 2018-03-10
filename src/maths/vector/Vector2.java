package vgl.maths.vector;

public class Vector2<T extends Number> {

	public T						x, y;
	private Class<? extends Number>	type;

	public Vector2(T x, T y) {
		this.x = x;
		this.y = y;
		this.type = x.getClass();
	}

//	private float asFloat(T t) {
//		return (float) t;
//	}
//
//	private int asInt(T t) {
//		return (int) t;
//	}
//
//	private double asDouble(T t) {
//		return (double) t;
//	}

}
