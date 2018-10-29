package vgl.tools.functional;

import java.util.LinkedList;
import java.util.List;

import vgl.tools.functional.callback.Callback;

public class Pipeline<T> {
	
	private T element;
	
	private List<Callback<T>> callbacks;
	
	private Callback<Throwable> fail;
	
	public Pipeline() {
		this.element = null;
		this.callbacks = new LinkedList<>();
	}
	
	synchronized public Pipeline<T> after(Callback<T> callback){
		callbacks.add(callback);
		return this;
	}
	
	synchronized public void supply(T element) {
		this.element = element;
		callbacks.forEach(callback -> callback.invoke(element));
	}
	
	synchronized public T get() {
		return element;
	}
	
	synchronized public void fail(Throwable t) {
		fail.invoke(t);
	}
	
	synchronized public Pipeline<T> ifThrown(Callback<Throwable> cb){
		this.fail = cb;
		return this;
	}
	
}
