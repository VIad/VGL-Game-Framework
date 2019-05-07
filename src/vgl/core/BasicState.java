package vgl.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author vladi
 *
 */
abstract public class BasicState {
	
	private String name;
	boolean initialized = false;
	List<Runnable> 	onUpdateLoops = new ArrayList<>();
	List<Runnable> 	onRenderLoops = new ArrayList<>();
	Queue<Runnable> onUpdateQueue = new LinkedList<Runnable>();
	
	public BasicState(String name) {
		this.name = name;
	}
	
	public final static String MAIN = "main";

	abstract public void onUpdate();
	
	abstract public void onRender();
	
	abstract public void onCreate();
	
	public void onAttach() {
	}
	
	public void onDetach() {
	}
	
	public void onDestroy() {
	}
	
	public void fixedUpdate() {
	}
	
	public String getName() {
		return name;
	}
	
	void update() {
		onUpdateQueue.removeIf(task -> {
			task.run();
			return true;
		});
		onUpdateLoops.forEach(Runnable::run);
	}
	
	void render() {
		onRenderLoops.forEach(Runnable::run);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicState other = (BasicState) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasicState [name=" + name + ", initialized=" + initialized + ", onUpdateLoops=" + onUpdateLoops
				+ ", onUpdateQueue=" + onUpdateQueue + "]";
	}
	
	
	
	
}
