package vgl.core.gfx.render;

import vgl.main.VGL;
import vgl.maths.vector.Vector2f;

public interface RenderContext {
	
	RenderContext UNSPECIFIED = new RenderContext() {

		@Override
		public Vector2f user2Device() {
			return new Vector2f((int) 1, (int) 1);
		}

		@Override
		public float projectionMinX() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public float projectionMinY() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public float projectionMaxX() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public float projectionMaxY() {
			// TODO Auto-generated method stub
			return 0;
		}
	};

	Vector2f user2Device();
	
	float projectionMinX();
	float projectionMinY();
	
	float projectionMaxX();
	float projectionMaxY();
	
	public static RenderContext create(float minX, float maxX, float minY, float maxY) {
		return new RenderContext() {
			

			@Override
			public float projectionMinX() {
				return minX;
			}

			@Override
			public float projectionMinY() {
				return minY;
			}

			@Override
			public float projectionMaxX() {
				return maxX;
			}

			@Override
			public float projectionMaxY() {
				return maxY;
			}
			
			@Override
			public Vector2f user2Device() {
				return new Vector2f(VGL.display.getWidth() * 1.0f / Math.abs(maxX - minX),
						VGL.display.getHeight()* 1.0f / Math.abs(maxY - minY));
			}
		};
	}
	
}
