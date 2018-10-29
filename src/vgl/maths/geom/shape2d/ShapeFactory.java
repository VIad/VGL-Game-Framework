package vgl.maths.geom.shape2d;

import java.util.ArrayList;
import java.util.List;

import vgl.maths.vector.Vector2f;

public class ShapeFactory {
	
	public static class PolygonBuilder{
		
		List<Vector2f> vertices;
		
		Vector2f initialCenter;
		
		List<Triangle> triangles;
		
		PolygonBuilder() {
			this.vertices = new ArrayList<>();
			this.triangles = new ArrayList<>();
		}
		
		public PolygonBuilder addVertex(Vector2f vertex) {
			this.vertices.add(vertex);
			return this;
		}
		
		public PolygonBuilder addVertex(float x, float y) {
			this.vertices.add(new Vector2f(x,y));
			return this;
		}
		
		public Polygon build() {
			return new Polygon(this);
		}
		
	}
	
	public static PolygonBuilder polyBuilder() {
		return new PolygonBuilder();
	}
	
	
	
}
