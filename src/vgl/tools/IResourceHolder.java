package vgl.tools;

public interface IResourceHolder {
	
	<T extends IResource> T getResource(String alias);
	
	<T> void putResource(T resource);

}
