package vgl.audio;

import vgl.tools.IResourceManager;
import vgl.tools.managers.AbstractResourceManager;
import vgl.tools.managers.Managers;

public class AudioManager extends AbstractResourceManager<Sound> {

	protected AudioManager(String managerName) {
		super(Sound.class, managerName);
	}

	public static Sound fetch(String resName) {
		return fetch(resName, Managers.DEFAULT_RESOURCE_MANAGER_KEY);
	}

	public static Sound fetch(String defaultResourceManagerKey, String resName) {
		return (Sound) Managers.fetchOrCreate(defaultResourceManagerKey, AudioManager::new).get(resName);
	}

	public static void insert(String alias, Sound snd) {
		insert(alias, Managers.DEFAULT_RESOURCE_MANAGER_KEY, snd);
	}

	@SuppressWarnings("unchecked")
	public static void insert(String manager, String alias, Sound sound) {
		((IResourceManager<Sound>) Managers.fetchOrCreate(manager, AudioManager::new)).put(alias, sound);
	}

}
