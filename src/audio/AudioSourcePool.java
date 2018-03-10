package vgl.audio;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL10;

import vgl.core.annotation.VGLInternal;

import static org.lwjgl.openal.AL10.*;
//============================================================================
//Name        : AudioSourcePool
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : Basic AL Source pool implementation with ability to query 
//free source slots
//============================================================================

/**
 * Source pool implementation for playing audio
 */
public class AudioSourcePool {

	private static int				POOL_SIZE		= 32;

	/**
	 * Returned from <code> queryFreeSourceSlot </code> if unable to find a slot
	 */
	public static final int			ASP_NOT_FOUND	= -0xff;

	private static List<Integer>	sourcesHandle;

	static {
		sourcesHandle = new ArrayList<>();
	}

	static void createSources() {
		for (int i = 0; i < POOL_SIZE; i++) {
			int sourceId = alGenSources();
			alSource3f(sourceId, AL_POSITION, 0f, 0f, 0f);
			sourcesHandle.add(sourceId);
		}
	}

	static void create(int poolSize) {
		AudioSourcePool.POOL_SIZE = poolSize;
		createSources();
	}

	static void create() {
		create(10);
	}

	static void destroy() {
		sourcesHandle.forEach(AL10::alDeleteSources);
	}

	/**
	 * Method returns the handle of a free alSource
	 * 
	 * @return the handle or ASP_NOT_FOUND if unable to find a slot
	 */
	@VGLInternal
	public static int queryFreeSourceSlot() {
		for (int source : sourcesHandle) {
			if (alGetSourcei(source, AL_SOURCE_STATE) != AL_PLAYING
			        && alGetSourcei(source, AL_SOURCE_STATE) != AL_PAUSED) {
				return source;
			}
		}
		return ASP_NOT_FOUND;
	}
}
