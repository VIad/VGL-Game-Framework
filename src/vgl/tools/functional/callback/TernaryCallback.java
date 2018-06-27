package vgl.tools.functional.callback;

public interface TernaryCallback<T, U, R> {
	
	void invoke(T arg0, U arg1, R arg2);

}
