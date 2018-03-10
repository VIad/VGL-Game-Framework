package vgl.audio.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import vgl.audio.AudioSystem;
import vgl.audio.Sound;
import vgl.core.exception.VGLAudioException;

//=============================================================================
//Name        : AudioManager
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : Manager class for all sound objects, used for creating, playing
//reconfiguring and others.
//=============================================================================
/**
 * Manager for sound objects. in order to play back a sound or reference it in
 * the manager, you need to give it an alias (any string)
 */
public class AudioManager {

	private static Map<String, Sound>	soundMap;
	private static boolean				created	= false;

	/**
	 * Creates the AudioManager <br>
	 * <b> Warning : Calls to any method of the class should be after
	 * initialization, otherwise <br>
	 * {@link VGLAudioException} will be thrown</b>
	 */
	public static void create() {
		if (!AudioSystem.isInitialized()) {
			throw new vgl.core.exception.VGLAudioException(
			        "AudioSystem is not initialized >> call AudioSystem.initialize()");
		}
		created = true;
		soundMap = new HashMap<>();
	}

	/**
	 * Adds a sound to the manager with the given alias
	 * 
	 * @param alias
	 *            - the specified alias
	 * @param sound
	 *            - the sound object
	 * @throws {@link
	 *             VGLAudioException} if the manager has not been initialized
	 */
	public static Sound add(String alias, Sound sound) {
		if (!created)
			throw new vgl.core.exception.VGLAudioException("AudioManager is not created >> call AudioManager.create()");
		soundMap.put(alias, sound);
		return sound;
	}

	/**
	 * Begins playback of the sound specified by the alias parameter <br>
	 * <b> Warning: a call to remove() whilst playing will not stop the sound, just
	 * remove it from the manager </b> <br>
	 * <br>
	 * <b>throws {@link VGLAudioException} if the manager has not been initialized
	 * or the sound by the given alias not found</b>
	 */
	public static void play(String alias) {
		if (!created)
			throw new vgl.core.exception.VGLAudioException("AudioManager is not created >> call AudioManager.create()");
		findEntryOrThrow(alias).getValue().play();
	}

	public static vgl.audio.SoundState getState(String alias) {
		if (!created)
			throw new vgl.core.exception.VGLAudioException("AudioManager is not created >> call AudioManager.create()");
		return findEntryOrThrow(alias).getValue().getState();
	}

	public static void pause(String alias) {
		if (!created)
			throw new vgl.core.exception.VGLAudioException("AudioManager is not created >> call AudioManager.create()");
		findEntryOrThrow(alias).getValue().pause();
	}

	public static void resume(String alias) {
		if (!created)
			throw new vgl.core.exception.VGLAudioException("AudioManager is not created >> call AudioManager.create()");
		findEntryOrThrow(alias).getValue().resume();
	}

	public static void stop(String alias) {
		if (!created)
			throw new vgl.core.exception.VGLAudioException("AudioManager is not created >> call AudioManager.create()");
		findEntryOrThrow(alias).getValue().stop();
	}

	/**
	 * Reconfigures the sound object found with the specified alias
	 * 
	 * @param alias
	 * @param newPitch
	 *            - the sound's new pitch value(0.0 - 10)
	 * @param newGain
	 *            - the sound's new gain value (0.0 - 1.0);
	 * @return the sound object for chained operations
	 * 
	 *         <br>
	 *         <b>throws {@link VGLAudioException} if the manager has not been
	 *         initialized or the sound by the given alias not found</b>
	 */
	public static Sound reconfigure(String alias, float newGain, float newPitch) {
		if (!created)
			throw new vgl.core.exception.VGLAudioException("AudioManager is not created >> call AudioManager.create()");
		Entry<String, Sound> mapEntry = findEntryOrThrow(alias);
		Sound s = mapEntry.getValue();
		s.setGain(newGain);
		s.setPitch(newPitch);
		return s;
	}

	/**
	 * Removes the sound referenced to by the specified alias
	 * 
	 * @param alias
	 * @return the sound object removed <br>
	 *         <b>throws {@link VGLAudioException} if the manager has not been
	 *         initialized or the sound by the given alias not found</b>
	 */
	public static Sound remove(String alias) {
		if (!created)
			throw new vgl.core.exception.VGLAudioException("AudioManager is not created >> call AudioManager.create()");
		Entry<String, Sound> mapEntry = findEntryOrThrow(alias);
		soundMap.remove(mapEntry.getKey(), mapEntry.getValue());
		return mapEntry.getValue();
	}

	/**
	 * Method to either find the sound object via an entry or throw <br>
	 * {@link VGLAudioException}
	 */
	private static java.util.Map.Entry<String, Sound> findEntryOrThrow(String entry) {
		return soundMap.entrySet().stream().filter((kvp) -> kvp.getKey().equals(entry)).findFirst().orElseThrow(
		        vgl.tools.functional.Functional.bind(VGLAudioException::new,
		                "Unable to find sound with name >> " + entry));
	}

	/**
	 * Deletes All audio related items in cache and disables Audio in VGL
	 */
	public static void destroy() {
		if (!created)
			throw new vgl.core.exception.VGLAudioException("AudioManager is not created >> call AudioManager.create()");
		created = false;
		AudioSystem.destroy();
	}

}
