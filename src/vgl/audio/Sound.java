package vgl.audio;

import static com.shc.gwtal.client.openal.AL10.AL_BUFFER;
import static com.shc.gwtal.client.openal.AL10.AL_GAIN;
import static com.shc.gwtal.client.openal.AL10.AL_PITCH;

import com.shc.gwtal.client.openal.AL10;

import vgl.core.audio.AudioSourcePool;
import vgl.main.VGL;
import vgl.tools.IResource;
import vgl.tools.ISpecifier;

//============================================================================
//Name        : Sound
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : A basic sound implementation using OpenAL
//supporting operations such as playback, setting pitch and gain
//TODO        : Add pause, loop, remove the source details from here
//============================================================================
public class Sound implements IResource, ISpecifier<IResource.ResourceState> {

	private int				soundBuffer;

	private float			pitch;

	private float			gain;

	private int				currentSourceSlot;

	private boolean			disposed;

	private SoundState		state;

	private ResourceState	resState	= ResourceState.UNAVAILABLE;

	public Sound(int soundBuffer) {
		this.soundBuffer = soundBuffer;
		this.pitch = 1;
		this.gain = 0.5f;
		this.state = SoundState.STOPPED;
		this.resState = ResourceState.AVAILABLE;
	}

	// TODO Edit source properties on query success
	public void play() {
		validate();
		if (state == SoundState.PAUSED) {
			releaseHandleInternal();
		}
		currentSourceSlot = AudioSourcePool.queryFreeSourceSlot();
		if (currentSourceSlot == AudioSourcePool.ASP_NOT_FOUND)
			return;
		VGL.api_afx.alSourcei(currentSourceSlot, AL_BUFFER, soundBuffer);
		VGL.api_afx.alSourcef(currentSourceSlot, AL_GAIN, gain);
		VGL.api_afx.alSourcef(currentSourceSlot, AL_PITCH, pitch);
		VGL.api_afx.alSourcePlay(currentSourceSlot);
		this.state = SoundState.PLAYING;
	}

	private void releaseHandleInternal() {
		state = SoundState.STOPPED;
		VGL.api_afx.alSourceStop(currentSourceSlot);
	}

	public void resume() {
		validate();
		if (state == SoundState.PAUSED) {
			VGL.api_afx.alSourcePlay(currentSourceSlot);
			state = SoundState.PLAYING;
		}
	}

	// TODO
	public void loop() {
		validate();
		play();
		VGL.api_afx.alSourcei(currentSourceSlot, AL10.AL_LOOPING, AL10.AL_TRUE);
		this.state = SoundState.LOOPING;
	}

	public void stop() {
		validate();
		if (state != SoundState.STOPPED) {
			releaseHandleInternal();
		}
	}
	
	public void togglePause() {
		validate();
		if(state == SoundState.PLAYING || state == SoundState.LOOPING) {
			VGL.api_afx.alSourcePause(currentSourceSlot);
			state = SoundState.PAUSED;
		}
		else if(state == SoundState.PAUSED) {
			VGL.api_afx.alSourcePlay(currentSourceSlot);
			state = SoundState.PLAYING;
		}
	}
	
	

	public void pause() {
		validate();
		if (state == SoundState.PLAYING || state == SoundState.LOOPING) {
			VGL.api_afx.alSourcePause(currentSourceSlot);
			state = SoundState.PAUSED;
		}
	}

	public float getGain() {
		validate();
		return gain;
	}

	public Sound setGain(float gain) {
		validate();
		gain = vgl.maths.Maths.clamp(gain, 1f, 0f);
		if (state != SoundState.STOPPED)
			VGL.api_afx.alSourcef(currentSourceSlot, AL_GAIN, gain);
		this.gain = gain;
		return this;
	}

	public int getCurrentSourceSlot() {
		validate();
		return currentSourceSlot;
	}

	public SoundState getState() {
		validate();
		return state;
	}

	public float getPitch() {
		validate();
		return pitch;
	}

	public Sound setPitch(float pitch) {
		validate();
		pitch = vgl.maths.Maths.clamp(pitch, 10f, 0f);
		if (state != SoundState.STOPPED)
			VGL.api_afx.alSourcef(currentSourceSlot, AL_PITCH, pitch);
		this.pitch = pitch;
		return this;
	}

	@Override
	public void releaseMemory() {
		validate();
		resState = ResourceState.DISPOSED;
		VGL.api_afx.alDeleteBuffers(soundBuffer);
		this.disposed = true;
	}

	@Override
	public boolean isDisposed() {
		return disposed;
	}

	@Override
	public synchronized void specify(ResourceState t) {
		this.resState = t;
	}

	@Override
	public synchronized ResourceState getResourceState() {
		return resState;
	}

}
