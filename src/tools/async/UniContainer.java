package vgl.tools.async;

public class UniContainer<T> {

	private volatile T t;

	public T get() {
		return t;
	}

	public UniContainer() {
		this.t = t;
	}

	public void put(T t) {
		this.t = t;
	}

}
