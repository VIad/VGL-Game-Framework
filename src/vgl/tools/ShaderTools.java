package vgl.tools;

import java.util.Arrays;

import vgl.core.gfx.shader.ShaderAttributeType;
import vgl.core.gfx.shader.ShaderCompiler;
import vgl.core.gfx.shader.ShaderTokener;
import vgl.core.gfx.shader.ShaderTokener.AttributeDeclaration;
import vgl.core.gfx.shader.ShaderType;
import vgl.core.internal.GlobalDetails;
import vgl.platform.Platform;

public class ShaderTools {

	public static ShaderCompiler determineShaderPlatform(String shaderSrc) {
		return Arrays.stream(shaderSrc.split("\\n")).anyMatch(s -> s.contains("#version"))
		        ? ShaderCompiler.GL
		        : ShaderCompiler.GLES;

	}

}
