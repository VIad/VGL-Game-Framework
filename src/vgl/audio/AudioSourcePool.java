package vgl.audio;


import java.util.ArrayList;
import java.util.List;

import com.shc.gwtal.client.openal.AL10;

import vgl.core.annotation.VGLInternal;
//============================================================================
//Name        : AudioSourcePool
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : Basic AL Source pool implementation with ability to query 
//free source slots
//============================================================================
import vgl.main.VGL;

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
			int sourceId = VGL.api_afx.alGenSources();
			VGL.api_afx.alSource3f(sourceId, AL10.AL_POSITION, 0f, 0f, 0f);
			sourcesHandle.add(sourceId);
		}
	}

	public static void create(int poolSize) {
		AudioSourcePool.POOL_SIZE = poolSize;
		createSources();
	}

	static void create() {
		create(10);
	}

	public static void destroy() {
		sourcesHandle.forEach(VGL.api_afx::alDeleteSources);
	}

	/**
	 * Method returns the handle of a free alSource
	 * 
	 * @return the handle or ASP_NOT_FOUND if unable to find a slot
	 */
	@VGLInternal
	public static int queryFreeSourceSlot() {
		return sourcesHandle.stream()
		                    .filter(source -> VGL.api_afx.alGetSourcei(source, AL10.AL_SOURCE_STATE) != AL10.AL_PAUSED)
		                    .filter(source -> VGL.api_afx.alGetSourcei(source, AL10.AL_SOURCE_STATE) != AL10.AL_PLAYING)
		                    .findFirst()
		                    .orElse(ASP_NOT_FOUND);
	}
}
