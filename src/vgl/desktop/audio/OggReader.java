package vgl.desktop.audio;

import static org.lwjgl.stb.STBVorbis.stb_vorbis_close;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_get_info;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_get_samples_short_interleaved;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_open_filename;
import static org.lwjgl.stb.STBVorbis.stb_vorbis_stream_length_in_samples;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryUtil;

import vgl.core.audio.al.ALFormat;

/**
 * 
 * @author Sri Harsha Chilakapati <br>
 *         <br>
 *         <br>
 *         <b> Class kindly borrowed from the SilenceEngine <br>
 *         https://github.com/sriharshachilakapati/SilenceEngine <br>
 * 
 *         with slight modification concerning the loading of dual channel
 *         audio</b>
 * 
 *
 */
public class OggReader {

	private ByteBuffer	data;

	private int			sampleRate;

	private ALFormat	format;

	public OggReader(String file) {
		decodeToPCM(file);
	}

	private void decodeToPCM(String input) {
		IntBuffer error = MemoryUtil.memAllocInt(1);
		long handle = stb_vorbis_open_filename(input, error, null);
		if (handle == 0)
			throw new RuntimeException("Error " + error.get(0) + ": decoding the OGG data");

		STBVorbisInfo info = STBVorbisInfo.malloc();
		stb_vorbis_get_info(handle, info);

		int channels = info.channels();
		sampleRate = info.sample_rate();

		format = channels == 1 ? ALFormat.MONO_16 : ALFormat.STEREO_16;
		int numSamples = stb_vorbis_stream_length_in_samples(handle);
		ByteBuffer pcm = ByteBuffer.allocateDirect(numSamples * Short.BYTES * channels);
		stb_vorbis_get_samples_short_interleaved(handle, channels, pcm.asShortBuffer());
		
		data = pcm;

		stb_vorbis_close(handle);
		info.free();
	}

	/**
	 * Returns the raw bytes of the data prepared for buffering via OpenAL
	 */
	public ByteBuffer getData() {
		return data;
	}

	/**
	 * Returns the sample rate of the decoded file
	 */
	public int getSampleRate() {
		return sampleRate;
	}

	/**
	 * Returns the format of the decoded file
	 */
	public ALFormat getFormat() {
		return format;
	}
}