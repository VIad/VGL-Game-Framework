package vgl.web.utils;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.animation.client.AnimationScheduler.AnimationCallback;
import com.google.gwt.dom.client.CanvasElement;

public class JSRenderInfoContainer {

	private static AnimationCallback animationCallback;
	
	private static CanvasElement canvasElement;
	
	public static void setCanvasElement(CanvasElement canvasElement) {
		JSRenderInfoContainer.canvasElement = canvasElement;
	}
	
	public static AnimationCallback getAnimationCallback() {
		return animationCallback;
	}
	
	public static void setAnimationCallback(AnimationCallback animationCallback) {
		JSRenderInfoContainer.animationCallback = animationCallback;
	}
	
	public static void requestFrame() {
		AnimationScheduler.get().requestAnimationFrame(animationCallback);
	}
	
}
