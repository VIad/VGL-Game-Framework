package vgl.core.events;

import java.util.ArrayList;
import java.util.List;

import vgl.core.input.KeyEvent;
import vgl.core.internal.GlobalDetails;
import vgl.main.VGL;
import vgl.platform.Platform;
import vgl.tools.functional.callback.Callback;
import vgl.web.VGLWebApplication;
import vgl.web.WebSpecific;

public class EventFeedback {

	private List<Callback<KeyEvent>>	keyListeners;

	private List<Callback<WindowFocusedEvent>>	windowFocusListeners;

	public EventFeedback() {
		this.keyListeners = new ArrayList<>();
		this.windowFocusListeners = new ArrayList<>();
	}

	public EventFeedback addKeyListener(Callback<KeyEvent> onKeyTyped) {
		this.keyListeners.add(onKeyTyped);
		return this;
	}
	
	public boolean shouldDispatchForContext(Class<? extends Event<?>> type) {
		if(type == KeyEvent.class && keyListeners.isEmpty())
			return false;
		else if(type == WindowFocusedEvent.class && windowFocusListeners.isEmpty())
			return false;
		
		return true;
	}

	public EventFeedback addOnDisplayFocused(Callback<WindowFocusedEvent> hasFocus) {
		if(GlobalDetails.getPlatform() == Platform.WEB) {
//			WebSpecific.Display.addFocusListener(((VGLWebApplication) VGL.app).getContext().display().getElement(), hasFocus);
			WebSpecific.JS.addEventListener("focus",    ((VGLWebApplication) VGL.app).getContext().display().getElement(), () -> hasFocus.invoke(new WindowFocusedEvent(true)));
			WebSpecific.JS.addEventListener("focusout", ((VGLWebApplication) VGL.app).getContext().display().getElement(), () -> hasFocus.invoke(new WindowFocusedEvent(false)));
			return this;
		}
		this.windowFocusListeners.add(hasFocus);
		return this;
	}
	
	public EventFeedback fire(Event<?> event) {
		if(event.getClass() == KeyEvent.class) {
			keyListeners.forEach(cb -> cb.invoke((KeyEvent) event));
		}
		if(event.getClass() == WindowFocusedEvent.class) {
			windowFocusListeners.forEach(cb -> cb.invoke((WindowFocusedEvent) event));
		}
		return this;
	}

}
