package vgl.tools.functional;

public class SynchronousHold<T> {

	private volatile T t;
	
	public SynchronousHold(Result<T> res) {
		this.t = res.successRes;
	}
	
	public static <T> SynchronousHold<T> from(Result<T> res){
		return new SynchronousHold<>(res);
	}
	
	public T get() {
		return t;
	}
	
}
