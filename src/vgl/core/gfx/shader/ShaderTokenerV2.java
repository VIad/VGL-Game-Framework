package vgl.core.gfx.shader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import vgl.core.internal.GlobalDetails;
import vgl.platform.Platform;
import vgl.tools.async.UniContainer;
import vgl.tools.functional.BiContainer;

public class ShaderTokenerV2 {

	private String								vertexShader;
	private String								fragmentShader;

	private List<ShaderAttributeDeclaration>	attributes;
	private List<ShaderStruct>					structures;

	public ShaderTokenerV2(String vsSource, String fsSource) {
		this.structures = new ArrayList<>();
		this.attributes = new ArrayList<>();
		this.vertexShader = translateToGL(fsSource, ShaderType.FRAGMENT);
		this.vertexShader = postProcessShader(vertexShader, ShaderType.FRAGMENT);
		System.out.println(
		        "******************************************************************************************");
		System.out.println(
		        "******************************************************************************************");
		System.out.println(
		        "******************************************************************************************");
		System.out.println(
		        "******************************************************************************************");
		System.out.println(vertexShader);
	}

	private String postProcessShader(String shd, ShaderType type) {
		final UniContainer<String> container = new UniContainer<>();
		Platform target = GlobalDetails.getPlatform();
		container.put(shd);
		structureMap.forEach((var, struct) -> {
			container.put(container.get().replaceAll(var + "\\.", "str_" + struct.dataType + "_"));
		});
		if (type == ShaderType.FRAGMENT) {
			shd = container.get();
			String line = Arrays.stream(shd.split("\n")).filter(l -> l.startsWith("out vec4")).findFirst().orElse(null);
			shd = shd.replaceAll(line, "out vec4 g_CB_Output_px_argb;");
			if (line != null) {
				String colName = readRanged(line, "vec4", ";");
				shd = shd.replaceAll(colName, "g_FragColor");
			}
		}
		if(target == Platform.DESKTOP_X64) {
			shd = shd.replaceAll("precision highp float;", "");
			
		}
		return shd;
	}

	private static class ShaderAttributeDeclaration {
		private ShaderVariableType	type;
		private String				attrName;
		private int					index;

		ShaderAttributeDeclaration(String attrName, ShaderVariableType type) {
			this.type = type;
			this.attrName = attrName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((attrName == null) ? 0 : attrName.hashCode());
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
			ShaderAttributeDeclaration other = (ShaderAttributeDeclaration) obj;
			if (attrName == null) {
				if (other.attrName != null)
					return false;
			} else if (!attrName.equals(other.attrName))
				return false;
			if (type != other.type)
				return false;
			return true;
		}

	}

	private static class StructVariableUsage {

	}

	private Map<String, ShaderStruct> structureMap = new HashMap<>();

	private static class ShaderVariableDeclaration {
		private String				name;
		private ShaderVariableType	type;

		ShaderVariableDeclaration(ShaderVariableType type, String name) {
			this.name = name;
			this.type = type;
		}

		@Override
		public String toString() {
			return "ShaderVariableDeclaration [name=" + name + ", type=" + type + "]";
		}
	}

	private static class ShaderStruct {
		String							dataType;
		String							structureType;
		String							varName;

		List<ShaderVariableDeclaration>	variables;

		public ShaderStruct(String dataType, String structType) {
			this.dataType = dataType;
			this.structureType = structType;
			this.variables = new ArrayList<>();
		}

		String listToStr() {
			return variables.stream().map(ShaderVariableDeclaration::toString).collect(Collectors.joining("\n"));
		}

		@Override
		public String toString() {
			return "ShaderStruct [dataType=" + dataType + ", structureType=" + structureType + ",\nvariables=\n"
			        + listToStr() + "" + "\n]";
		}

	}

	private String translateToGL(String shdSrc, ShaderType type) {
		StringBuilder src = new StringBuilder();
		Platform target = GlobalDetails.getPlatform();
		addPlatformSpecificHeaders(target, src, shdSrc);
		String[] lines = shdSrc.split("\\n");
		boolean parsingStruct = false;
		for (String line : lines) {
			line = line.trim();
			if (shouldIgnore(line, target))
				continue;

			if (isStructHeader(line, shdSrc)) {
				BiContainer<String, String> data = getStructHeader(line, shdSrc);
				System.out.println("Parsing struct type >> " + data.getFirst());
				System.out.println("Datatype >> " + data.getSecond());
				structures.add(new ShaderStruct(data.getSecond(), data.getFirst()));
				parsingStruct = true;
				continue;
			}
			if (isStructFooter(line, shdSrc, parsingStruct)) {
				System.out.println("EOS}");
				parsingStruct = false;
				System.out.println("footer : " + line);
				String strVarName = shdSrc.substring(shdSrc.indexOf(line),
				        shdSrc.indexOf(';', shdSrc.indexOf(line))).replaceAll("}", "").replaceAll(";", "").trim();
				structureMap.put(strVarName, structures.get(structures.size() - 1));
				System.out.println(structures.get(structures.size() - 1));
				writeStruct(src);
				continue;
			}
			if (parsingStruct) {
				structHandle(line, shdSrc, parsingStruct);
				continue;
			}
			if (type == ShaderType.VERTEX) {
				line = parseAttributesWithLayout(line, shdSrc);
				line = parseAttributesNoLayout(line, shdSrc);
			}
			/////////////////////////////////////
			src.append(line.trim()).append('\n');
		}
		return src.toString();
	}

	private String parseAttributesNoLayout(String line, String shdSrc) {
		if (line.contains("in") && !line.contains("layout")) {
			String[] lin = line.split("\\s");
			ShaderAttributeDeclaration attrDecl = new ShaderAttributeDeclaration(
			        getStandartVariableDeclName(lin[2]),
			        ShaderVariableType.parse(lin[1]));
			if (!attributes.contains(attrDecl)) {
				attributes.add(attrDecl);
				attrDecl.index = ii++;
			}
		}
		return line;
	}

	private static int ii = 0;

	private static class Validator {
		private List<String>					wordsMatch;
		private Map<Integer, Predicate<String>>	predicates;

		private String							start;
		private String							shader;
		boolean									res	= false;

		public Validator(String shader, String line) {
			System.out.println(line);
			try {
				this.start = shader.substring(shader.indexOf(line)).trim();
			} catch (Exception ex) {
				res = true;
			}
			this.wordsMatch = new ArrayList<>();
			this.predicates = new HashMap<>();
		}

		int index = 0;

		public Validator next(String nextMatch) {
			wordsMatch.add(index++, nextMatch);

			return this;
		}

		public Validator next(Predicate<String> stringPredicate) {
			predicates.put(index++, stringPredicate);
			return this;
		}

		public boolean isCorrect() {
			if (res)
				return false;
			String[] st = start.split("\\s");
			for (int i = 0; i < index; i++) {
				if (predicates.containsKey((int) i)) {
					if (!predicates.get(i).test(st[i]))
						return false;
					continue;
				}
				if (wordsMatch.get(i).equals("__ANY"))
					continue;
				if (!st[i].trim().equals(wordsMatch.get(i)))
					return false;
			}
			return true;
		}
	}

	private void writeStruct(StringBuilder src) {
		ShaderStruct struct = structures.get(structures.size() - 1);
		struct.variables.forEach(
		        variable -> src.append(struct.structureType).append(' ').append(variable.type.getGlName()).append(
		                ' ').append("str_").append(struct.dataType).append('_').append(variable.name).append(
		                        ';').append('\n'));
	}

	private String parseAttributesWithLayout(String line, String shdSrc) {
		if (line.contains("layout") && line.contains("location") && line.startsWith("in")) {
			int location = Integer.parseInt(readRanged(line, "location", ")").replaceAll("=", "").trim());
			String[] stdDecl = readRanged(line, ")", "___EOF").trim().split("\\s");
			ShaderAttributeDeclaration attrDecl = new ShaderAttributeDeclaration(
			        getStandartVariableDeclName(stdDecl[1]),
			        ShaderVariableType.parse(stdDecl[0]));
			if (!attributes.contains(attrDecl)) {
				attributes.add(attrDecl);
				attrDecl.index = location;
			}
			return "in " + attributes.get(attributes.size() - 1).type.getGlName() + " "
			        + attributes.get(attributes.size() - 1).attrName + ";";

		} else
			return line;
	}

	private String readRanged(String line, String from, String to) {
		int index = line.indexOf(from) + from.length();
		if (to.equals("___EOF"))
			return line.substring(index);
		return line.substring(index, line.indexOf(to, index));
	}

	private void structHandle(String line, String shdSrc, boolean parsingStruct) {
		String[] spl = line.split("\\s");
		if (line.trim().equals("{"))
			return;
		if (checkIfVariableDecl(line)) {
			structures.get(structures.size() - 1).variables.add(new ShaderVariableDeclaration(
			        ShaderVariableType.parse(spl[0]),
			        getStandartVariableDeclName(spl[1])));
		}
	}

	private String getStandartVariableDeclName(String expectedName) {
		if (expectedName.endsWith(";"))
			return expectedName.substring(0, expectedName.length() - 1).trim();
		return expectedName.trim();
	}

	private boolean checkIfVariableDecl(String line) {
		String[] ln = line.split("\\s");
		if (ln.length != 2)
			if (!ln[2].trim().equals("}"))
				return false;
		if (!ShaderVariableType.exists(ln[0]))
			return false;
		return true;
	}

	private boolean isStructFooter(String line, String shdSrc, boolean parsingStruct) {
		if (!parsingStruct)
			return false;
		return line.startsWith("}");
	}

	private boolean isStructHeader(String line, String shdSrc) {
		String[] spl = line.split("\\s");
		if (spl[0].equals("in") || spl[0].equals("out")) {
			if (spl.length == 3)
				return spl[2].equals("{");
			else
				return line.endsWith("{")
				        ? true
				        : shdSrc.substring(shdSrc.indexOf(line) + line.length()).trim().startsWith("{");
		}
		return false;
	}

	private BiContainer<String, String> getStructHeader(String line, String shdSrc) {
		String[] spl = line.split("\\s");
		String strReturnType = line.startsWith("in") ? "in" : "out";
		int fromIndex = shdSrc.indexOf(line) + line.indexOf(strReturnType) + strReturnType.length();
		return new BiContainer<>(strReturnType, shdSrc.substring(fromIndex, shdSrc.indexOf('{', fromIndex)).trim());
	}

	private boolean shouldIgnore(String line, Platform target) {
		if (target == Platform.WEB) {
			if (line.startsWith("#version"))
				return true;
		} else {
			if (line.startsWith("precision"))
				return true;
		}
		return false;
	}

	private void addPlatformSpecificHeaders(Platform target, StringBuilder src, String shader) {
		if (target == Platform.WEB) {
			if (!shader.contains("precision"))
				src.append("precision highp float;\n");
		} else {
			if (!shader.contains("#version"))
				src.append("#version 330 core\n");
		}
	}

}
