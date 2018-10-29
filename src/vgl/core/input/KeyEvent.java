package vgl.core.input;

import java.util.Arrays;
import java.util.List;

import vgl.core.events.Event;

//TODO
public class KeyEvent implements vgl.core.events.Event<KeyEvent>{
	
	public static enum EventType{
		DOWN, RELEASED, TYPED, REPEATED;
	}
	
	private int 	  keyCode;
	private EventType eventType;
	private char 	  keyChar;
	
	private List<EventFlag> flags;
	
	public EventType getEventType() {
		return eventType;
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
	public char getKeyChar() {
		return keyChar;
	}
	
	public KeyEvent(int keyCode, EventType eventType, Event.EventFlag... flags) {
		this.keyCode = keyCode;
		this.eventType = eventType;
		this.keyChar = (char) keyCode;
		if(flags != null && flags.length != 0)
			this.flags = Arrays.asList(flags); 
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public void setKeyChar(char keyChar) {
		this.keyChar = keyChar;
	}

	@Override
	public String toString() {
		return "KeyEvent [keyCode=" + keyCode + ", eventType=" + eventType + ", keyChar=" + keyChar + "]";
	}

	@Override
	public List<EventFlag> flags() {
		return flags;
	}
	


}
