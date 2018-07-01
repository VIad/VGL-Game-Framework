package vgl.utils;

import vgl.main.VGL;
import vgl.tools.IResourceLoader;

public class ResourceLoader {
	
	private static IResourceLoader self;
	
	private static void check() {
		if(self == null)
			self = VGL.factory.createResourceLoader();
	}
	
	static {
		check();
	}
	
	public static IResourceLoader get() {
		check();
		return self;
	}
	
}
