package vgl.core.gfx.gl;

import java.util.ArrayList;
import java.util.List;

import vgl.core.buffers.MemoryBuffer;
import vgl.main.VGL;
import vgl.platform.gl.GLBufferTarget;
import vgl.platform.gl.GLBufferUsage;
import vgl.platform.gl.Primitive;

public class GPUBuffer {

	public final static int		NULL	= 0;

	private static GPUBuffer	currentlyBound;

	protected int					vbo;

	protected int					usage;

	public GPUBuffer(int usage) {
		this.usage = usage;
		this.vbo = VGL.api_gfx.glGenBuffer();
	}

	public GPUBuffer() {
		this.usage = GLBufferUsage.STATIC_DRAW;
		this.vbo = VGL.api_gfx.glGenBuffer();
	}

	public void setData(MemoryBuffer data) {
		bind();
		VGL.api_gfx.glBufferData(GLBufferTarget.ARRAY_BUFFER, data, usage);
	}
	
	public void setData(MemoryBuffer data, int offset) {
		bind();
		VGL.api_gfx.glBufferSubData(GLBufferTarget.ARRAY_BUFFER.nativeGL(), offset, data);
	}
	
	public void resize(int bytes) {
		bind();
		VGL.api_gfx.glBufferData(GLBufferTarget.ARRAY_BUFFER, bytes, usage);
	}

	public void setLayout(GPUBuffer.Layout layout) {
		bind();
		for (int i = 0; i < layout.data.size(); i++) {
			VGL.api_gfx.glEnableVertexAttribArray(i);
			VGL.api_gfx.glVertexAttribPointer(i, layout.data.get(i).count, layout.data.get(i)._primType.toGLType(),
			        false, layout.totalBytes, layout.calculatePointer(i));
		}
	}

	public void bind() {
		if (currentlyBound != this) {
			VGL.api_gfx.glBindBuffer(GLBufferTarget.ARRAY_BUFFER, vbo);
			currentlyBound = this;
		}
	}

	public void unbind() {
		if (currentlyBound == this) {
			VGL.api_gfx.glBindBuffer(GLBufferTarget.ARRAY_BUFFER, NULL);
			currentlyBound = null;
		}
	}

	public static class Layout {
		private List<BufferDataType>	data;
		private int						index;
		private int						stride;
		private int						totalBytes;

		public Layout() {
			this.data = new ArrayList<>();
		}

		public Layout push(Primitive primitive, int amnt) {
			data.add(index++, new BufferDataType(primitive, amnt));
			totalBytes += primitive.size() * amnt;
			return this;
		}

		long calculatePointer(int forIndex) {
			long ptr = 0;
			for (int i = 0; i < forIndex; i++) {
				ptr += data.get(i)._primType.size() * data.get(i).count;
			}
			return ptr;
		}
		
		public int getVertexSizeBytes() {
			return totalBytes;
		}
	}

	private static class BufferDataType {
		Primitive	_primType;
		int			count;

		BufferDataType(Primitive prim, int c) {
			this._primType = prim;
			this.count = c;
		}
	}

	public void dispose() {
		if(currentlyBound == this)
			currentlyBound = null;
		VGL.api_gfx.glDeleteBuffers(vbo);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + vbo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GPUBuffer other = (GPUBuffer) obj;
		if (vbo != other.vbo)
			return false;
		return true;
	}

	public int getId() {
		return vbo;
	}

	public static GPUBuffer getCurrentlyBound() {
		return currentlyBound;
	}

}
