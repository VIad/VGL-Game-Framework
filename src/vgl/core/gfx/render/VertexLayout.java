package vgl.core.gfx.render;

public interface VertexLayout {

	int VERTEX_INDEX = 0;
	int COLOR_INDEX = 1;
	int UV_INDEX = 2;
	int TID_INDEX = 3;

	VertexLayout.GLSize size(int index);

	final class GLSize {

		public GLSize(int glPrimitive, int count, int sizeBytes) {
			this.glPrimitiveSize = count;
			this.glPrimitiveType = glPrimitive;
			this.sizeBytes = sizeBytes;
		}

		private int glPrimitiveType;
		private int glPrimitiveSize;
		private int sizeBytes;

		public int getType() {
			return glPrimitiveSize;
		}

		public int getCount() {
			return glPrimitiveType;
		}

		public int getSizeBytes() {
			return sizeBytes;
		}

	}

}
