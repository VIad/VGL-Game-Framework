package vgl.core.collision;

import vgl.maths.geom.shape2d.IRect;
import vgl.maths.geom.shape2d.Polygon;
import vgl.maths.geom.shape2d.Shape2D;
import vgl.tools.functional.IDispenser;

public class Collider2D {
	
	private static ICollisionEngine2D self;
	
	static {
		setCollisionEngine(SAT2D::new);
	}
	
	public static void setCollisionEngine(IDispenser<ICollisionEngine2D> engine) {
		self = engine.dispense();
	}
	
	public static void setCollisionEngine(ICollisionEngine2D engine) {
		self = engine;
	}
	
	public static boolean checkPolygonIntersection(Polygon first, Polygon second) {
		return self.testPolygonIntersection(first, second).collided;
	}
	
	public static boolean checkShapeIntersection(Shape2D first, Shape2D second) {
		if(first instanceof IRect && second instanceof IRect)
			return checkRectangleIntersection((IRect) first, (IRect) second);
		return checkPolygonIntersection(first.toPolygon(),
				                        second.toPolygon());
	}
	
	public static boolean checkRectangleIntersection(IRect r1, IRect r2) {
		return self.testRectangleIntersection(r1, r2).collided;
	}
	
	public static CollisionReport2D evaluatePolygonIntersection(Polygon p1, Polygon p2) {
		return self.testPolygonIntersection(p1, p2);
	}
	
	public static CollisionReport2D evaluateRectangleIntersection(IRect first, IRect second) {
		return self.testRectangleIntersection(first, second);
	}
	
	public static boolean checkShapeContainment(Shape2D container, Shape2D contained) {
		if(container instanceof IRect && contained instanceof IRect)
			return self.testRectangleIntersection((IRect) container, (IRect) contained).firstContainsSecond;
		return checkPolygonContainment(container.toPolygon(),
				                       contained.toPolygon());
	}
	
	public static boolean checkPolygonContainment(Polygon container, Polygon contained) {
		return evaluatePolygonIntersection(container,
				                           contained).firstContainsSecond;
	}
	
	
}
