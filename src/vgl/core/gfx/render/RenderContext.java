package vgl.core.gfx.render;

import vgl.main.VGL;
import vgl.maths.vector.Vector2f;

public interface RenderContext {
	
	RenderContext UNSPECIFIED = new RenderContext() {

		@Override
		public Vector2f user2Device() {
			return new Vector2f((int) 1, (int) 1);
		}
	};

	Vector2f user2Device();
	
	public static RenderContext create(float xScale, float yScale) {
		return new RenderContext() {
			
			@Override
			public Vector2f user2Device() {
				return new Vector2f(VGL.display.getWidth() * 1.0f / xScale,
						            VGL.display.getHeight()* 1.0f / yScale);
			}
		};
	}
	
}
