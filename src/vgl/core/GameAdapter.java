package vgl.core;

public interface GameAdapter {

	void update();
	
	void render();
	
	void init();
	
	default void finish() {
		
	}
	
	default void fixedUpdate() {
		
	}
	
}
