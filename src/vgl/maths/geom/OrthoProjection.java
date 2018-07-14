package vgl.maths.geom;

import java.util.List;

import vgl.maths.geom.shape2d.Polygon;
import vgl.maths.vector.Vector2f;

public class OrthoProjection {

	public float min, max;
	
	public OrthoProjection(Polygon polygon, Vector2f axis) {
		Vector2f axxis = axis.copy();
		if(axxis.length() != 0)
			axxis.normalize();
		List<Vector2f> vertices = polygon.getVertices();
		float min = axxis.dot(vertices.get(0));
		float max = min;
		for (int i = 1; i < vertices.size(); i++) {
			float p = axxis.dot(vertices.get(i));
			if (p < min)
				min = p;
			else if (p > max)
				max = p;
		}
		this.min = min;
		this.max = max;
	}
	
	public float getMax() {
		return max;
	}
	
	public float getMin() {
		return min;
	}
	
	public boolean overlaps(OrthoProjection other) {
		if(other.min >= max || this.min >= other.max)
			return false;
		return true;
	}
	
	public float getOverlap(OrthoProjection other) {
		if(!overlaps(other))
			return 0.0f;
		return max <= other.max ? max - other.min : other.max - min;
	}
	
	public boolean contains(OrthoProjection other) {
		if(!overlaps(other))
			return false;
		return min <= other.min && max >= other.max;
	}
}
