package vgl.audio;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.AL_PITCH;

import com.shc.gwtal.client.openal.AL10;

import vgl.main.VGL;
import vgl.tools.IResource;

//============================================================================
//Name        : Sound
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : A basic sound implementation using OpenAL
//supporting operations such as playback, setting pitch and gain
//TODO        : Add pause, loop, remove the source details from here
//============================================================================
public class Sound implements IResource{

	private int			soundBuffer;

	private float		pitch;

	private float		gain;

	private int			currentSourceSlot;
	
	private boolean disposed;

	private SoundState	state;

	public Sound(int soundBuffer) {

		this.soundBuffer = soundBuffer;
		this.pitch = 1;
		this.gain = 0.5f;
		this.state = SoundState.STOPPED;
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

	private static void checks(boolean gain, float val) {
		if (gain) {
			if (val > 1f || val < 0f)
				throw new vgl.core.exception.VGLAudioException("Value must be between 1 and 0 , entered >> " + val);
		} else if (val <= 0f)
			throw new vgl.core.exception.VGLAudioException("Value must be greater than 0 , entered >> " + val);
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
	
	//TODO
	public void loop() {
		validate();
//		play();
//		VGL.api_afx.alSourcei(currentSourceSlot, AL10.AL_LOOPING, AL10.AL_TRUE);
	}

	public void stop() {
		validate();
		if (state != SoundState.STOPPED) {
			releaseHandleInternal();
		}
	}

	public void pause() {
		validate();
		if (state == SoundState.PLAYING) {
			VGL.api_afx.alSourcePause(currentSourceSlot);
			state = SoundState.PAUSED;
		}
	}

	public float getGain() {
		validate();
		return gain;
	}

	public void setGain(float gain) {
		validate();
		checks(true, gain);
		VGL.api_afx.alSourcef(currentSourceSlot, AL_GAIN, gain);
		this.gain = gain;
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

	public void setPitch(float pitch) {
		validate();
		checks(false, pitch);
		VGL.api_afx.alSourcef(currentSourceSlot, AL_PITCH, pitch);
		this.pitch = pitch;
	}

	@Override
	public void releaseMemory() {
		validate();
		VGL.api_afx.alDeleteBuffers(soundBuffer);
		this.disposed = true;
	}

	@Override
	public boolean isDisposed() {
		return disposed;
	}

}
