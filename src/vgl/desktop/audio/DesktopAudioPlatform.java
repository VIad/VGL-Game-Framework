package vgl.desktop.audio;

import java.util.Arrays;
import java.util.stream.Stream;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

import vgl.audio.IAudioPlatform;
import vgl.core.buffers.MemoryBuffer;

public class DesktopAudioPlatform implements IAudioPlatform{

	@Override
	public int alGetError() {
		return AL10.alGetError();
	}

	@Override
	public int alGenBuffer() {
		return AL10.alGenBuffers();
	}

	@Override
	public int alGenSources() {
		return AL10.alGenSources();
	}

	@Override
	public int[] alGenSources(int sourcesAmnt) {
		int[] sources = new int[sourcesAmnt];
		AL10.alGenSources(sources);
		return sources;
	}

	@Override
	public int[] alGenBuffers(int buffersAmnt) {
		int[] buffers = new int[buffersAmnt];
		AL10.alGenBuffers(buffers);
		return buffers;
	}

	@Override
	public void alBufferData(int buffer, int format, MemoryBuffer data, int freq) {
		AL10.alBufferData(buffer,
			           	  format,
			           	  ((java.nio.ByteBuffer)data.nativeBufferDetails().getBuffer()),
			           	  freq);
	}

	@Override
	public void alDeleteBuffers(int... buffers) {
		AL10.alDeleteBuffers(buffers);
	}

	@Override
	public void alDeleteSources(int... sources) {
		AL10.alDeleteSources(sources);
	}

	@Override
	public boolean alIsBuffer(int buffer) {
		return AL10.alIsBuffer(buffer);
	}

	@Override
	public void alDistanceModel(int modelName) {
		AL10.alDistanceModel(modelName);
	}

	@Override
	public boolean alIsSource(int sourceID) {
		return AL10.alIsSource(sourceID);
	}

	@Override
	public void alSource3f(int source, int param, float v0, float v1, float v2) {
		AL10.alSource3f(source, param, v0, v1, v2);
	}

	@Override
	public void alSourcef(int source, int param, float val) {
		AL10.alSourcef(source, param, val);
	}

	@Override
	public void alSourcei(int source, int param, int value) {
		AL10.alSourcei(source, param, value);
	}

	@Override
	public void alSource3i(int source, int param, int v0, int v1, int v2) {
		AL11.alSource3i(source, param, v0, v1, v2);
	}

	@Override
	public void alSourcePlay(int source) {
		AL10.alSourcePlay(source);
	}

	@Override
	public void alSourceStop(int source) {
		AL10.alSourceStop(source);
	}

	@Override
	public void alSourcePause(int source) {
		AL10.alSourcePause(source);
	}

	@Override
	public void alSourceRewind(int source) {
		AL10.alSourceRewind(source);
	}

	@Override
	public void alGetSource3f(int source, int param, float[] v0, float[] v1, float[] v2) {
		if (!Validations.hasEnoughSpace(1, v0, v1, v2))
			throw new vgl.core.exception.VGLAudioException("Not enough space in arrays to invoke alGet function");
		AL10.alGetSource3f(source, param, v0, v1, v2);
	}

	@Override
	public float alGetSourcef(int source, int param) {
		return AL10.alGetSourcef(source, param);
	}

	@Override
	public int alGetSourcei(int source, int param) {
		return AL10.alGetSourcei(source, param);
	}

	@Override
	public void alListener3f(int param, float v0, float v1, float v2) {
		AL10.alListener3f(param, v0, v1, v2);
	}

	@Override
	public void alListener3i(int param, int v0, int v1, int v2) {
		AL11.alListener3i(param, v0, v1, v2);
	}

	@Override
	public void alListeneri(int param, int v) {
		AL10.alListeneri(param, v);
	}

	@Override
	public void alListenerf(int param, float v) {
		AL10.alListenerf(param, v);
	}

	@Override
	public void alGetListener3f(int param, float[] v0, float[] v1, float[] v2) {
		if (!Validations.hasEnoughSpace(1, v0, v1, v2))
			throw new vgl.core.exception.VGLAudioException("Not enough space in arrays to invoke alGet function");
		AL10.alGetListener3f(param, v0, v1, v2);
	}

	@Override
	public void alGetListener3i(int param, int[] v0, int[] v1, int[] v2) {
		if (!Validations.hasEnoughSpace(1, v0, v1, v2))
			throw new vgl.core.exception.VGLAudioException("Not enough space in arrays to invoke alGet function");
		int[] values = new int[3];
		AL11.alGetListeneriv(param,values);
		v0[0] = values[0];
		v1[0] = values[1];
		v2[0] = values[2];
	}

	@Override
	public float alGetListenerf(int param) {
		return AL10.alGetListenerf(param);
	}

	@Override
	public int alGetListeneri(int param) {
		return AL10.alGetListeneri(param);
	}

	@Override
	public int alGetBufferi(int buffer, int param) {
		return AL10.alGetBufferi(buffer, param);
	}
	
	private static class Validations{

		public static boolean hasEnoughSpace(int i, float[]... arrays) {
			for(float[] arr : arrays) {
				if(arr.length < i)
					return false;
			}
			return true;
		}
		
		public static boolean hasEnoughSpace(int i, int[]... arrays) {
			for(int[] arr : arrays) {
				if(arr.length < i)
					return false;
			}
			return true;
		}
		
	}
	
	private long deviceHandle, alContextHandle;

	@Override
	public void setupAudioContext() {
		deviceHandle = ALC10.alcOpenDevice((java.nio.ByteBuffer) null);
		ALCCapabilities deviceCapabilities = ALC.createCapabilities(deviceHandle);
		alContextHandle = ALC10.alcCreateContext(deviceHandle, (java.nio.IntBuffer) null);
		if (alContextHandle == AudioSystem.NULL)
			throw new vgl.core.exception.VGLAudioException("Unable to create AL Handle");
		ALC10.alcMakeContextCurrent(alContextHandle);
		AL.createCapabilities(deviceCapabilities);
	}
	
	@Override
	public void destroyOnExit() {
		ALC10.alcDestroyContext(alContextHandle);
		ALC10.alcCloseDevice(deviceHandle);
	}

}
