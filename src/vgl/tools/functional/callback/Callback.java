package vgl.tools.functional.callback;

@FunctionalInterface
public interface Callback<T> {

	void invoke(T t);

}
