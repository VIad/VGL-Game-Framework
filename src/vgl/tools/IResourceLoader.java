package vgl.tools;

import vgl.platform.io.FileDetails;

public interface IResourceLoader {

	vgl.desktop.gfx.Texture loadTexture(FileDetails file);

	vgl.core.gfx.font.IFont loadFont(FileDetails file);

	vgl.audio.Sound loadSound(FileDetails file);
}
