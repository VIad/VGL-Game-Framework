package vgl.tools.functional;

public class Functional {

	public static <T, R> java.util.function.Supplier<R> bind(java.util.function.Function<T, R> fn, T val) {
		return () -> fn.apply(val);
	}

}
