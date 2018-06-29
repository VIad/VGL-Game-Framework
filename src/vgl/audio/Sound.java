package vgl.audio;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.AL_PITCH;

import vgl.desktop.audio.AudioSystem;
import vgl.main.VGL;

//============================================================================
//Name        : Sound
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : A basic sound implementation using OpenAL
//supporting operations such as playback, setting pitch and gain
//TODO        : Add pause, loop, remove the source details from here
//============================================================================
public class Sound {

	private int			soundBuffer;

	private float		pitch;

	private float		gain;

	private int			currentSourceSlot;

	private SoundState	state;

	public Sound(int soundBuffer) {
//		if (file.endsWith(".ogg"))
//			soundBuffer = AudioSystem.loadOGG(file);
//		else
//			soundBuffer = AudioSystem.loadSound(file);
		this.soundBuffer = soundBuffer;
		this.pitch = 1;
		this.gain = 0.5f;
		this.state = SoundState.STOPPED;
	}

	// TODO Edit source properties on query success
	public void play() {
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
		if (state == SoundState.PAUSED) {
			VGL.api_afx.alSourcePlay(currentSourceSlot);
			state = SoundState.PLAYING;
		}
	}

	public void stop() {
		if (state != SoundState.STOPPED) {
			releaseHandleInternal();
		}
	}

	public void pause() {
		if (state == SoundState.PLAYING) {
			VGL.api_afx.alSourcePause(currentSourceSlot);
			state = SoundState.PAUSED;
		}
	}

	public float getGain() {
		return gain;
	}

	public void setGain(float gain) {
		checks(true, gain);
		VGL.api_afx.alSourcef(currentSourceSlot, AL_GAIN, gain);
		this.gain = gain;
	}

	public SoundState getState() {
		return state;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		checks(false, pitch);
		VGL.api_afx.alSourcef(currentSourceSlot, AL_PITCH, pitch);
		this.pitch = pitch;
	}

}
