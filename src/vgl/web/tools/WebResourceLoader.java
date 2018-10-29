package vgl.web.tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.shc.gwtal.client.openal.AudioDecoder;

import vgl.audio.Sound;
import vgl.core.exception.VGLIOException;
import vgl.core.gfx.Image;
import vgl.core.gfx.Image.Format;
import vgl.core.gfx.font.BMFont;
import vgl.core.gfx.gl.Texture;
import vgl.core.internal.ProcessManager;
import vgl.main.VGL;
import vgl.platform.io.FileDetails;
import vgl.platform.io.ILoadOption;
import vgl.platform.io.ImageLoadOption;
import vgl.platform.io.StandartLoadOption;
import vgl.tools.IResource;
import vgl.tools.IResourceLoader;
import vgl.tools.functional.callback.BinaryCallback;
import vgl.tools.functional.callback.Callback;
import vgl.tools.functional.callback.TernaryCallback;
import vgl.web.WebSpecific;

public class WebResourceLoader implements IResourceLoader {

	private int																																								filesToLoad;
	private float																																							readyPercentage;
	private float																																							percIncreasePerFile;

	private Runnable																																						finishCallback;

	private Map<FileDetails, TernaryContainer<Class<? extends IResource>,
                                              List<? extends ILoadOption>,
                                              TernaryCallback<? super IResource, ? super IResource, Throwable>>> loadMap;

	private static class TernaryContainer<A1, A2, A3> {
		A1	first;
		A2	second;
		A3	third;

		public TernaryContainer(A1 first, A2 second, A3 third) {
			this.first = first;
			this.second = second;
			this.third = third;
		}
	}

	public WebResourceLoader() {
		this.loadMap = new HashMap<>();
		this.finishCallback = () -> {};
	}

	@Override
	public float getReadyPercentage() {
		return readyPercentage;
	}

	@Override
	public void loadImage(FileDetails imageFile, Callback<Image> success, Callback<Throwable> error,
	        ILoadOption... options) {
		if (!loadMap.containsKey(imageFile)) {
			loadMap.put(imageFile,
			        new TernaryContainer<>(Image.class, Arrays.asList(options), (image, ignored, fail) -> {
				        if (fail == null)
					        success.invoke((Image) image);
				        else
					        error.invoke(fail);
			        }));
			filesToLoad++;
		}
	}

	@Override
	public void loadSound(FileDetails soundFile, Callback<Sound> success, Callback<Throwable> error) {
		if (!loadMap.containsKey(soundFile)) {
			loadMap.put(soundFile, new TernaryContainer<>(Sound.class, null, (sound, ignored, fail) -> {
				if (fail == null)
					success.invoke((Sound) sound);
				else
					error.invoke(fail);
			}));
			filesToLoad++;
		}
	}

	@Override
	public void loadTexture(FileDetails imageFile, BinaryCallback<Texture, Image> result, Callback<Throwable> error) {
		if (!loadMap.containsKey(imageFile)) {
			loadMap.put(imageFile, new TernaryContainer<>(Texture.class, null, (texture, image, fail) -> {
				if (fail == null)
					result.invoke((Texture) texture, (Image) image);
				else
					error.invoke(fail);
			}));
			filesToLoad++;
		}
	}

	@Override
	public void loadFont(FileDetails fntFile, Callback<BMFont> result, Callback<Throwable> error) {
		if (!loadMap.containsKey(fntFile)) {
			loadMap.put(fntFile, new TernaryContainer<>(BMFont.class, null, (font, ignored, fail) -> {
				if(fail == null)
					result.invoke((BMFont) font);
				else
					error.invoke(fail);
			}));
			filesToLoad++;
		}
	}

	@Override
	public void begin() {
		this.percIncreasePerFile = 100.0f / filesToLoad;
		this.readyPercentage = 0f;
		beginLoad();
	}

	private void beginLoad() {
		loadMap.entrySet().forEach(entry -> {
			try {
				loadByClass(entry.getKey(), entry.getValue().first, entry.getValue().second, entry.getValue().third);
			} catch (Throwable e) {
				entry.getValue().third.invoke(null, null, e);
			}
			
		});
	}

	private void loadByClass(FileDetails file, Class<? extends IResource> type, List<? extends ILoadOption> options,
	        TernaryCallback<? super IResource, ? super IResource, Throwable> finisher) throws Throwable {
		if (type.equals(Image.class)) {
			WebSpecific.FileLoading.loadImage0(file, (image, error) -> {
				if (error != null) {
					finisher.invoke(null, null, error);
					return;
				}

				Image.Format loadFormat = image.getDataFormat();

				if (options != null) {
					Optional<ImageLoadOption> loadOp = options.stream().filter(op -> op instanceof ImageLoadOption).map(
					        op -> (ImageLoadOption) op).findFirst();
					if (loadOp.isPresent()) {
						switch (loadOp.get())
						{
							case LOAD_FORMAT_ARGB:
								loadFormat = Format.ARGB;
								break;
							case LOAD_FORMAT_RGB:
								loadFormat = Format.RGB;
								break;
							case LOAD_FORMAT_RGBA:
								loadFormat = Format.RGBA;
								break;
						}
					}
				}

				final Image.Format loadFormatFinal = loadFormat;
				if (options != null && options.stream().anyMatch(op -> op == StandartLoadOption.LOAD_NEXT_UPDATE)) {
					ProcessManager.get().runNextUpdate(() -> {
						finisher.invoke(image.getDataFormat() == loadFormatFinal
						        ? image
						        : Image.copy(image, loadFormatFinal, true), (IResource) null, (Throwable) null);
						WebResourceLoader.this.readyPercentage += percIncreasePerFile;
						filesToLoad--;
						if(filesToLoad <= 0)
							ProcessManager.get().runNextUpdate(finishCallback);
					});
					return;
				}
				WebResourceLoader.this.readyPercentage += percIncreasePerFile;
				finisher.invoke(
				        image.getDataFormat() == loadFormatFinal ? image : Image.copy(image, loadFormatFinal, true),
				        (IResource) null, (Throwable) null);
				        filesToLoad--;
				        if(filesToLoad <= 0)
				        	ProcessManager.get().runNextUpdate(finishCallback);
			});
		}
		if (type.equals(Sound.class)) {
			VGL.io.readBytes(file, buffer -> {
				AudioDecoder.decodeAudio(WebSpecific.JS.cast(buffer), alBuffer -> {
					ProcessManager.get().runNextUpdate(() -> {
						finisher.invoke(new Sound(alBuffer), null, null);
						WebResourceLoader.this.readyPercentage += percIncreasePerFile;
						filesToLoad--;
						if(filesToLoad <= 0)
							ProcessManager.get().runNextUpdate(finishCallback);
					});
				}, error -> {
					throw new VGLIOException("Error while decoding audio : " + error);
				});
			});
		}
		if (type.equals(Texture.class)) {
			WebSpecific.FileLoading.loadImage0(file, (image, error) -> {
				if (error != null) {
					finisher.invoke(null, null, error);
					return;
				}

				ProcessManager.get().runNextUpdate(() -> {
					finisher.invoke(new Texture(image), image, null);
					WebResourceLoader.this.readyPercentage += percIncreasePerFile;
					filesToLoad--;
					if(filesToLoad <= 0)
						ProcessManager.get().runNextUpdate(finishCallback);
				});
			});
		}
		if(type.equals(BMFont.class)) {
			BMFont.load(file, (font, error) -> {
				finisher.invoke(font, null, error);
				WebResourceLoader.this.readyPercentage += percIncreasePerFile;
				filesToLoad--;
				if(filesToLoad <= 0)
					ProcessManager.get().runNextUpdate(finishCallback);
			});
		}
	}

	@Override
	public void onLoadingFinished(Runnable onFinish) {
		this.finishCallback = onFinish;
	}

}
