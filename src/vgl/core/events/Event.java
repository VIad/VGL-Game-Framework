package vgl.core.events;

import java.util.List;

public interface Event<T> {
	
	public static enum EventFlag{
		FIRE_ONCE;
	}
	
	List<EventFlag> flags();

}
