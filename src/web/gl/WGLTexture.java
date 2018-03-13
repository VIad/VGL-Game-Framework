package vgl.web.gl;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.shc.webgl4j.client.WebGL10;

import sun.rmi.server.UnicastRef;
import vgl.platform.gl.GLTexture;
import vgl.tools.async.UniContainer;

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
		throw new UnsupportedOperationException("Cannot load texture from BufferedImage on web platform");
	}

	@Override
	protected int load(String path) {
		final UniContainer<Integer> result = new UniContainer<>();
	}

	@Override
	public void bind() {
		WebGL10.glBindTexture(WebGL10.GL_TEXTURE_2D, texture);
	}

	@Override
	public void unbind() {
		WebGL10.glBindTexture(WebGL10.GL_TEXTURE_2D, 0);
	}

	// private class ImageBufferArray implements ArrayBuffer {
	//
	// private IntBuffer pixels;
	//
	// ImageBufferArray(IntBuffer pixels) {
	// this.pixels = pixels;
	// }
	//
	// @Override
	// public int byteLength() {
	// return pixels.capacity() << 2;
	// }
	//
	// public IntBuffer ib() {
	// return pixels;
	// }
	// }
	//
	// private class ImageBufferArrayView implements ArrayBufferView {
	//
	// ImageBufferArray buffer;
	//
	// public ImageBufferArrayView(ImageBufferArray buffer) {
	// this.buffer = buffer;
	// }
	//
	// @Override
	// public ImageBufferArray buffer() {
	// return buffer;
	// }
	//
	// @Override
	// public int byteLength() {
	// return buffer.ib().capacity() << 2;
	// }
	//
	// @Override
	// public int byteOffset() {
	// return buffer.ib().position() << 2;
	// }
	//
	// }

}
