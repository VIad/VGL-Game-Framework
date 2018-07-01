package vgl.core.gfx.gl;

import org.lwjgl.opengl.GL11;

import com.google.gwt.typedarrays.shared.ArrayBufferView;
import com.google.gwt.typedarrays.shared.Uint8Array;

import vgl.core.exception.VGLGraphicsException;
import vgl.core.gfx.Image;
import vgl.core.internal.GlobalDetails;
import vgl.main.VGL;
import vgl.platform.Platform;
import vgl.platform.gl.GLTextureType;
import vgl.platform.gl.GLTypes;
import vgl.tools.IResource;
import vgl.web.WebGraphicsPlatform;
import vgl.web.WebSpecific;

public class Texture implements IResource{

	int	width, height;

	int	textureID;
	
	private boolean isDisposed;

	private static int currentlyActive;
	
	public Texture(int width, int height) {
		this.textureID = VGL.api_gfx.glGenTexture();
		VGL.api_gfx.glBindTexture(GLTextureType.TEXTURE_2D, textureID);
		VGL.api_gfx.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		VGL.api_gfx.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
//		VGL.api_gfx.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA,
//		        GLTypes.UNSIGNED_BYTE, (MemoryBuffer) null);
		texImage2d(this, null);
		VGL.api_gfx.glBindTexture(GLTextureType.TEXTURE_2D, 0);
		VGL.api_gfx.glEnable(GL11.GL_BLEND);
		VGL.api_gfx.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public Texture(Image image) {
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.textureID = VGL.api_gfx.glGenTexture();
		VGL.api_gfx.glBindTexture(GLTextureType.TEXTURE_2D, textureID);
		setFilter(GL11.GL_NEAREST, GL11.GL_NEAREST);
		texImage2d(this, image);
		VGL.api_gfx.glBindTexture(GLTextureType.TEXTURE_2D, 0);
		VGL.api_gfx.glEnable(GL11.GL_BLEND);
		VGL.api_gfx.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private static void texImage2d(Texture tex, Image image) {
		Image actual = image.getDataFormat() == Image.Format.RGBA ?
				               image :
				               Image.copy(image, Image.Format
				                                      .RGBA, false);

		if(GlobalDetails.getPlatform() == Platform.WEB) {
		   	 ArrayBufferView pixels = WebSpecific.JS
			 		                             .copy(actual.getPixels().getBuffer());
			((WebGraphicsPlatform) VGL.api_gfx).glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, image.getWidth(),
			        image.getHeight(), 0, GL11.GL_RGBA, GLTypes.UNSIGNED_BYTE, pixels);
			return;
		}
		VGL.api_gfx
		   .glTexImage2D(GL11.GL_TEXTURE_2D,
				         0,
				         GL11.GL_RGBA,
				         tex.width,
				         tex.height,
				         0,
				         GL11.GL_RGBA,
				         GLTypes.UNSIGNED_BYTE,
				         image != null ? 
				         actual.getPixels().getBuffer() : null);
		if (!actual.equals(image))
			actual.releaseMemory();
	}
	
	public static void setActiveTextureUnit(int textureUnit) {
		if(currentlyActive == textureUnit)
			return;
		VGL.api_gfx.glActiveTexture(textureUnit);
		currentlyActive = textureUnit;
	}
	
	public void genMipmaps() {
		bind();
		VGL.api_gfx.glGenMipmap(GLTextureType.TEXTURE_2D.gl());
	}
	
	public void setWrap(int s, int t) {
		bind();
		VGL.api_gfx.glTexParameteri(GLTextureType.TEXTURE_2D.gl(), GL11.GL_TEXTURE_WRAP_S, s);
		VGL.api_gfx.glTexParameteri(GLTextureType.TEXTURE_2D.gl(), GL11.GL_TEXTURE_WRAP_T, t);
	}
	
	public void setFilter(int min, int mag) {
		bind();
		VGL.api_gfx.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, min);
		VGL.api_gfx.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, mag);
	}
	
	public void bind() {
		validate();
		VGL.api_gfx.glBindTexture(GLTextureType.TEXTURE_2D, this.textureID);
	}
		
	public void unbind() {
		validate();
		VGL.api_gfx.glBindTexture(GLTextureType.TEXTURE_2D, 0);
	}
	
	public static int getCurrentlyBoundTextureUnit() {
		return currentlyActive;
	}
	
	public void releaseMemory() {
		validate();
		VGL.api_gfx.glDeleteTexture(textureID);
		this.isDisposed = true;
	}

	public int getWidth() {
		validate();
		return width;
	}
	
	public int getHeight() {
		validate();
		return height;
	}
	
	public int getID() {
		validate();
		return textureID;
	}

	@Override
	public boolean isDisposed() {
		return isDisposed;
	}
}
