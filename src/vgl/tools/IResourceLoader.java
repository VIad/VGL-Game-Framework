package vgl.tools;

import vgl.audio.Sound;
import vgl.core.gfx.Image;
import vgl.core.gfx.font.BMFont;
import vgl.core.gfx.gl.Texture;
import vgl.main.VGL;
import vgl.platform.io.FileDetails;
import vgl.platform.io.ILoadOption;
import vgl.tools.functional.callback.BinaryCallback;
import vgl.tools.functional.callback.Callback;

public interface IResourceLoader {

	float getReadyPercentage();

	void loadImage(FileDetails imageFile, Callback<Image> image, Callback<Throwable> error, ILoadOption... options);

	void loadSound(FileDetails soundFile, Callback<Sound> sound, Callback<Throwable> error);
	
	void loadFont(FileDetails fntFile, Callback<BMFont> font, Callback<Throwable> error);

	void loadTexture(FileDetails imageFile, BinaryCallback<Texture, Image> result, Callback<Throwable> error);

	default void loadImage(FileDetails imageFile, Callback<Image> image) {
		loadImage(imageFile, image, VGL.errorChannel::forward);
	}

	default void loadSound(FileDetails soundFile, Callback<Sound> sound) {
		loadSound(soundFile, sound, VGL.errorChannel::forward);
	}
	
	default void loadFont(FileDetails fntFile, Callback<BMFont> result) {
		loadFont(fntFile, result, VGL.errorChannel::forward);
	}

	default void loadTexture(FileDetails imageFile, BinaryCallback<Texture, Image> result) {
		loadTexture(imageFile, result, VGL.errorChannel::forward);
	}

	default void loadTexture(FileDetails imageFile, Callback<Texture> result) {
		loadTexture(imageFile, (texture, image) -> {
			image.releaseMemory();
			result.invoke(texture);
		});
	}

	void begin();
	
	interface Raw{
		void loadSoundRaw(FileDetails fd, Callback<Integer> alBufferRaw, Callback<Throwable> error);
	}

}
