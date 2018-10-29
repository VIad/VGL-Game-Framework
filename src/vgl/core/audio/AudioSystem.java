package vgl.core.audio;

import org.lwjgl.openal.AL10;

import vgl.main.VGL;

//============================================================================
//Name        : AudioSystem
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : Represents a higher level wrapper of AL's initialization 
//functions and manages the source pool's size
//TODO        : To later implement management for 3D audio
//============================================================================

/**
 * 
 * 
 *
 */
public class AudioSystem {

	static boolean					initialized	= false;

	public static final int			NULL		= 0;

	/**
	 * Initializes the AudioSystem with the specified pool size
	 */
	public static void initialize(int sourcePoolSize) {
		initialized = true;
		VGL.api_afx.setup();
		AudioSourcePool.create(sourcePoolSize);
		setListener();
	}

	/**
	 * Initializes the AudioSystem with the default pool size : 20
	 */
	public static void initialize() {
		initialize(20);
	}

	/**
	 * TODO Work on, create a listener class, w/ params
	 */
	private static void setListener() {
		VGL.api_afx.alListener3f(AL10.AL_POSITION, 0f, 0f, 0f);
		VGL.api_afx.alListener3f(AL10.AL_VELOCITY, 0f, 0f, 0f);
	}

	/**
	 * Call this on <code> Application.finish() </code> or whenever you want to stop
	 * using audio if you have initialized the AudioSystem to clean up after usage
	 */
	public static void destroy() {
		VGL.api_afx.destroyOnExit();
		AudioSourcePool.destroy();
		initialized = false;
	}

	public static boolean isInitialized() {
		return initialized;
	}

}
