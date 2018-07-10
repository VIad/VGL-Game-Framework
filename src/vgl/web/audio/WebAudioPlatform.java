package vgl.web.audio;

import com.shc.gwtal.client.openal.AL;
import com.shc.gwtal.client.openal.AL10;
import com.shc.gwtal.client.openal.ALCCapabilities;
import com.shc.gwtal.client.openal.ALContext;
import com.shc.gwtal.client.webaudio.AudioContext;
import com.shc.gwtal.client.webaudio.AudioContextException;
import com.vgl.gwtreq.client.GWTDataView;

import vgl.core.audio.AudioSourcePool;
import vgl.core.audio.IAudioPlatform;
import vgl.core.buffers.MemoryBuffer;
import vgl.core.exception.PlatformUnsupportedException;
import vgl.core.exception.VGLAudioException;
import vgl.main.VGL;
import vgl.tools.functional.Functional;

public class WebAudioPlatform implements IAudioPlatform {

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
		return AL10.alGenSources(sourcesAmnt);
	}

	@Override
	public int[] alGenBuffers(int buffersAmnt) {
		return AL10.alGenBuffers(buffersAmnt);
	}

	@Override
	public void alBufferData(int buffer, int format, MemoryBuffer data, int freq) {
		AL10.alBufferData(buffer, format, ((GWTDataView) data.nativeBufferDetails().getBuffer()).getView().buffer(),
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
		return AL10.alIsBuffer(buffer) == AL10.AL_TRUE;
	}

	@Override
	public void alDistanceModel(int modelName) {
		AL10.alDistanceModel(modelName);
	}

	@Override
	public boolean alIsSource(int sourceID) {
		return AL10.alIsSource(sourceID) == AL10.AL_TRUE;
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
		AL10.alSource3i(source, param, v0, v1, v2);
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
		throw new PlatformUnsupportedException("This instance of alGet is not supported on Web platform");
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
		// AL11.alListener3i(param, v0, v1, v2);
		AL10.alListener3i(param, v0, v1, v2);
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
		throw new PlatformUnsupportedException("This instance of alGet is not supported on Web platform");
	}

	@Override
	public void alGetListener3i(int param, int[] v0, int[] v1, int[] v2) {
		throw new PlatformUnsupportedException("This instance of alGet is not supported on Web platform");
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

	private AudioContext	context;
	private ALContext		alContext;

	private boolean initialized = false;
	
	@Override
	public void setupAudioContext() {
		try {
			context = AudioContext.create();
		} catch (AudioContextException e) {
			VGL.errorChannel.forward(
			        Functional.bind(VGLAudioException::new, "Unable to create audio context, check browser support"));
		}

		try {
			alContext = ALContext.create();
			AL.setCurrentContext(alContext);
		} catch (AudioContextException e) {
			VGL.errorChannel.forward(
			        Functional.bind(VGLAudioException::new, "Unable to create AL context, check browser support"));
		}
		AudioSourcePool.create(10);
		initialized = true;
	}

	@Override
	public void destroyOnExit() {
		alContext.destroy();
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

}
