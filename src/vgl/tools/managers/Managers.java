package vgl.tools.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import vgl.tools.IResource;
import vgl.tools.IResourceManager;
import vgl.tools.functional.BiContainer;
import vgl.tools.functional.IDispenser;

abstract public class Managers {

	public final static String						DEFAULT_RESOURCE_MANAGER_KEY	= "default";

	private static Function<String, IResource>		onNotFound;
	private static BiPredicate<String, IResource>	onAlreadyExits;

	public static void onNotFound(Function<String, IResource> onNotFound) {
		Managers.onNotFound = onNotFound;
	}

	/**
	 * Tests whether the resource already exists<br>
	 * If it does and the function returns true, <b> the new resource overwrites the
	 * old one </b><br>
	 * If it does and the function returns false,<b> the new resource is ignored <b>
	 */
	public static void onAlreadyExists(BiPredicate<String, IResource> onExists) {
		Managers.onAlreadyExits = onExists;
	}

	private static Map<String, IResourceManager<? extends IResource>> managers;

	static {
		managers = new HashMap<>();

		onNotFound(alias -> {
			throw new IResource.ResourceException("Resource with alias >> " + alias + " was not found");
		});

		onAlreadyExists((alias, resource) -> {
			throw new IResource.ResourceException(
			        "Resource with alias >> " + alias + " already exists, cannot add twice");
		});
	}

	static void addManager(String alias, IResourceManager<? extends IResource> manager) {
		managers.put(alias, manager);
	}

	@SuppressWarnings("unchecked")
	public static <T extends IResource> List<T> allOfType(Class<T> type) {
		return (List<T>) managers.values().stream().filter(manager -> manager.getResourceType().equals(type)).flatMap(
		        manager -> manager.all().stream()).map(container -> container.getSecond()).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public static <T extends IResource> List<IResourceManager<T>> managing(Class<T> clazz) {
		return managers.values().stream().filter(manager -> manager.getResourceType().equals(clazz)).map(
		        rm -> (IResourceManager<T>) rm).collect(Collectors.toList());
	}
	
	public static Class<? extends IResource> resourceTypeOf(String mgrKey){
		return managers.entrySet()
		               .stream()
		               .filter(entry -> entry.getKey().equals(mgrKey))
		               .map(entry -> entry.getValue().getResourceType())
		               .findFirst()
		               .orElse(null);
	}

	public static Stream<BiContainer<String, ? extends IResource>> walk() {
		Stream.Builder<BiContainer<String, ? extends IResource>> builder = Stream.builder();
		managers.values().stream().flatMap(mgr -> mgr.all().stream()).forEach(builder::add);
		return builder.build();
	}

	public static IResourceManager<? extends IResource> fetchOrCreate(String resMgrKey,
	        Function<String, IResourceManager<? extends IResource>> ifNotFound) {
		if (managers.containsKey(resMgrKey))
			return managers.get(resMgrKey);
		return ifNotFound.apply(resMgrKey);
	}

	@SuppressWarnings("unchecked")
	public static <T extends IResource> IResourceManager<T> fetchOrCreate(String resMgrKey,
			                                                              Class<T> resType,
	                                                                      BiFunction<Class<T>, String, IResourceManager<T>> managerSupplier) {
		if(managers.containsKey(resMgrKey) && resourceTypeOf(resMgrKey).equals(resType)) 
			return (IResourceManager<T>) managers.get(resMgrKey);
		return managerSupplier.apply(resType, resMgrKey);
	}

	static Function<String, IResource> getOnNotFound() {
		return onNotFound;
	}

	static BiPredicate<String, IResource> getOnAlreadyExits() {
		return onAlreadyExits;
	}

}
