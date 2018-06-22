package vgl.core.buffers;

public class BufferDetails {

	private Object	buffer;
	private int		sizeBytes;

	public BufferDetails(Object buffer, int sizeBytes) {
		this.buffer = buffer;
		this.sizeBytes = sizeBytes;
		
	}

	public Object getBuffer() {
		return buffer;
	}

	public int getSizeBytes() {
		return sizeBytes;
	}

}
