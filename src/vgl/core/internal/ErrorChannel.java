package vgl.core.internal;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

import vgl.core.exception.ErrorChannelHandler;
import vgl.main.VGL;

public class ErrorChannel {
	
	private Queue<Throwable> errors;
	
	private ErrorChannelHandler errorHandler;
	
	public ErrorChannel() {
		this.errors = new LinkedList<>();
		this.errorHandler = error -> {
			
		};
	}
	
	public ErrorChannel forward(Supplier<Throwable> supplier) {
		this.errors.add(supplier.get());
		return this;
	}
	
	public void setErrorHandler(ErrorChannelHandler ech) {
		this.errorHandler = ech;
	}
	
	public void supplyChannel() {
		errors.removeIf(error -> {
			errorHandler.invoke(error);
			return true;
		});
	}
	
	public Queue<Throwable> getErrors() {
		return errors;
	}

}
