package vgl.audio;

import vgl.core.buffers.MemoryBuffer;

public interface IAudioPlatform {

	/*
	 * Creating AL Objects
	 */
	int alGetError();

	int alGenBuffer();

	int alGenSources();

	int[] alGenSources(int sourcesAmnt);

	int[] alGenBuffers(int buffers);

	/*
	 * Buffering audio data
	 */
	void alBufferData(int buffer, int format, MemoryBuffer data, int freq);

	void alDeleteBuffers(int... buffers);

	//Modifying buffer data | getting info
	void alDeleteSources(int... sources);

	boolean alIsBuffer(int buffer);

	void alDistanceModel(int modelName);

	boolean alIsSource(int sourceID);

	void alSource3f(int source, int param, float v0, float v1, float v2);

	void alSourcef(int source, int param, float val);

	void alSourcei(int source, int param, int value);

	void alSource3i(int source, int param, int v0, int v1, int v2);

	void alSourcePlay(int source);

	void alSourceStop(int source);

	void alSourcePause(int source);

	void alSourceRewind(int source);

	void alGetSource3f(int source, int param, float[] v0, float[] v1, float[] v2);

	float alGetSourcef(int source, int param);

	int alGetSourcei(int source, int param);

	void alListener3f(int param, float v0, float v1, float v2);

	void alListener3i(int param, int v0, int v1, int v2);

	void alListeneri(int param, int v);

	void alListenerf(int param, float v);

	void alGetListener3f(int param, float[] v0, float[] v1, float[] v2);

	void alGetListener3i(int param, int[] v0, int[] v1, int[] v2);

	float alGetListenerf(int param);

	int alGetListeneri(int param);

	int alGetBufferi(int buffer, int param);
	
	void setupAudioContext();
	
	void destroyOnExit();
}
