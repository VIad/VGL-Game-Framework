package vgl.core;

public interface StateControlledApplication {
	
	StateControlledApplication addState(String name, BasicState state);
	
	BasicState removeState(String name);
	
	BasicState getCurrent();
	
	void switchState(String name);

}
