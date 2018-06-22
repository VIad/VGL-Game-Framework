package vgl.tools.functional;

import java.util.Objects;

public interface ThrowableFunction<T extends java.lang.Throwable, A> {

	T apply(A a);

	default java.util.function.Supplier<T> supplyWith(A argument) {
		Objects.requireNonNull(argument);
		return () -> this.apply(argument);
	}

}
