package vgl.platform.gl;

import vgl.core.internal.Checks;

public abstract class GLIndexBuffer {

	protected final int	buffer_ID;
	protected final int	count;

	public GLIndexBuffer(final int[] data) {
		Checks.checkIfInitialized();
		Checks.checkCanInstantiate(getClass());
		buffer_ID = newBuffer();
		storeData(data);
		count = data.length;
	}

	abstract protected int newBuffer();

	abstract protected void storeData(int[] data);

	abstract public void bind();

	abstract public void unbind();

	public int getBuffer_ID() {
		return buffer_ID;
	}

	public int count() {
		return count;
	}

}
