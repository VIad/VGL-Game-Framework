package vgl.tools.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import vgl.tools.IResource;
import vgl.tools.IResourceManager;
import vgl.tools.functional.BiContainer;

abstract public class AbstractResourceManager<T extends IResource> implements IResourceManager<T>{

	private Map<String, T> container;
	
	private Class<T> type;
	
	protected AbstractResourceManager(Class<T> clazz, String managerName){
		this.container = new HashMap<>();
		this.type = clazz;
		Managers.addManager(managerName, this);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T get(String alias) {
		if(!container.containsKey(alias))
			return (T) Managers.getOnNotFound().apply(alias);
		return container.get(alias);
	}
	
	public Class<T> getResourceType() {
		return type;
	}
	
	@Override
	public IResourceManager<T> put(String alias, T resource) {
		if(container.containsKey(alias)) {
			if(Managers.getOnAlreadyExits().test(alias, resource)) {
				container.replace(alias, resource);
			}else {
				//Ignore
			}
		}else {
			container.put(alias, resource);
		}
		return this;
	}
	
	public void remove(String alias) {
		if(!container.containsKey(alias)) {
			IResource resource = Managers.getOnNotFound().apply(alias);
			/**
			 * Since not used, pervent memory leak
			 */
			if (resource != null)
				resource.releaseMemory();
			return;
		}
		container.remove(alias);
	}

	public boolean contains(String alias) {
		return container.containsKey(alias);
	}
	
	@Override
	public List<BiContainer<String, T>> all() {
		return container.entrySet()
				        .stream()
				        .map(entry -> new BiContainer<>(entry.getKey(), entry.getValue()))
				        .collect(Collectors.toList());
	}
	
}
