package vgl.core.audio.al;

import org.lwjgl.openal.AL10;

//============================================================================
//Name        : ALFormat
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : Supported Audio loading formats
//============================================================================
public enum ALFormat {

	/**
	 * 16 bit single channel AL Format
	 */
	MONO_16,
	/**
	 * 16 bit dual channel AL Format
	 */
	STEREO_16;
	/**
	 * Converts the enum into an OpenAL supported format
	 */
	public int toALFormat() {
		switch (this)
		{
			default:
				throw new vgl.core.exception.VGLFatalError("Not possible");
			case MONO_16:
				return AL10.AL_FORMAT_MONO16;
			case STEREO_16:
				return AL10.AL_FORMAT_STEREO16;

		}
	}

}
