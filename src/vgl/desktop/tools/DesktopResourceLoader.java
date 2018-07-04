package vgl.desktop.tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

import vgl.audio.Sound;
import vgl.core.gfx.Image;
import vgl.core.gfx.Image.Format;
import vgl.core.gfx.font.BMFont;
import vgl.core.gfx.gl.Texture;
import vgl.core.internal.ProcessManager;
import vgl.desktop.DesktopSpecific;
import vgl.main.VGL;
import vgl.platform.io.FileDetails;
import vgl.platform.io.ILoadOption;
import vgl.platform.io.ImageLoadOption;
import vgl.platform.io.StandartLoadOption;
import vgl.tools.IResource;
import vgl.tools.IResource.ResourceState;
import vgl.tools.IResourceLoader;
import vgl.tools.ISpecifier;
import vgl.tools.functional.callback.BinaryCallback;
import vgl.tools.functional.callback.Callback;
import vgl.tools.functional.callback.TernaryCallback;

public class DesktopResourceLoader implements IResourceLoader {

	private int									filesRemaining;
	private int									filesToLoad;
	private float								readyPercentage;
	private float								percIncreasePerFile;
	private java.util.concurrent.ForkJoinPool	loadPerformer;

	public DesktopResourceLoader() {
		this.loadMap = new HashMap<>();
		this.loadPerformer = new ForkJoinPool(10);
	}
	
	private Map<FileDetails, TernaryContainer<Class<? extends IResource>,
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
			.stream()
			.forEach(entry -> {
				loadPerformer.execute(() -> {
					try {
						loadByClass(entry.getKey(),
								entry.getValue().first,
								entry.getValue().second,
								entry.getValue().third
								);
					} catch (Throwable e) {
						entry.getValue()
						.third
						.invoke(null, null, e);
					}
					readyPercentage += percIncreasePerFile;
					filesRemaining--;
				});
				
			});
			this.loadPerformer.shutdown();
		}).start();
	}

	private void loadByClass(FileDetails file,
			                 Class<? extends IResource> type,
			                 List<? extends ILoadOption> options,
			                 TernaryCallback<? super IResource, ? super IResource, Throwable> third
			                 ) throws Throwable {
		if(type.equals(Image.class)) {
			Image result = DesktopSpecific.FileLoading
					                      .loadImage0(file.absolutePath());
			((ISpecifier<IResource.ResourceState>) result).specify(ResourceState.LOADING);
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
				        .runNextUpdate(() -> {
					        third.invoke(result.getDataFormat() == loadFormatFinal
					                        ? result
					                        : Image.copy(result, loadFormatFinal, true),
					                (IResource) null, (Throwable) null);
					        ((ISpecifier<IResource.ResourceState>) result).specify(ResourceState.AVAILABLE);
				        });
				return;                                         
			}
			((ISpecifier<IResource.ResourceState>) result).specify(ResourceState.AVAILABLE);
			third.invoke(result.getDataFormat() == loadFormatFinal ? result :
				         Image.copy(result, loadFormatFinal, true),
				         (IResource) null,
				         (Throwable) null);
		}
		//Sounds need to be loaded sequentially, enqueue for next update
		if(type.equals(Sound.class)) {
			ProcessManager.get()
			              .runNextUpdate(() -> third.invoke(new Sound(file.absolutePath().endsWith(".ogg") ? 
			            		                                      DesktopSpecific.AudioDecoder
			            		                                                     .decodeOGG(file.absolutePath()) :
			            		                                      DesktopSpecific.AudioDecoder
			            		                                                     .decodeAudio(file.absolutePath())),
			            		                                                             null, null));
		}
		if(type.equals(Texture.class)) {
			Image result = DesktopSpecific.FileLoading
					                      .loadImage0(file.absolutePath());
			//Textures need to be loaded sequentially, enqueue for next update
			ProcessManager.get()
			              .runNextUpdate(() -> third.invoke(new Texture(result), result, null));
		}
		if(type.equals(BMFont.class)) {
			BMFont.load(file, (font, fail) -> {
				third.invoke(font, null, fail);
			});
		}
	}

	@Override
	public void loadImage(FileDetails imageFile, Callback<Image> image, Callback<Throwable> error,
	        ILoadOption... options) {
		if(!loadMap.containsKey(imageFile)) {
			loadMap.put(imageFile,
			        new TernaryContainer<>(Image.class, Arrays.asList(options), (first, ignored, fail) -> {
				        if (fail == null)
					        image.invoke((Image) first);
				        else
					        error.invoke(fail);
			        }));
			filesToLoad++;
		}
	}

	@Override
	public void loadSound(FileDetails soundFile, Callback<Sound> sound, Callback<Throwable> error) {
		if (!loadMap.containsKey(soundFile)) {
			loadMap.put(soundFile, new TernaryContainer<>(Sound.class, null, (first, ignored, fail) -> {
				if (fail == null)
					sound.invoke((Sound) first);
				else
					error.invoke(fail);
			}));
			filesToLoad++;
		}
	}

	@Override
	public void loadTexture(FileDetails imageFile, BinaryCallback<Texture, Image> result, Callback<Throwable> error) {
		if (!loadMap.containsKey(imageFile)) {
			loadMap.put(imageFile,
			        new TernaryContainer<>(Texture.class, null, (texture, image, fail) -> {
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
			loadMap.put(fntFile,
					new TernaryContainer<>(BMFont.class, null, (bmFont, ignored, fail) -> {
						if(fail == null)
							result.invoke((BMFont) bmFont);
						else
							error.invoke(fail);
			        }));
			filesToLoad++;
		}
	}
	
	public class RawLoader implements IResourceLoader.Raw{

		@Override
		public void loadSoundRaw(FileDetails fd, Callback<Integer> alBufferRaw, Callback<Throwable> error) {
			
		}
		
	}

}
