package vgl.web.gl;

import static com.shc.webgl4j.client.WebGL10.GL_NEAREST;
import static com.shc.webgl4j.client.WebGL10.GL_TEXTURE_2D;
import static com.shc.webgl4j.client.WebGL10.GL_TEXTURE_MAG_FILTER;
import static com.shc.webgl4j.client.WebGL10.GL_TEXTURE_MIN_FILTER;
import static com.shc.webgl4j.client.WebGL10.glBindTexture;
import static com.shc.webgl4j.client.WebGL10.glBlendFunc;
import static com.shc.webgl4j.client.WebGL10.glCreateTexture;
import static com.shc.webgl4j.client.WebGL10.glEnable;
import static com.shc.webgl4j.client.WebGL10.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.shc.webgl4j.client.WebGL10;

import vgl.platform.gl.GLTexture;

/**
 * TODO USE IMAGE INSTEAD OF ARRAYVIEW / ARRAYBUFFER
 * 
 * @author vladimir
 *
 */
public class WGLTexture extends GLTexture {

	public WGLTexture(BufferedImage image) {
		super(image);
	}

	public WGLTexture(String path) {
		super(path);
	}

	@Override
	protected void removeFromGL() {
		WebGL10.glDeleteTexture(texture);
	}

	@Override
	protected int load(BufferedImage texImage) {
		int[] pixels = null;
		width = texImage.getWidth();
		height = texImage.getHeight();
		pixels = new int[width * height];
		texImage.getRGB(0, 0, width, height, pixels, 0, width);

		final int[] data = new int[width * height];
		for (int i = 0; i < (width * height); i++) {
			final int a = (pixels[i] & 0xff000000) >> 24;
			final int r = (pixels[i] & 0xff0000) >> 16;
			final int g = (pixels[i] & 0xff00) >> 8;
			final int b = (pixels[i] & 0xff);

			data[i] = (a << 24) | (b << 16) | (g << 8) | r;
		}

		final int result = glCreateTexture();
		WebGL10.glBindTexture(GL_TEXTURE_2D, result);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		final IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(
		        ByteOrder.nativeOrder()).asIntBuffer();
		buffer.put(data).flip();

		// glTexImage2D(GL_TEXTURE_2D,
		// 0,
		// GL_RGBA,
		// width,
		// height,
		// 0,
		// GL_RGBA,
		// GLTypes.UNSIGNED_BYTE,
		// buffer);
//		ImageBufferArray imgBuffer = new ImageBufferArray(buffer);
//		ArrayBufferView abView = new ImageBufferArrayView(imgBuffer);
//		glTexImage2D(GL_TEXTURE_2D,
//		        0,
//		        GL_RGBA,
//		        width,
//		        height,
//		        0,
//		        GL_RGBA,
//		        GL_UNSIGNED_BYTE,
//		        abView);
		glBindTexture(GL_TEXTURE_2D, 0);
		glEnable(GL11.GL_BLEND);
		glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		return result;
	}

	@Override
	protected int load(String path) {
		try {
			return load(ImageIO.read(new FileInputStream(path)));
		} catch (IOException e) {
			return -100;
		}
	}

	@Override
	public void bind() {
		WebGL10.glBindTexture(WebGL10.GL_TEXTURE_2D, texture);
	}

	@Override
	public void unbind() {
		WebGL10.glBindTexture(WebGL10.GL_TEXTURE_2D, 0);
	}

//	private class ImageBufferArray implements ArrayBuffer {
//
//		private IntBuffer pixels;
//
//		ImageBufferArray(IntBuffer pixels) {
//			this.pixels = pixels;
//		}
//
//		@Override
//		public int byteLength() {
//			return pixels.capacity() << 2;
//		}
//
//		public IntBuffer ib() {
//			return pixels;
//		}
//	}
//
//	private class ImageBufferArrayView implements ArrayBufferView {
//
//		ImageBufferArray buffer;
//
//		public ImageBufferArrayView(ImageBufferArray buffer) {
//			this.buffer = buffer;
//		}
//
//		@Override
//		public ImageBufferArray buffer() {
//			return buffer;
//		}
//
//		@Override
//		public int byteLength() {
//			return buffer.ib().capacity() << 2;
//		}
//
//		@Override
//		public int byteOffset() {
//			return buffer.ib().position() << 2;
//		}
//
//	}

}
