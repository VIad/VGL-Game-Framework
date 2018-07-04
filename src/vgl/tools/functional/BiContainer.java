package vgl.tools.functional;

import vgl.core.gfx.Image;
import vgl.tools.managers.Managers;

public class BiContainer<A1, A2> {
	
	private A1 a1;
	private A2 a2;
	
	public BiContainer(A1 a1, A2 a2) {
		this.a1 = a1;
		this.a2 = a2;
	}
	
	public A1 getFirst() {
		return a1;
	}
	
	public A2 getSecond() {           
		return a2;
	}

}
