package vgl.desktop.io;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.lwjgl.system.MemoryUtil;

import vgl.core.buffers.Buffers;
import vgl.core.buffers.MemoryBuffer;
import vgl.core.gfx.Image;
import vgl.desktop.DesktopSpecific;
import vgl.desktop.tools.async.VoidWorker;
import vgl.main.VGL;
import vgl.platform.io.FileDetails;
import vgl.platform.io.IOSystem;
import vgl.platform.io.ReadOption;
import vgl.tools.functional.callback.BinaryCallback;
import vgl.tools.functional.callback.Callback;

public class DesktopIOSystem extends IOSystem {

	@Override
	public void readBytes(FileDetails file, Callback<MemoryBuffer> result) {
		new VoidWorker<>(file, f -> {
			result.invoke(readBytes(f));
		}).start(); 
	}

	@Override
	public MemoryBuffer readBytes(FileDetails file) {
		try {
			return Buffers.wrap(
					Files.readAllBytes(Paths.get(new File(file.absolutePath()).toURI())));
		} catch (IOException e) {
			return MemoryBuffer.EMPTY_BUFFER;
		}
	}

	@Override
	public void readString(FileDetails file, Callback<String> result, ReadOption... options) {
		new VoidWorker<>(file, f -> {
			result.invoke(readString(f, options));
		}).start(); 
	}

	@Override
	public String readString(FileDetails file, ReadOption... options) {
		boolean utf8 = Arrays.stream(options).anyMatch(option -> option == ReadOption.READ_UTF8);
		boolean discardNwl = Arrays.stream(options).anyMatch(option -> option == ReadOption.IGNORE_NEWLINES);
		try {
			if (utf8)
				return
				        Files.readAllLines(Paths.get(file.absolutePath()), StandardCharsets.UTF_8).stream().collect(
				                Collectors.joining(discardNwl ? "" : "\n")).toString();
			else
				return
						Files.readAllLines(Paths.get(file.absolutePath())).stream().collect(
				        Collectors.joining(discardNwl ? "" : "\n")).toString();
		} catch (IOException e) {
			return "";
		}
	}

	@Override
	public void memset(MemoryBuffer buffer, int data) {
		MemoryUtil.memSet(((ByteBuffer) buffer.nativeBufferDetails().getBuffer()), data);
	}

	@Override
	public void readImage(FileDetails file, BinaryCallback<Image, Throwable> result) {
		new VoidWorker<>(file, f -> {
			try {
				result.invoke(DesktopSpecific.FileLoading
						                     .loadImage0(file.absolutePath()), null);
			} catch (Throwable e) {
				result.invoke(null, e);
			}
		}).start(); 
	}

}
