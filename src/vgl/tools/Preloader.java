package vgl.tools;

import java.util.HashMap;
import java.util.Map;

import com.gargoylesoftware.htmlunit.javascript.host.html.Image;
import com.sun.prism.Texture;

import vgl.audio.Sound;
import vgl.core.exception.VGLRuntimeException;
import vgl.core.gfx.font.BMFont;
import vgl.main.VGL;
import vgl.platform.io.FileDetails;

public class Preloader {

	private static final Preloader INST_PRELOADER = new Preloader();

	private Preloader() {

	}

	private volatile boolean used = false;

	private IResourceLoader performer;

	private Map<FileDetails, IResource> resourcesLoaded = new HashMap<>();

	public static Preloader use() {
		if (INST_PRELOADER.used)
			throw new VGLRuntimeException("Cannot use Preloader while loading");
		INST_PRELOADER.performer = VGL.factory.createResourceLoader();
		INST_PRELOADER.resourcesLoaded = new HashMap<>();
		return INST_PRELOADER;
	}

	@SuppressWarnings("unchecked")
	public static <T extends IResource> T fetch(FileDetails key, Class<T> type) {
		return (T) INST_PRELOADER.resourcesLoaded.get(key);
	}

	public Preloader preload(FileDetails file, Class<? extends IResource> type) {
		if (type.equals(BMFont.class)) {
			performer.loadFont(file, font -> {
				resourcesLoaded.put(file, font);
			});
		}
		if (type.equals(Image.class)) {
			performer.loadImage(file, image -> {
				resourcesLoaded.put(file, image);
			});
		}
		if (type.equals(Sound.class)) {
			performer.loadSound(file, sound -> {
				resourcesLoaded.put(file, sound);
			});
		}
		if (type.equals(Texture.class)) {
			performer.loadTexture(file, texture -> {
				resourcesLoaded.put(file, texture);
			});
		}
		return this;
	}

	public void begin() {
		used = true;
		performer.begin();
		performer.onLoadingFinished(() -> {
			used = false;
		});
	}

}
