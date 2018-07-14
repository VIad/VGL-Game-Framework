package vgl.maths.geom.shape2d;

public interface IRect extends Shape2D{

	float getX();
	float getY();
	float getWidth();
	float getHeight();
	
	boolean intersects(IRect other);
	
	
	default boolean contains(IRect rect) {
		return contains(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}
	
	boolean contains(float x, float y, float w, float h);
}
