package vgl.core.input;

import vgl.core.annotation.VGLInternal;
import vgl.core.gfx.render.RenderContext;
import vgl.main.VGL;
import vgl.maths.Maths;
import vgl.maths.Projection;
import vgl.maths.vector.Vector2f;

public interface IPlatformInputDevice {
	
	byte	CURSOR_INPUT_MODE			= 0xf;
	byte	MOVEMENT_INPUT_MODE			= 0xc;
	byte	CURSOR_HIDDEN_INPUT_MODE	= 0xa;

	boolean isKeyDown(int keyCode);
	
	boolean isMouseButtonDown(int mouseButton);
	
	float getMouseX();
	
	float getMouseY();
	
	default float getMouseX(RenderContext context) {
		return Maths.linearConversion(getMouseX(), 0f, VGL.display.getWidth(), context.projectionMinX(), context.projectionMaxX());
	}
	
	default float getMouseY(RenderContext context) {
		return Maths.linearConversion(getMouseY(), 0f, VGL.display.getHeight(), context.projectionMinY(), context.projectionMaxY());
	}
	
	default Vector2f getMousePosition(RenderContext context) {
		return new Vector2f(getMouseX(context),
				            getMouseY(context));
	}
	
	float getDeltaX();
	
	float getDeltaY();
	
	float getMouseWheelDelta();
	
	void setMouseInputMode(int mInputMode);
	
	@VGLInternal
	void updateDeltas();

	default Vector2f getMousePosition() {
		return new Vector2f(getMouseX(), getMouseY());
	}
}
