package vgl.desktop.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

import vgl.core.buffers.MemoryBuffer;
import vgl.platform.io.FileDetails;
import vgl.platform.io.IOSystem;
import vgl.platform.io.ReadOption;
import vgl.tools.functional.callback.Callback;

public class DesktopIOSystem extends IOSystem {

	@Override
	public void readBytes(FileDetails file, Callback<MemoryBuffer> result) {
		try {
			MemoryBuffer buffer = MemoryBuffer.wrap(
			        Files.readAllBytes(Paths.get(new File(file.absolutePath()).toURI())));
			result.invoke(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public MemoryBuffer readBytes(FileDetails file) {
		try {
			return MemoryBuffer.wrap(Files.readAllBytes(Paths.get(new File(file.absolutePath()).toURI())));
		} catch (IOException e) {
			return MemoryBuffer.EMPTY_BUFFER;
		}
	}

	@Override
	public void readString(FileDetails file, Callback<String> result, ReadOption... options) {
		boolean utf8 = Arrays.stream(options).anyMatch(option -> option == ReadOption.READ_UTF8);
		boolean discardNwl = Arrays.stream(options).anyMatch(option -> option == ReadOption.IGNORE_NEWLINES);
		try {
			if (utf8)
				result.invoke(
				        Files.readAllLines(Paths.get(file.absolutePath()), StandardCharsets.UTF_8).stream().collect(
				                Collectors.joining(discardNwl ? "" : "\n")).toString());
			else
				result.invoke(Files.readAllLines(Paths.get(file.absolutePath())).stream().collect(
				        Collectors.joining(discardNwl ? "" : "\n")).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

}
