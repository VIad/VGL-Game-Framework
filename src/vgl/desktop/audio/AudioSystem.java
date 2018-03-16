package vgl.desktop.audio;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

import vgl.core.annotation.VGLInternal;

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

	private static long				deviceHandle;
	private static long				alContextHandle;

	public static final int			NULL		= 0;

	private static List<Integer>	buffers		= new ArrayList<>();

	/**
	 * Initializes the AudioSystem with the specified pool size
	 */
	public static void initialize(int sourcePoolSize) {
		initialized = true;
		deviceHandle = ALC10.alcOpenDevice((java.nio.ByteBuffer) null);
		ALCCapabilities deviceCapabilities = ALC.createCapabilities(deviceHandle);
		alContextHandle = ALC10.alcCreateContext(deviceHandle, (java.nio.IntBuffer) null);
		if (alContextHandle == NULL)
			throw new vgl.core.exception.VGLAudioException("Unable to create AL Handle");
		ALC10.alcMakeContextCurrent(alContextHandle);
		AL.createCapabilities(deviceCapabilities);
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
		AL10.alListener3f(AL10.AL_POSITION, 0f, 0f, 0f);
		AL10.alListener3f(AL10.AL_VELOCITY, 0f, 0f, 0f);
	}

	static int loadOGG(String file) {
		OggReader reader = new OggReader(file);
		int buffer = AL10.alGenBuffers();
		buffers.add(buffer);
		AL10.alBufferData(buffer, reader.getFormat().toALFormat(), reader.getData(), reader.getSampleRate());
		return buffer;
	}

	static int loadSound(String file) {
		int buffer = AL10.alGenBuffers();
		buffers.add(buffer);
		TMWaveData waveFile = TMWaveData.create(file);
		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
		return buffer;
	}

	/**
	 * Call this on <code> Application.finish() </code> or whenever you want to stop
	 * using audio if you have initialized the AudioSystem to clean up after usage
	 */
	public static void destroy() {
		buffers.forEach(AL10::alDeleteBuffers);
		ALC10.alcDestroyContext(alContextHandle);
		ALC10.alcCloseDevice(deviceHandle);
		AudioSourcePool.destroy();
		initialized = false;
	}

	public static boolean isInitialized() {
		return initialized;
	}

}
