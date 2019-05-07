package vgl.tools;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import vgl.core.buffers.MemoryBufferInt;
import vgl.core.gfx.Color;
import vgl.core.gfx.Image;
import vgl.core.internal.GlobalDetails;
import vgl.main.VGL;
import vgl.platform.Platform;
import vgl.tools.async.UniContainer;
import vgl.tools.functional.callback.Callback;
import vgl.web.VGLWebApplication;
import vgl.web.WebSpecific;

public class ScreenRecorder {

	public static void captureScreen(Callback<Image> result) {
		if (GlobalDetails.getPlatform() == Platform.DESKTOP) {
			
			int width = VGL.display.getWidth();
			int height = VGL.display.getHeight();
			Image image = new Image(width, height);
			Image flipped = new Image(width, height);
			VGL.api_gfx.glReadBuffer(GL11.GL_FRONT);
			VGL.api_gfx.glReadPixels(0, 0, VGL.display.getWidth(), VGL.display.getHeight(), GL11.GL_RGBA,
					GL11.GL_UNSIGNED_BYTE, image.getPixels().getBuffer());

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					flipped.setPixel(x, (height - 1) - y, new Color(image.getPixel(x, y).getRGBA()));
				}
			}
			image.releaseMemory();

			result.invoke(flipped);
		} else {
			String dataURL = ((VGLWebApplication) VGL.app).getContext().display().toDataUrl();
			WebSpecific.JS.getImageData(dataURL, image -> {
				result.invoke(image);
			}, error -> {
				VGL.promptLogger.promptMessage("Error taking screenshot");
			});
		}
	}

}
