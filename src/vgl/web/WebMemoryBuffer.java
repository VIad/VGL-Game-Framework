package vgl.web;

import javax.xml.soap.Detail;

import com.vgl.gwtreq.client.GWTArrayBuffer;
import com.vgl.gwtreq.client.GWTDataView;

import vgl.core.buffers.BufferDetails;
import vgl.core.buffers.MemoryBuffer;

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
			endian = getEndianness() ? LITTLE_ENDIAN : BIG_ENDIAN;
		}
		this.buffer = new GWTArrayBuffer(capacity);
		this.dataView = new GWTDataView(buffer);
		this.details = new BufferDetails(dataView, capacity);
	}

	@Override
	public void putInt(int index, int value) {
		dataView.getView().setInt32(index, value, endian == LITTLE_ENDIAN);
	}

	@Override
	public void putByte(int index, byte value) {
		dataView.getView().setInt8(index, value, endian == LITTLE_ENDIAN);
	}

	@Override
	public void putFloat(int index, float value) {
		dataView.getView().setFloat32(index, value, endian == LITTLE_ENDIAN);
	}

	@Override
	public int readInt(int index) {
		return dataView.getView().getInt32(index, endian == LITTLE_ENDIAN);
	}

	@Override
	public float readFloat(int index) {
		return dataView.getView().getFloat32(index, endian == LITTLE_ENDIAN);
		;
	}

	@Override
	public byte readByte(int index) {
		return dataView.getView().getInt8(index, endian == LITTLE_ENDIAN);
	}

	@Override
	public BufferDetails nativeBufferDetails() {
		return details;
	}

	@Override
	public int getPointer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void free() {

	}

	/**
	 * @author Sri Harsha Chilakapati
	 * @return
	 */
	private native boolean getEndianness() /*-{
		var buffer = new ArrayBuffer(2);
		new DataView(buffer).setInt16(0, 256, true);
		// Int16Array uses the platform's endianness.
		return new Int16Array(buffer)[0] === 256;
	}-*/;

}
