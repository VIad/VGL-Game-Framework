package vgl.core;

import java.util.ArrayList;
import java.util.List;

import vgl.core.annotation.VGLInternal;
import vgl.core.exception.VGLRuntimeException;

public class StateController {

	// CASES WHERE DEFECATION MIGHT OCCUR
	//
	//
	// CURRENT STATE REMOVED
	// INIT CALLED WHEN ?

	private List<BasicState> stateList = new ArrayList<>();
	private BasicState current;

	/**
	 * Sets the currently active state, <b> MUST BE ADDED FIRST</b>
	 * 
	 * @param state
	 */
	public void setActive(String state) {
		BasicState basicState = get(state);
		if (this.current != null)
			this.current.onDetach();
		this.current = basicState;
		if (!basicState.initialized) {
			basicState.onCreate();
			basicState.initialized = true;
		}
		basicState.onAttach();
	}

	/**
	 * Adds a state but doesn't make it current
	 * 
	 * @param stateName
	 * @param state
	 * @return
	 */
	public StateController add(BasicState state) {
		if(stateList.contains(state))
			throw new VGLRuntimeException("Can't add state: "+state+ " twice");
		this.stateList.add(state);
		return this;
	}

	/**
	 * Removes a managed state <b> if it isn't CURRENT </b>
	 * 
	 * @param stateName
	 * @return
	 */
	public BasicState remove(String stateName) {
		BasicState basicState = get(stateName);
		if (current.equals(basicState))
			throw new VGLRuntimeException("Can't remove current stage! [HINT: switch states prior to removing]");
		basicState.onDetach();
		basicState.onDestroy();
		stateList.removeIf(f -> f.getName().equals(stateName));
		return basicState;
	}

	public BasicState getActive() {
		return current;
	}

	public BasicState get(String name) {
		return stateList.stream().filter(state -> state.getName().equals(name)).findFirst().orElseThrow(() -> new VGLRuntimeException("State named : "+name+ " doesn't exist"));
	}

	/**
	 * Adds a task for each update <b> only for the current state </b>
	 * 
	 * @param runnable
	 * @return
	 */
	public StateController addOnActiveStateUpdate(Runnable runnable) {
		this.current.onUpdateLoops.add(runnable);
		return this;
	}
	
	/**
	 * Adds a task for each update <b> only for the current state </b>
	 * 
	 * @param runnable
	 * @return
	 */
	public StateController addOnActiveStateRender(Runnable runnable) {
		this.current.onRenderLoops.add(runnable);
		return this;
	}

	/**
	 * Adds a task for the next update <b> only for the current state </b>
	 * 
	 * @param runnable
	 * @return
	 */
	public StateController runNextActiveStateUpdate(Runnable runnable) {
		this.current.onUpdateQueue.add(runnable);
		return this;
	}

	@VGLInternal public void update() {
		this.current.update();
		this.current.onUpdate();
	}

	@VGLInternal public void render() {
		this.current.render();
		this.current.onRender();
	}

	@VGLInternal public void fixedUpdate() {
		this.current.fixedUpdate();
	}

	@VGLInternal public void init() {
	}

	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
