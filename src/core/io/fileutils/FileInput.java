package vgl.core.io.fileutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import vgl.tools.functional.callback.Callback;

public class FileInput {

	private FileInput() {

	}

	public static void readAll(final File f, final Callback<String> onCompletion) {
		readAll(f, onCompletion, true);
	}

	public static void readAll(final File f, final Callback<String> onCompletion, final boolean keepNewLines) {
		new Thread(() -> {
			String result = readAllSync(f, keepNewLines);
			onCompletion.invoke(result);
		}).start();
	}

	public static void readALLUTF8(final File f, final Callback<String> onCompletion) {
		new Thread(() -> {
			String utf8Read = readAllUTF8Sync(f, true);
			onCompletion.invoke(utf8Read);
		}).start();
	}

	public static void readBytes(final File f, final Callback<byte[]> onCompletion) {
		new Thread(() -> {
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

}