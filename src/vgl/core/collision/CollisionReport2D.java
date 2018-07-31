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
	
	private CollisionReport2D(Builder builder) {
		this.mtv = builder.mtv;
		this.collided = builder.collided;
		this.firstContainsSecond = builder.firstContainsSecond;
		this.secondContainsFirst = builder.secondContainsFirst;
	}
	
	public static class Builder{
		
		private MTV mtv;
		
		private boolean collided;
		
		private boolean firstContainsSecond;
		
		private boolean secondContainsFirst;
		
		public CollisionReport2D build() {
			return new CollisionReport2D(this);
		}
		
		public Builder mtv(MTV mtv) {
			this.mtv = mtv;
			return this;
		}
		
		public Builder setCollided(boolean collided) {
			this.collided = collided;
			return this;
		}
		
		public Builder firstContainsSecond(boolean fcs) {
			this.firstContainsSecond = fcs;
			return this;
		}
		
		public Builder secondContainsFirst(boolean scf) {
			this.secondContainsFirst = scf;
			return this;
		}
		
	}
}
