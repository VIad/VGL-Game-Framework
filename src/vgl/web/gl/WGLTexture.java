package vgl.web.gl;

import static com.shc.webgl4j.client.WebGL10.GL_ONE_MINUS_SRC_ALPHA;
import static com.shc.webgl4j.client.WebGL10.GL_SRC_ALPHA;
import static com.shc.webgl4j.client.WebGL10.glBlendFunc;
import static com.shc.webgl4j.client.WebGL10.glCreateTexture;
import static com.shc.webgl4j.client.WebGL10.glTexImage2D;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

import com.shc.webgl4j.client.WebGL10;
import com.vgl.gwtreq.client.VGWT;

import vgl.core.internal.GlobalDetails;
import vgl.platform.gl.GLTexture;
import vgl.platform.gl.GLTypes;
import vgl.web.VGLWebApplication;

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
	@Deprecated
	protected int load(BufferedImage texImage) {
		throw new UnsupportedOperationException("Cannot load texture from BufferedImage on web platform");
	}

	@Override
	protected int load(String path) {
		glBindTexture(GL_TEXTURE_2D, 0);
		VGWT.onImageLoad(path, (event, image) -> {
			int tex = glCreateTexture();
			glBindTexture(GL_TEXTURE_2D, texture);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GLTypes.UNSIGNED_BYTE, image);
			glBindTexture(GL_TEXTURE_2D, 0);
			glEnable(GL11.GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			WGLTexture.this.texture = tex;
		});
		return texture;
	}

	@Override
	public int getTextureID() {
		while (texture == 0) {

		}
		((VGLWebApplication) GlobalDetails.getApplication()).getContext().set();
		return super.getTextureID();
	}

	@Override
	public void bind() {
		WebGL10.glBindTexture(WebGL10.GL_TEXTURE_2D, getTextureID());
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
