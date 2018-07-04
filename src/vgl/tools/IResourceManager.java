package vgl.tools;

import java.util.List;

import vgl.tools.functional.BiContainer;

public interface IResourceManager<T extends IResource> {
	
	IResourceManager<T> put(String alias, T resource);
	
	T get(String alias);
	
	List<BiContainer<String, T>> all();
	
	void remove(String alias);
	
	Class<T> getResourceType();
	
}
