package vgl.desktop.audio;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.AL_PITCH;
import static org.lwjgl.openal.AL10.alSourcePause;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcei;

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

	public Sound(String file) {
		if (file.endsWith(".ogg"))
			soundBuffer = AudioSystem.loadOGG(file);
		else
			soundBuffer = AudioSystem.loadSound(file);
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
		alSourcei(currentSourceSlot, AL_BUFFER, soundBuffer);
		alSourcef(currentSourceSlot, AL_GAIN, gain);
		alSourcef(currentSourceSlot, AL_PITCH, pitch);
		alSourcePlay(currentSourceSlot);
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
		alSourceStop(currentSourceSlot);
	}

	public void resume() {
		if (state == SoundState.PAUSED) {
			alSourcePlay(currentSourceSlot);
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
			alSourcePause(currentSourceSlot);
			state = SoundState.PAUSED;
		}
	}

	public float getGain() {
		return gain;
	}

	public void setGain(float gain) {
		checks(true, gain);
		alSourcef(currentSourceSlot, AL_GAIN, gain);
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
		alSourcef(currentSourceSlot, AL_PITCH, pitch);
		this.pitch = pitch;
	}

}
