package vgl.desktop.tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import vgl.audio.Sound;
import vgl.core.gfx.Image;
import vgl.core.gfx.Image.Format;
import vgl.core.gfx.gl.Texture;
import vgl.core.internal.ProcessManager;
import vgl.desktop.DesktopSpecific;
import vgl.platform.io.FileDetails;
import vgl.platform.io.ILoadOption;
import vgl.platform.io.ImageLoadOption;
import vgl.platform.io.StandartLoadOption;
import vgl.tools.IResource;
import vgl.tools.IResourceLoader;
import vgl.tools.functional.callback.BinaryCallback;
import vgl.tools.functional.callback.Callback;
import vgl.tools.functional.callback.TernaryCallback;

public class DesktopResourceLoader implements IResourceLoader {

	private int		filesToLoad;
	private float	readyPercentage;
	private float	percIncreasePerFile;

	public DesktopResourceLoader() {
		this.loadMap = new HashMap<>();
	}
	
	private Map<String, TernaryContainer<Class<? extends IResource>,
	                                     List<? extends ILoadOption>,
	                                     TernaryCallback<? super IResource, ? super IResource, Throwable>>> loadMap;

	private static class TernaryContainer<A1,A2,A3>{
		A1 first;
		A2 second;
		A3 third;
		
		public TernaryContainer(A1 first, A2 second, A3 third) {
			this.first = first;
			this.second = second;
			this.third = third;
		}
	}
	
	@Override
	public float getReadyPercentage() {
		return readyPercentage;
	}

	@Override
	public void begin() {
		this.readyPercentage = 0f;
		this.percIncreasePerFile = 100.0f / filesToLoad;
		beginLoad();
	}

	private void beginLoad() {
		new Thread(() -> {			
			loadMap.entrySet()
			.parallelStream()
			.forEach(entry -> {
				try {
					loadByClass(entry.getKey(),
							entry.getValue().first,
							entry.getValue().second,
							entry.getValue().third
							);
					readyPercentage += percIncreasePerFile;
				} catch (Throwable e) {
					entry.getValue()
					     .third
					     .invoke(null, null, e);
				}
			});
		}).start();
	}

	private void loadByClass(String file,
			                 Class<? extends IResource> type,
			                 List<? extends ILoadOption> options,
			                 TernaryCallback<? super IResource, ? super IResource, Throwable> third
			                 ) throws Throwable {
		if(type.equals(Image.class)) {
			Image result = DesktopSpecific.FileLoading
					                      .loadImage0(file);
			Image.Format loadFormat = result.getDataFormat();
			if(options != null) {
				Optional<ImageLoadOption> loadOp = options.stream()
						                                  .filter(op -> op instanceof ImageLoadOption)
						                                  .map(op -> (ImageLoadOption) op)
						                                  .findFirst();
				if(loadOp.isPresent()) {
					switch(loadOp.get()) {
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
			if(options != null && options.stream().anyMatch(op -> op == StandartLoadOption.LOAD_NEXT_UPDATE)) {
				ProcessManager.get()
				              .runNextUpdate(() -> third.invoke(result.getDataFormat() == loadFormatFinal ? result :
							                                    Image.copy(result, loadFormatFinal, true),
							                                    (IResource) null,
							                                    (Throwable) null));
				return;                                         
			}
			third.invoke(result.getDataFormat() == loadFormatFinal ? result :
				         Image.copy(result, loadFormatFinal, true),
				         (IResource) null,
				         (Throwable) null);
		}
		if(type.equals(Sound.class)) {
			ProcessManager.get()
			              .runNextUpdate(() -> third.invoke(new Sound(file.endsWith(".ogg") ? 
			            		                                      DesktopSpecific.AudioDecoder
			            		                                                     .decodeOGG(file) :
			            		                                      DesktopSpecific.AudioDecoder
			            		                                                     .decodeAudio(file)),
			            		                                                             null, null));
		}
		if(type.equals(Texture.class)) {
			Image result = DesktopSpecific.FileLoading
					                      .loadImage0(file);
			//Texture needs to be loaded in a sequential flow
			ProcessManager.get()
			              .runNextUpdate(() -> third.invoke(new Texture(result), result, null));
		}
	}

	@Override
	public void loadImage(FileDetails imageFile, Callback<Image> image, Callback<Throwable> error, ILoadOption...options) {
		loadMap.put(imageFile.absolutePath(),
		        new TernaryContainer<>(Image.class, Arrays.asList(options), (first, ignored, fail) -> {
			        if (fail == null)
				        image.invoke((Image) first);
			        else
				        error.invoke(fail);
		        }));
		filesToLoad++;
	}

	@Override
	public void loadSound(FileDetails soundFile, Callback<Sound> sound, Callback<Throwable> error) {
		loadMap.put(soundFile.absolutePath(), new TernaryContainer<>(Sound.class, null, (first, ignored, fail) -> {
			if (fail == null)
				sound.invoke((Sound) first);
			else
				error.invoke(fail);
		}));
		filesToLoad++;
	}

	@Override
	public void loadTexture(FileDetails imageFile, BinaryCallback<Texture, Image> result, Callback<Throwable> error) {
		loadMap.put(imageFile.absolutePath(), new TernaryContainer<>(Texture.class, null, (texture, image, fail) -> {
			if(fail == null) 
				result.invoke((Texture) texture, (Image) image);
			else
				error.invoke(fail);
		}));
		filesToLoad++;
	}
	
	public class RawLoader implements IResourceLoader.Raw{

		@Override
		public void loadSoundRaw(FileDetails fd, Callback<Integer> alBufferRaw, Callback<Throwable> error) {
			
		}
		
	}

}
