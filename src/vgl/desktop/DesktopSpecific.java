package vgl.desktop;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;

import org.lwjgl.openal.AL10;

import vgl.core.gfx.Color;
import vgl.core.gfx.Image;
import vgl.core.gfx.Image.Format;
import vgl.desktop.audio.OggReader;
import vgl.desktop.audio.TMWaveData;
import vgl.desktop.tools.async.VoidWorker;
import vgl.main.VGL;
import vgl.maths.vector.Matrix4f;
import vgl.tools.IResource;
import vgl.tools.ISpecifier;
import vgl.tools.IResource.ResourceState;
import vgl.tools.functional.callback.Callback;
import vgl.tools.managers.Managers;

abstract public class DesktopSpecific {
	
	static {
		
	}
	
	abstract public static class FileLoading {

		public static Image loadImage0(String absPath) throws Throwable {
			Image result = null;
			BufferedImage buffered = ImageIO.read(new File(absPath));
			result = new Image(buffered.getWidth(), buffered.getHeight(), Format.ARGB);
			for (int y = 0; y < buffered.getHeight(); y++) {
				for (int x = 0; x < buffered.getWidth(); x++) {
					result.setPixel(x, y, Color.fromARGB(buffered.getRGB(x, y)));
				}
			}
			((ISpecifier<IResource.ResourceState>) result).specify(ResourceState.AVAILABLE);
			return result;
		}
		
		public static void readAll(final File f, final Callback<String> onCompletion) {
			readAll(f, onCompletion, true);
		}

		public static void readAll(final File f, final Callback<String> onCompletion, final boolean keepNewLines) {
			new VoidWorker<File>(f, (file) -> {
				String contents = readAllSync(file);
				onCompletion.invoke(contents);
			}).start();
		}

		public static void readALLUTF8(final File f, final Callback<String> onCompletion) {
			new VoidWorker<File>(f, (file) -> {
				String utf8Read = readAllUTF8Sync(f, true);
				onCompletion.invoke(utf8Read);
			}).start();
		}

		public static void readBytes(final File f, final Callback<byte[]> onCompletion) {
			new VoidWorker<File>(f, (file) -> {
				byte[] bytes = readBytesSync(f);
				onCompletion.invoke(bytes);
			}).start();
		}

		public static void readBytes(final String filePath, final Callback<byte[]> onCompletion) {
			readBytes(new File(filePath), onCompletion);
		}

		public static String readAllSync(final File f, boolean keepNewLines) {
			try {
				final FileReader reader = new FileReader(f);
				final BufferedReader brReader = new BufferedReader(reader);
				String line;
				;
				final StringBuilder builder = new StringBuilder();
				while ((line = brReader.readLine()) != null) {
					builder.append(line);
					if (keepNewLines)
						builder.append('\n');
				}
				reader.close();
				brReader.close();
				return builder.toString();
			} catch (final IOException e) {
				throw new RuntimeException("Error reading file >> " + e.getMessage());
			} finally {

			}
		}

		public static byte[] readBytesSync(final File f) {
			try {
				FileInputStream fis = new FileInputStream(f);
				byte[] buffer = new byte[(int) f.length()];
				fis.read(buffer);
				fis.close();
				return buffer;
			} catch (IOException e) {
				return null;
			}
		}

		public static byte[] readBytesSync(final String filePath) {
			return readBytesSync(new File(filePath));
		}

		public static ByteBuffer readBytesBufferSync(final File f) {
			return ByteBuffer.wrap(readBytesSync(f));
		}

		public static String readAllSync(final String filePath) {
			return readAllSync(new File(filePath), true);
		}

		public static String readAllSync(final File filePath) {
			return readAllSync(filePath, true);
		}

		public static String readAllSync(final String filePath, boolean keepNewLines) {
			return readAllSync(new File(filePath), keepNewLines);
		}

		public static String readAllUTF8Sync(final File f, boolean keepNewLines) {
			try {
				final InputStreamReader reader = new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8);
				final BufferedReader rd = new BufferedReader(reader);
				String line;
				final StringBuilder builder = new StringBuilder();
				while ((line = rd.readLine()) != null) {
					builder.append(line);
					if (keepNewLines)
						builder.append('\n');
				}
				reader.close();
				rd.close();
				return builder.toString();
			} catch (final IOException e) {
				throw new RuntimeException("Error reading file >> " + e.getMessage());
			}
		}

		public static String readAllUTF8Sync(final String filePath, boolean keepNewLines) {
			return readAllUTF8Sync(new File(filePath), keepNewLines);
		}
		
		public static void writeStringUTF8(String contents, File destination) {
			new vgl.desktop.tools.async.VoidWorker<String>(contents, (s) -> {
				try {
					FileOutputStream fos;
					OutputStreamWriter osr = new OutputStreamWriter(
					        fos = new FileOutputStream(destination),
					        StandardCharsets.UTF_8);
					osr.write(s);
					osr.close();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).start();
		}

		public static void writeString(String contents, File destination) {
			new vgl.desktop.tools.async.VoidWorker<String>(contents, (s) -> {
				try {
					FileWriter writer = new FileWriter(destination);
					writer.write(s);
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).start();
		}

		public static void writeBytes(byte[] bytes, File destination) {
			new vgl.desktop.tools.async.VoidWorker<byte[]>(bytes, (b) -> {
				try {
					FileOutputStream fos = new FileOutputStream(destination);
					fos.write(bytes);
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).start();
		}

		public static void writeBytes(ByteBuffer bytes, File destination) {
			writeBytes(bytes.array(), destination);
		}

	}
	
	abstract public static class AudioDecoder{
		
		public static int decodeOGG(String file) {
			OggReader reader = new OggReader(file);
			int buffer = AL10.alGenBuffers();
			AL10.alBufferData(buffer, reader.getFormat().toALFormat(), reader.getData(), reader.getSampleRate());
			return buffer;
		}

		public static int decodeAudio(String file) {
			int buffer = AL10.alGenBuffers();
			TMWaveData waveFile = null;
			try {
				waveFile = TMWaveData.create(file);
			} catch (FileNotFoundException e) {
				VGL.errorChannel
				   .forward(() -> e);
			}
			AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
			waveFile.dispose();
			return buffer;
		}
		
	}
	
	abstract public static class Memory{
		
		public static FloatBuffer store(Matrix4f m4f, FloatBuffer buffer) {
			buffer.put(m4f.m00);
			buffer.put(m4f.m01);
			buffer.put(m4f.m02);
			buffer.put(m4f.m03);
			buffer.put(m4f.m10);
			buffer.put(m4f.m11);
			buffer.put(m4f.m12);
			buffer.put(m4f.m13);
			buffer.put(m4f.m20);
			buffer.put(m4f.m21);
			buffer.put(m4f.m22);
			buffer.put(m4f.m23);
			buffer.put(m4f.m30);
			buffer.put(m4f.m31);
			buffer.put(m4f.m32);

			buffer.put(m4f.m33);
			buffer.flip();
			return buffer;
		}

		
		public static IntBuffer storeDataInIntBuffer(final int[] data) {
			final IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).asIntBuffer();
			buffer.put(data);
			buffer.flip();
			return buffer;
		}

		
		public static FloatBuffer storeInFloatBuffer(final float[] data) {
			final FloatBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).asFloatBuffer();
			buffer.put(data);
			buffer.flip();
			return buffer;
		}
	}

	abstract public static class RuntimeFontFactory{
		
//		public static IFont makeFont() {
//			
//		}
		
	}
	
}
