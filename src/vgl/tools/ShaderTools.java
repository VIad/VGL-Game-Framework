package vgl.tools;

import java.util.Arrays;

import vgl.core.gfx.shader.ShaderCompiler;

public class ShaderTools {

	public static ShaderCompiler determineShaderPlatform(String shaderSrc) {
		return Arrays.stream(shaderSrc.split("\\n")).anyMatch(s -> s.contains("#version"))
		        ? ShaderCompiler.GL
		        : ShaderCompiler.GLES;

	}

}
