package vgl.maths.geom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import vgl.maths.geom.shape2d.Polygon;
import vgl.maths.geom.shape2d.Triangle;
import vgl.maths.vector.Vector2f;

public class GeomUtils {

	public static List<Vector2f> getNormals(Polygon shape) {
		List<Vector2f> normals = new ArrayList<>();
		List<Vector2f> vertices = shape.getVertices();
		for (int i = 0; i < vertices.size(); i++) {
			Vector2f p1 = vertices.get(i);
			Vector2f p2 = vertices.get(i + 1 == shape.getVertexCount() ? 0 : i + 1);

			Vector2f edge = p1.copy().subtract(p2);
			Vector2f normal = edge.perp();

			normals.add(normal.normalize());
		}

		return normals;
	}

	// public static List<Vector2f> getUniqueNormals(Polygon shape) {
	// List<Vector2f> normals = getNormals(shape);
	// System.out.println(normals + " <<");
	// System.out.println(normals.get(0) + " <> "+normals.get(0).copy().negate());
	// for (Vector2f vector2f : normals) {
	//
	// }
	//
	// return normals.stream()
	// .filter(vec -> normals.contains(vec.copy().negate()))
	// .collect(Collectors.toList());
	// }

	
	/**
	 * @author John W. Ratcliff
	 * 
	 * Implemented in Java by Vladimir Ivanov
	 *
	 */
	public static class Triangulator {

		static final float EPSILON = 0.0000000001f;

		static float area(final List<Vector2f> contour) {

			int n = contour.size();

			float A = 0.0f;

			for (int p = n - 1, q = 0; q < n; p = q++) {
				A += contour.get(p).x * contour.get(q).y - contour.get(q).x * contour.get(p).y;
			}
			return A * 0.5f;
		}

		/*
		 * InsideTriangle decides if a point P is Inside of the triangle defined by A,
		 * B, C.
		 */
		static boolean insideTri(float Ax, float Ay, float Bx, float By, float Cx, float Cy, float Px, float Py)

		{
			float ax, ay, bx, by, cx, cy, apx, apy, bpx, bpy, cpx, cpy;
			float cCROSSap, bCROSScp, aCROSSbp;

			ax = Cx - Bx;
			ay = Cy - By;
			bx = Ax - Cx;
			by = Ay - Cy;
			cx = Bx - Ax;
			cy = By - Ay;
			apx = Px - Ax;
			apy = Py - Ay;
			bpx = Px - Bx;
			bpy = Py - By;
			cpx = Px - Cx;
			cpy = Py - Cy;

			aCROSSbp = ax * bpy - ay * bpx;
			cCROSSap = cx * apy - cy * apx;
			bCROSScp = bx * cpy - by * cpx;

			return ((aCROSSbp >= 0.0f) && (bCROSScp >= 0.0f) && (cCROSSap >= 0.0f));
		};

		static boolean snip(final List<Vector2f> contour, int u, int v, int w, int n, int[] V) {
			int p;
			float Ax, Ay, Bx, By, Cx, Cy, Px, Py;

			Ax = contour.get(V[u]).x;
			Ay = contour.get(V[u]).y;

			Bx = contour.get(V[v]).x;
			By = contour.get(V[v]).y;

			Cx = contour.get(V[w]).x;
			Cy = contour.get(V[w]).y;

			if (EPSILON > (((Bx - Ax) * (Cy - Ay)) - ((By - Ay) * (Cx - Ax))))
				return false;

			for (p = 0; p < n; p++) {
				if ((p == u) || (p == v) || (p == w))
					continue;
				Px = contour.get(V[p]).x;
				Py = contour.get(V[p]).y;
				if (insideTri(Ax, Ay, Bx, By, Cx, Cy, Px, Py))
					return false;
			}

			return true;
		}

		static public boolean process(final List<Vector2f> contour, List<Vector2f> result) {
			/* allocate and initialize list of Vertices in polygon */

			int n = contour.size();
			if (n < 3)
				return false;

			int[] V = new int[n];

			/* we want a counter-clockwise polygon in V */

			if (0.0f < area(contour))
				for (int v = 0; v < n; v++)
					V[v] = v;
			else
				for (int v = 0; v < n; v++)
					V[v] = (n - 1) - v;

			int nv = n;

			/* remove nv-2 Vertices, creating 1 triangle every time */
			int count = 2 * nv; /* error detection */

			for (int m = 0, v = nv - 1; nv > 2;) {
				/* if we loop, it is probably a non-simple polygon */
				if (0 >= (count--)) {
					// ** Triangulate: ERROR - probable bad polygon!
					return false;
				}

				/* three consecutive vertices in current polygon, <u,v,w> */
				int u = v;
				if (nv <= u)
					u = 0; /* previous */
				v = u + 1;
				if (nv <= v)
					v = 0; /* new v */
				int w = v + 1;
				if (nv <= w)
					w = 0; /* next */

				if (snip(contour, u, v, w, nv, V)) {
					int a, b, c, s, t;

					/* true names of the vertices */
					a = V[u];
					b = V[v];
					c = V[w];

					/* output Triangle */
					result.add(contour.get(a));
					result.add(contour.get(b));
					result.add(contour.get(c));

					m++;

					/* remove v from remaining polygon */
					for (s = v, t = v + 1; t < nv; s++, t++)
						V[s] = V[t];
					nv--;

					/* resest error detection counter */
					count = 2 * nv;
				}
			}
			return true;
		}
		
		public static Optional<List<Triangle>> triangulate(Polygon polygon){
			List<Vector2f> vertices = new ArrayList<>();
			if(!process(polygon.getVertices(), vertices))
				return Optional.empty();
			List<Triangle> triangles = new ArrayList<>();
			int tCount = vertices.size() / 3;
			for (int i = 0; i < tCount; i++) {
				final Vector2f p1 = vertices.get(i*3+0);
			    final Vector2f p2 = vertices.get(i*3+1);
			    final Vector2f p3 = vertices.get(i*3+2);
			    triangles.add(new Triangle(p1, p2, p3));
			}
			return Optional.of(triangles);
		}
		
	}

	private GeomUtils() {

	}

}
