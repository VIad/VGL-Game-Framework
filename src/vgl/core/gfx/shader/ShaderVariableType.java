package vgl.core.gfx.shader;

import java.util.Arrays;

import vgl.core.exception.VGLGraphicsException;
import vgl.tools.functional.Functional;

public enum ShaderVariableType {
	
	INT("int", 4),
	FLOAT("float", 4),
	VEC2("vec2",8),
	VEC3("vec3", 12),
	VEC4("vec4", 16);
	
	private ShaderVariableType(String name, int byteSize) {
		this.byteSize = byteSize;
		this.glName = name;
	}
	
	private int byteSize;
	private String glName;
	
	
	public int getByteSize() {
		return byteSize;
	}
	
	public String getGlName() {
		return glName;
	}
	
	public static ShaderVariableType parse(String name) {
		return Arrays.stream(values())
				     .filter(type -> type.glName.equals(name))
				     .findFirst()
				     .orElseThrow(Functional.bind(VGLGraphicsException::new,
				    		      "Unable to parse uniform >> "+name));
	}
	
	public static boolean exists(String var) {
		return  Arrays.stream(values())
			          .anyMatch(value -> value.glName.equals(var));
	}
	
	

}
