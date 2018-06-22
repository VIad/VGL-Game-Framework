package vgl.tools;

import vgl.core.gfx.shader.ShaderType;

public class ShaderParser {

	public static String makeSafe(ShaderType type, String... source) {
		StringBuilder sb = new StringBuilder();

		// if (!source[0].contains("#version"))
		// {
		// String version = null;
		// Platform plat = GlobalDetails.getPlatform();
		// if (plat != Platform.WEB)
		// version = "330 core";
		//
		// if (version != null)
		// {
		// sb.append("#version ").append(version).append("\n");
		//
		// if (type == ShaderType.FRAGMENT)
		// sb.append("out vec4 g_FragColor;\n");
		// }
		// else
		// {
		// sb.append("#define in ").append(type == ShaderType.VERTEX ? "attribute\n" :
		// "varying\n");
		//
		// if (type == ShaderType.VERTEX)
		// sb.append("#define out varying\n");
		//
		// sb.append("#define texture texture2D\n")
		// .append("#define g_FragColor gl_FragColor\n")
		// .append("precision mediump float;\n");
		// }
		//
		// sb.append("#line 0\n");
		// }
		// for (String s : source)
		// sb.append(s);

		return sb.toString();
	}

}
