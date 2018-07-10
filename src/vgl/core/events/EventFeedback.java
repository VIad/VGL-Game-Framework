package vgl.core.events;

import vgl.main.VGL;
import vgl.tools.functional.callback.Callback;

public class EventFeedback {

	private Callback<Integer> onKeyTyped = key -> {};
	
	public EventFeedback setOnKeyTyped(Callback<Integer> onKeyTyped) {
		this.onKeyTyped = onKeyTyped;
		return this;
	}
	
	
}
