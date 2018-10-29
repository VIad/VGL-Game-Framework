package vgl.web;

import com.vgl.gwtreq.client.GWTArrayBuffer;
import com.vgl.gwtreq.client.GWTDataView;

import vgl.core.buffers.BufferDetails;
import vgl.core.buffers.MemoryBuffer;
import vgl.main.VGL;

public class WebMemoryBuffer extends MemoryBuffer {

	private GWTArrayBuffer		buffer;

	private GWTDataView			dataView;

	private static int			endian			= 0;
	private static final int	LITTLE_ENDIAN	= 1;
	private static final int	BIG_ENDIAN		= 2;

	private BufferDetails		details;

	public WebMemoryBuffer(int capacity) {
		super(capacity);
		if (endian == 0) {
			endian = isLittleEndian() ? LITTLE_ENDIAN : BIG_ENDIAN;
		}
		this.buffer = new GWTArrayBuffer(capacity);
		this.dataView = new GWTDataView(buffer);
		this.details = new BufferDetails(dataView, capacity);
	}

	public WebMemoryBuffer(GWTArrayBuffer arrayBuffer) {
		super(0);
		if (endian == 0) {
			endian = isLittleEndian() ? LITTLE_ENDIAN : BIG_ENDIAN;
		}
		this.buffer = arrayBuffer;
		this.dataView = new GWTDataView(buffer);
		this.details = new BufferDetails(dataView, capacity);
	}

	@Override
	public WebMemoryBuffer putInt(int index, int value) {
		dataView.getView().setInt32(index, value, endian == LITTLE_ENDIAN);
		return this;
	}

	@Override
	public WebMemoryBuffer putByte(int index, int value) {
		dataView.getView().setInt8(index, value);
		return this;
	}

	@Override
	public WebMemoryBuffer putFloat(int index, float value) {
		dataView.getView().setFloat32(index, value, endian == LITTLE_ENDIAN);
		return this;
	}

	@Override
	public int readInt(int index) {
		return dataView.getView().getInt32(index, endian == LITTLE_ENDIAN);
	}

	@Override
	public float readFloat(int index) {
		return dataView.getView().getFloat32(index, endian == LITTLE_ENDIAN);
	}

	@Override
	public byte readByte(int index) {
		return dataView.getView().getInt8(index);
	}

	@Override
	public BufferDetails nativeBufferDetails() {
		return details;
	}

	@Override
	public void free() {
		this.dataView = null;
		this.buffer = null;
		if (details.getSizeBytes() >= 1024 * 1024)
			VGL.logger.warn("Deallocating memory chunk [" + details.getSizeBytes() + "] -> " + this);
	}

	private native boolean isLittleEndian() /*-{
		var isLittleEndian = new Uint8Array(
				new Uint32Array([ 0x12345678 ]).buffer)[0] === 0x78;
		return isLittleEndian;
	}-*/;

}
