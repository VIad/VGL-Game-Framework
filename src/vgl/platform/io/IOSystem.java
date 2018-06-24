package vgl.platform.io;

import vgl.core.buffers.MemoryBuffer;
import vgl.tools.functional.callback.Callback;

abstract public class IOSystem {

	abstract public void readBytes(final FileDetails file, final Callback<MemoryBuffer> result);

	abstract public MemoryBuffer readBytes(final FileDetails file);

	abstract public void readString(final FileDetails file, final Callback<String> result, ReadOption... options);

	abstract public String readString(final FileDetails file, ReadOption... options);
	
	abstract public FileDetails file(String file);
	
	abstract public void memset(MemoryBuffer buffer, int data);

}
