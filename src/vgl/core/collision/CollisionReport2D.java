package vgl.core.collision;

import vgl.maths.vector.Vector2f;

public class CollisionReport2D {
	
	public MTV mtv;
	
	public boolean collided;
	
	public boolean firstContainsSecond;
	
	public boolean secondContainsFirst;
	
	public static class MTV{
		public Vector2f translationDirection;
		public float    overlap;
		
		public MTV(Vector2f vec, float overlap) {
			this.translationDirection = vec;
			this.overlap = overlap;
		}
	}
	
	public CollisionReport2D() {
		
	}
	
	public CollisionReport2D collided(boolean collided) {
		this.collided = collided;
		return this;
	}
	
	public CollisionReport2D firstContainsSecond(boolean fcs) {
		this.firstContainsSecond = fcs;
		return this;
	}
	
	public CollisionReport2D secondContainsFirst(boolean scf) {
		this.secondContainsFirst = scf;
		return this;
	}

	public CollisionReport2D mtv(MTV mtv) {
		this.mtv = mtv;
		return this;
	}
}
