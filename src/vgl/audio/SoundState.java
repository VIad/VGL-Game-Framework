package vgl.audio;

import org.lwjgl.openal.AL10;

public enum SoundState {

	PLAYING,

	PAUSED,
	
	LOOPING,

	STOPPED;

	public int toALFormat() {
		switch (this)
		{
			default:
				throw new vgl.core.exception.VGLFatalError("Not possible");
			case PAUSED:
				return AL10.AL_PAUSED;
			case PLAYING:
				return AL10.AL_PLAYING;
			case STOPPED:
				return AL10.AL_STOPPED;
			case LOOPING:
				return AL10.AL_LOOPING;
		}
	}

}
