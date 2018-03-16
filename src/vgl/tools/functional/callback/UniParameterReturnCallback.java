package vgl.tools.functional.callback;

@FunctionalInterface
public interface UniParameterReturnCallback<A, R> {

	R get(A argument);

}
