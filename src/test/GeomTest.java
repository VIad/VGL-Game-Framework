package test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import vgl.maths.geom.GeomUtils;
import vgl.maths.geom.OrthoProjection;
import vgl.maths.geom.shape2d.Polygon;
import vgl.maths.geom.shape2d.RectFloat;
import vgl.maths.geom.shape2d.Triangle;
import vgl.maths.vector.Vector2f;

public class GeomTest {

	public static void main(String[] args) {
		RectFloat rect = new RectFloat(0f,0f,5f,5f);
		RectFloat rect2 = new RectFloat(4.99f,4.99f,1f,1f);
		RectFloat idk = new RectFloat(5.2f, 5.3f, 4f, 7f);
//		System.out.println(rect.intersects(rect2));
//		System.out.println(intersects(rect.toPolygon().translateBy(6.0f, 0f), rect2.toPolygon()));
		Polygon hugeTriangle = new Polygon()
				                   .addVertex(0f, 0f)
				                   .addVertex(10f, 0f)
				                   .addVertex(5f, 10f);
		
		Polygon a = new Polygon();
	   	  a.addVertex(new Vector2f(0,6));
		  a.addVertex(new Vector2f(0,0));
		  a.addVertex(new Vector2f(3,0));
		  a.addVertex(new Vector2f(4,1));
		  a.addVertex(new Vector2f(6,1));
		  a.addVertex(new Vector2f(8,0));
		  a.addVertex(new Vector2f(12,0));
		  a.addVertex(new Vector2f(13,2));
		  a.addVertex(new Vector2f(8,2));
		  a.addVertex(new Vector2f(8,4));
		  a.addVertex(new Vector2f(11,4));
		  a.addVertex(new Vector2f(11,6));
		  a.addVertex(new Vector2f(6,6));
		  a.addVertex(new Vector2f(4,3));
		  a.addVertex(new Vector2f(2,6));
		
		ArrayList<Vector2f> result = new ArrayList<>();
		
		System.out.println(GeomUtils.Triangulator.process(a.getVertices(), result));
		
		 int tcount = result.size()/3;

		  for (int i=0; i<tcount; i++)
		  {
		    final Vector2f p1 = result.get(i*3+0);
		    final Vector2f p2 = result.get(i*3+1);
		    final Vector2f p3 = result.get(i*3+2);
		    System.out.printf("Triangle %d => (%f,%f) (%f,%f) (%f,%f)\n",i+1,p1.x,p1.y,p2.x,p2.y,p3.x,p3.y);
		  }
		  
		  
		long ms = System.currentTimeMillis();
		GeomUtils.Triangulator.triangulate(a);
		System.out.println("Time : "+(System.currentTimeMillis() - ms));
//		RectFloat bounds = 
//				hugeTriangle.bounds();
//		for(int i  = (int) bounds.x; i < bounds.x + bounds.width; i++) {
//			for(int j = (int) bounds.y; j < bounds.y + bounds.height;j++) {
//				System.out.print(hugeTriangle.contains(j, i) ? "1 " : " ");
//			}
//			System.out.println();
//		}
		
//		Polygon tri2 = new Polygon(new Vector2f())
//                                  .addVertex(5f, 5.1f)
//                                  .addVertex(10f, 5.1f)
//                                  .addVertex(7.5f, -10f)
//                                  .translateBy(0f, -0.1f);
//		System.out.println(intersects(tri1, tri2));
//				             


	}

}
