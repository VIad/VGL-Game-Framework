package vgl.platform.io;

import vgl.core.annotation.SupportedPlatforms;
import vgl.core.buffers.MemoryBuffer;
import vgl.core.gfx.Image;
import vgl.platform.Platform;
import vgl.tools.functional.callback.BinaryCallback;
import vgl.tools.functional.callback.Callback;

abstract public class IOSystem {

	abstract public void readBytes(final FileDetails file, final Callback<MemoryBuffer> result);

	abstract public void readString(final FileDetails file, final Callback<String> result, ReadOption... options);
	
	@SupportedPlatforms(values = { Platform.DESKTOP_X64,
			                       Platform.DESKTOP_X86})
	abstract public MemoryBuffer readBytes(final FileDetails file);
	
	@Deprecated
	abstract public String readString(final FileDetails file, ReadOption... options);
	
	abstract public void memset(MemoryBuffer buffer, int data);
	
	abstract public void readImage(FileDetails file, BinaryCallback<Image, Throwable> result);

}
