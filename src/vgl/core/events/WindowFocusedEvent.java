package vgl.core.events;

import java.util.Arrays;
import java.util.List;

public class WindowFocusedEvent implements Event<WindowFocusedEvent> {

	private boolean focused;
	
	private List<EventFlag> flags;
	
	public WindowFocusedEvent(boolean focused, Event.EventFlag... flags) {
		this.focused = focused;
		if(flags != null && flags.length != 0)
			this.flags = Arrays.asList(flags); 
	}
	
	public boolean isFocused() {
		return focused;
	}

	@Override
	public List<EventFlag> flags() {
		return flags;
	}
	

}
