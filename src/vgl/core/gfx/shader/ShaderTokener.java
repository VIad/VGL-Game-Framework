package vgl.core.gfx.shader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import vgl.core.internal.GlobalDetails;
import vgl.platform.Platform;

public class ShaderTokener {

	private String		vs, fs;
	private ShaderData	data;

	public ShaderTokener(String vs, String fs) {
		this.data = new ShaderData();
		this.vs = translate(vs, ShaderType.VERTEX);
		this.fs = translate(fs, ShaderType.FRAGMENT);
		// begin();
	}

	public String getVertexSourceSafe() {
		return vs;
	}

	public String getFragmentSourceSafe() {
		return fs;
	}

	public ShaderData getData() {
		return data;
	}

	public static ShaderTokener.AttributeDeclaration attributeOnLine(String line) {
		// AttributeDeclaration decl = new AttributeDeclaration(li, type)
		String[] lineSpl = line.split("\\s");
		return new AttributeDeclaration(
		        0,
		        lineSpl[lineSpl.length - 1].substring(0, lineSpl[lineSpl.length - 1].length() - 1),
		        ShaderVariableType.parse(lineSpl[lineSpl.length - 2]));
	}

	public static int getLocation(String line) {
		String[] l = line.split("\\s");
		if (l.length == 3)
			return last++;
		String fromLoc = line.substring(line.indexOf("location") + 8);
		String number = fromLoc.substring(fromLoc.indexOf('=') + 1, fromLoc.indexOf(')')).trim();
		return Integer.parseInt(number);
	}

	private static int last = 0;

	public static class AttributeDeclaration {
		private String				name;
		private ShaderVariableType	type;
		private int					index;

		public AttributeDeclaration(int index, String name, ShaderVariableType type) {
			super();
			this.index = index;
			this.name = name;
			this.type = type;
		}

		public int getIndex() {
			return index;
		}

		public String getName() {
			return name;
		}

		public ShaderVariableType getType() {
			return type;
		}

		@Override
		public String toString() {
			return "AttributeDeclaration [name=" + name + ", type=" + type + ", index=" + index + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + index;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AttributeDeclaration other = (AttributeDeclaration) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (type != other.type)
				return false;
			return true;
		}

	}

	public class UniformDeclaration {
		private String				name;
		private ShaderUniformType	type;
		private ShaderType			shaderType;

		public UniformDeclaration(String name, ShaderType sType, ShaderUniformType type) {
			this.name = name;
			this.type = type;
			this.shaderType = sType;
		}

		public String getName() {
			return name;
		}

		public ShaderUniformType getType() {
			return type;
		}

		@Override
		public String toString() {
			return "UniformDeclaration [name=" + name + ", type=" + type + ", shaderType=" + shaderType + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((shaderType == null) ? 0 : shaderType.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			UniformDeclaration other = (UniformDeclaration) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (shaderType != other.shaderType)
				return false;
			if (type != other.type)
				return false;
			return true;
		}

		private ShaderTokener getOuterType() {
			return ShaderTokener.this;
		}

	}

	public class ShaderData {
		Set<UniformDeclaration>		uniforms;
		Set<AttributeDeclaration>	attributes;

		public ShaderData() {
			this.uniforms = new HashSet<>();
			this.attributes = new HashSet<>();
		}

		public Set<AttributeDeclaration> getAttributes() {
			return attributes;
		}

		public Set<UniformDeclaration> getUniforms() {
			return uniforms;
		}
	}

	public String translate(String shaderSrc, ShaderType type) {
		return translate(shaderSrc, type, GlobalDetails.getPlatform());
	}

	private HashMap<String, String> blockTypeToVariable = new HashMap<>();

	public String translate(String shaderSrc, ShaderType type, Platform to) {
		StringBuilder src = new StringBuilder();

		Platform platform = to;
		// if(to == Platform.WEB && ShaderTools.determineShaderPlatform(shaderSrc) !=
		// ShaderCompiler.GLES) {
		// shaderSrc = translate(shaderSrc, type, Platform.DESKTOP_X64);
		// }
		String shaderSrcActual = shaderSrc;
		if (to == Platform.WEB) {
			shaderSrcActual = translate(shaderSrc, type, Platform.DESKTOP_X64);

		}
		String[] lines = shaderSrcActual.split("\n");

		if (platform != Platform.WEB) {
			if (!shaderSrcActual.contains("#version")) {
				src.append("#version 330 core\n");
			}
			boolean block = false;
			List<String> blockVariables = new ArrayList<>();
			List<String> blockPrefixes = new ArrayList<>();
			String blockName = null;
			String blockDataType = null;
			String outColor = null;
			int blockType = 0;
			for (String s : lines) {
				String lineActual = s.trim();

				if (lineActual.contains("precision"))
					continue;

				lineActual = lineActual.startsWith("varying")
				        ? lineActual.replaceAll("varying", type == ShaderType.VERTEX ? "out" : "in")
				        : lineActual;
				lineActual = lineActual.replaceAll("texture2D\\(", "texture(");
				lineActual = lineActual.replaceAll("textureCube\\(", "texture(");

				if (type == ShaderType.VERTEX)
					lineActual = lineActual.replaceAll("attribute", "in");
				lineActual = lineRemoveComments(lineActual).trim();
				getUniforms(lineActual, type);

				if (isAttributeBlockHeader("in", lineActual, shaderSrcActual)) {
					block = true;
					blockType = 1;// In
					blockDataType = getBlockDataType(lineActual);
					continue;
				}
				if (isAttributeBlockHeader("out", lineActual, shaderSrcActual)) {
					block = true;
					blockType = 2;
					blockDataType = getBlockDataType(lineActual);
					continue;
				}
				if (getAttributeBlockFooter(lineActual) != null) {
					block = false;
					blockName = getAttributeBlockFooter(lineActual);
					blockPrefixes.add(blockName);
					blockTypeToVariable.put(blockName, blockDataType);
					blockDataType = null;
					final int bType = blockType;
					blockType = 0;
					for (String bv : blockVariables) {
						src.append(bType == 1 ? "in " : "out ").append(bv.split("\\s")[0]).append(' ').append(
						        blockName).append('_').append(bv.split("\\s")[1]).append('\n');
					}
					blockVariables.clear();
					continue;
				}
				if (block) {
					String var = getIfStandartVariableDeclaration(lineActual);
					if (var != null)
						blockVariables.add(var);
					continue;
				}

				if (type == ShaderType.VERTEX) {
					if (isStandartAttributeDeclaration("in", lineActual)) {
						AttributeDeclaration decl = attributeOnLine(lineActual);
						boolean added = data.attributes.add(decl);
						if (added)
							decl.index = last++;
					}
					if (lineActual.contains("in ") && lineActual.contains("layout")) {
						AttributeDeclaration decl = attributeOnLine(lineActual);
						boolean added = data.attributes.add(decl);
						if (added)
							decl.index = last++;
						lineActual = "in " + decl.getType().getGlName() + " " + decl.getName() + ";\n";
					}
				}
				if (!lineEmpty(lineActual))
					src.append(lineActual.trim()).append('\n');
			}
//			String s = src.toString();
//			System.out.println("heer : "+s);
//			src = new StringBuilder();
//			for (String bp : blockPrefixes) {
//				s = s.replaceAll(bp + ".", bp + "_");
//				s = s.replaceAll(bp + "_", "pass_" + blockTypeToVariable.get(bp) + "_");
//				for (String lineActual : s.split("\n")) {
//					if (type == ShaderType.FRAGMENT) {
//						if (outColor != null && lineActual.contains(outColor)) {
//							
//						}
//						if (lineActual.contains("out vec4")) {
//							
//							String outColorWS = readSized(lineActual, "out vec4", ";").trim();
//							// outColor = outColorWS.substring(0, outColorWS.length() - 1);
//							System.out.println(outColorWS);
//							outColor = outColorWS.trim();
//							lineActual = "out vec4 g_CB_clr_output_rgb;\n";
//						}
//					}
//					src.append(lineActual.trim())
//					   .append('\n');
//				}
//			}
			return src.toString();
		} else {
			if (!shaderSrcActual.contains("precision"))
				src.append("precision highp float;\n");
			String outColor = null;
			for (String s : lines) {
				String lineActual = s.trim();
				if (lineActual.contains("#version"))
					continue;
				if (type == ShaderType.FRAGMENT) {
					if (outColor != null && s.contains(outColor)) {
						lineActual = lineActual.replaceAll(outColor, "gl_FragColor");
					}
					if (lineActual.contains("out vec4")) {
						String outColorWS = lineActual.split("\\s")[2];
						outColor = outColorWS.substring(0, outColorWS.length() - 1);
						lineActual = "";
					}
				}

				if (lineActual.startsWith("in ")) {
					String lineRest = lineActual.substring(2);
					lineActual = (type == ShaderType.VERTEX ? "attribute" : "varying") + lineRest;
				}
				if (type == ShaderType.VERTEX) {
					if (lineActual.startsWith("out ")) {
						String lineRest = lineActual.substring(3);
						lineActual = "varying" + lineRest;
					}
				}

				lineActual = lineActual.replaceAll("texture\\(", "texture2D(");
				lineActual = lineRemoveComments(lineActual).trim();
				getUniforms(lineActual, type);
				if (type == ShaderType.VERTEX) {
					if (isStandartAttributeDeclaration("attribute", lineActual)) {
						AttributeDeclaration decl = attributeOnLine(lineActual);
						boolean added = data.attributes.add(decl);
						if (added)
							decl.index = last++;
					}
				}
				if (!lineEmpty(lineActual))
					src.append(lineActual.trim()).append('\n');
			}
		}

		return src.toString();
	}

	private void getUniforms(String lineActual, ShaderType type) {
		if (lineActual.startsWith("uniform")) {
			String[] l = lineActual.split("\\s");
			data.uniforms.add(new UniformDeclaration(
			        l[2].endsWith(";") ? l[2].substring(0, l[2].length() - 1) : l[2],
			        type,
			        ShaderUniformType.parse(l[1])));
		}
	}

	private static String readSized(String input, String start, String end) {
		return input.substring(input.indexOf(start) + start.length(), input.indexOf(end));
	}

	private String getBlockDataType(String line) {
		String[] l = line.split("\\s");
		if (l[1].endsWith("{")) {
			return l[1].substring(0, l[1].length() - 1).trim();
		}
		return l[1].trim();
	}

	private String getAttributeBlockFooter(String lineActual) {
		String[] line = lineActual.split("\\s");
		return line.length != 2
		        ? null
		        : !line[0].trim().equals("}") ? null : line[1].trim().substring(0, line[1].trim().length() - 1);
	}

	private boolean isStandartAttributeDeclaration(String plat, String line) {
		String[] l = line.split("\\s");
		return l.length == 3 && l[0].trim().equals(plat) && ShaderVariableType.exists(l[1].trim());
	}

	private String getIfStandartVariableDeclaration(String line) {
		String[] l = line.split("\\s");
		return l.length != 2 ? null : !ShaderVariableType.exists(l[0]) ? null : line;
	}

	private boolean isAttributeBlockHeader(String blockType, String line, String shaderSrcActual) {
		String[] l = line.split("\\s");
		if (l.length == 2)
			if (l[0].trim().equals(blockType)
			        && shaderSrcActual.substring(shaderSrcActual.indexOf(line) + line.length()).replaceAll("\\s",
			                "").startsWith("{"))
				return true;
		if (l.length >= 2)
			return l[0].trim().equals(blockType) && l[l.length - 1].trim().endsWith("{");
		return false;
	}

	private static String lineRemoveComments(String line) {
		boolean hasComments = line.indexOf("//") != -1;
		// boolean hasNwLine = line.indexOf('\n') != -1;
		if (!hasComments)
			return line;
		return line.substring(0, line.indexOf("//"));
	}

	private static boolean lineEmpty(String line) {
		return line.trim().isEmpty();
	}

}
