package vgl.maths.geom.shape2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vgl.maths.geom.OrthoProjection;
import vgl.maths.geom.Size2f;
import vgl.maths.geom.Transform2D;
import vgl.maths.geom.shape2d.ShapeFactory.PolygonBuilder;
import vgl.maths.vector.Vector2f;

public class Polygon implements Shape2D {

	private Vector2f		position;

	private List<Vector2f>	vertices;

	private float			angleRot;

	private RectFloat		bounds;

	private Vector2f		translation;

	private Size2f			scale;

	public Polygon() {
		this((Vector2f) null);
	}

	public Polygon(float x, float y) {
		this.vertices = new ArrayList<>();
		this.position = new Vector2f(x, y);
		addVertex(position);
		this.bounds = new RectFloat();
		this.translation = new Vector2f();
		this.scale = Size2f.identity();
		this.angleRot = 0.0f;
	}

	public Polygon(Vector2f pos) {
		this.vertices = new ArrayList<>();
		if (pos != null) {
			this.position = pos.copy();
			addVertex(this.position);
		}
		this.bounds = new RectFloat();
		this.translation = new Vector2f();
		this.scale = Size2f.identity();
		this.angleRot = 0.0f;
	}

	/**
	 * Broken atm
	 * 
	 * @param other
	 */
	public Polygon(Polygon other) {
		this.vertices = new ArrayList<>();
		other.vertices.forEach(vertex -> this.vertices.add(vertex.copy()));
		if (!this.vertices.isEmpty())
			this.position = this.vertices.get(0);
		this.bounds = new RectFloat(other.bounds);
		this.translation = other.translation.copy();
		this.angleRot = other.angleRot;
		this.scale.width = other.scale.width;
		this.scale.height = other.scale.height;
	}

	public Polygon(PolygonBuilder polygonBuilder) {
		this.vertices = polygonBuilder.vertices;
		this.position = polygonBuilder.vertices.get(0);
		this.bounds = new RectFloat();
		this.translation = new Vector2f();
		this.scale = Size2f.identity();
		this.angleRot = 0.0f;
	}

	/*
	 * O(n) each update :thinking:
	 */
	private void recalculateBounds() {
		float minX = Float.POSITIVE_INFINITY, minY = Float.POSITIVE_INFINITY, maxX = Float.NEGATIVE_INFINITY,
		        maxY = Float.NEGATIVE_INFINITY;
		for (Vector2f vector2f : vertices) {
			if (vector2f.x < minX)
				minX = vector2f.x;
			if (vector2f.y < minY)
				minY = vector2f.y;
			if (vector2f.x > maxX)
				maxX = vector2f.x;
			if (vector2f.y > maxY)
				maxY = vector2f.y;
		}
		bounds.setBounds(minX, minY, maxX - minX, maxY - minY);

	}

	public Polygon transformBy(Transform2D transform) {
		return this;
	}

	public Polygon translateTo(float x, float y) {
		return translateBy(-bounds.x + x, -bounds.y + y);
	}

	public Polygon translateBy(float x, float y) {
		this.translation.add(x, y);
		vertices.stream().forEach(vertex -> vertex.add(x, y));
		recalculateBounds();
		return this;
	}

	public static Polygon create(Shape2D shape) {
		return shape.toPolygon();
	}

	public boolean contains(Vector2f point) {
		return contains(point.x, point.y);
	}

	public boolean contains(float x, float y) {
		return pnpoly(getVertexCount(), x, y);
	}

	private boolean pnpoly(int nvert, float testx, float testy) {

		int i, j;
		boolean c = false;
		for (i = 0, j = nvert - 1; i < nvert; j = i++) {
			if (((vertices.get(i).y > testy) != (vertices.get(j).y > testy))
			        && (testx < (vertices.get(j).x - vertices.get(i).x) * (testy - vertices.get(i).y)
			                / (vertices.get(j).y - vertices.get(i).y) + vertices.get(i).x))
				c = !c;
		}
		return c;
	}

	public Polygon scaleBy(float xFactor, float yFactor) {
		scale.width += xFactor;
		scale.height += yFactor;
		vertices.stream().forEach(vertex -> vertex.scale(xFactor, yFactor));
		recalculateBounds();
		return this;
	}

	public Polygon rotateAroundCenterBy(float angleDegrees) {
		return rotateBy(angleDegrees, bounds().getCenter());
	}

	public Polygon rotateAroundCenterTo(float angleDegrees) {
		return rotateBy(-this.angleRot + angleDegrees, bounds().getCenter());
	}

	public Polygon rotateBy(float angleDegrees, float anchorX, float anchorY) {
		this.angleRot += angleDegrees;
		vertices.stream().forEach(
		        vertex -> vertex.subtract(anchorX, anchorY).rotate(angleDegrees).subtract(-anchorX, -anchorY));
		recalculateBounds();
		return this;
	}

	public Polygon rotateBy(float angleDegrees, Vector2f anchor) {
		return rotateBy(angleDegrees, anchor.x, anchor.y);
	}

	public Polygon rotateBy(float angleDegrees) {
		return rotateBy(angleDegrees, 0, 0);
	}

	public Vector2f getPosition() {
		return position;
	}

	public Polygon addVertex(Vector2f vertex) {
		if(position == null)
			this.position = vertex;
		vertices.add(vertex);
		return this;
	}

	public Polygon addVertex(float x, float y) {
		return addVertex(new Vector2f(x, y));
	}

	public List<Vector2f> getVertices() {
		return Collections.unmodifiableList(vertices);
	}

	public RectFloat bounds() {
		recalculateBounds();
		return new RectFloat(bounds);
	}

	public int getVertexCount() {
		return vertices.size();
	}

	public OrthoProjection project(Vector2f axis) {
		return new OrthoProjection(this, axis);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bounds == null) ? 0 : bounds.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((vertices == null) ? 0 : vertices.hashCode());
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
		Polygon other = (Polygon) obj;
		if (bounds == null) {
			if (other.bounds != null)
				return false;
		} else if (!bounds.equals(other.bounds))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (vertices == null) {
			if (other.vertices != null)
				return false;
		} else if (!vertices.equals(other.vertices))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Polygon [position=" + position + ", bounds=" + bounds + "]";
	}

	@Override
	public Vector2f relativeCenter() {
		return bounds().getCenter();
	}

	@Override
	public Polygon toPolygon() {
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Polygon copy() {
		return new Polygon(this);
	}

}
