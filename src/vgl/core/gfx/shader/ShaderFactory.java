package vgl.core.gfx.shader;

import java.io.File;

import vgl.core.internal.GlobalDetails;
import vgl.desktop.io.FileInput;
import vgl.platform.Platform;

public class ShaderFactory {

	public static ShaderProgram batch2DGLSL() {
		Platform platform = GlobalDetails.getPlatform();
		if (platform != Platform.WEB) {
			ShaderProgram def = new ShaderProgram(
			        FileInput.readAllSync(new File("resources/shaders/vert.txt")),
			        FileInput.readAllSync(new File("resources/shaders/frag.txt"))) {

				@Override
				public void getAllUniformLocations() {
					
				}

				@Override
				public void bindVertexShaderInputAttribs() {
					super.bindAttribute(0, "positions");
					super.bindAttribute(1, "color");
					super.bindAttribute(2, "uvs");
					super.bindAttribute(3, "texId");
				}
			};
			return def;
		}
		return null;
	}

}
