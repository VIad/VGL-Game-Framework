package test;

import java.util.Arrays;

public class ShaderToolsTest {


	private static String vsBatch = "#version 400 core\r\n" + 
			"\r\n" + 
			"in layout (location = 0) vec3 positions;\r\n" + 
			"in layout (location = 1) vec4 color;\r\n" + 
			"in layout (location = 2) vec2 uvs;\r\n" + 
			"in layout (location = 3) float texId;\r\n" + 
			"\r\n" + 
			"uniform mat4 transformationMatrix = mat4(1);\r\n" + 
			"uniform mat4 projectionMatrix;\r\n" + 
			"\r\n" + 
			"out DATA{\r\n" + 
			" vec3 positions;\r\n" + 
			" vec4 color;\r\n" + 
			" vec2 uvs;\r\n" + 
			" float texId;\r\n" + 
			"} vs_out;\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"void main(void){\r\n" + 
			"  gl_Position = projectionMatrix * transformationMatrix * vec4(positions,1.0f);\r\n" + 
			"  vs_out.positions = positions;\r\n" + 
			"  vs_out.color = color;\r\n" + 
			"  vs_out.uvs = uvs;\r\n" + 
			"  vs_out.texId = texId;\r\n" + 
			"}";
	
	
	private static String fsBatch = "#version 400 core\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"in DATA{\r\n" + 
			" vec3 positions;\r\n" + 
			" vec4 color;\r\n" + 
			" vec2 uvs;\r\n" + 
			" float texId;\r\n" + 
			"} fs_in;\r\n" + 
			"\r\n" + 
			"out vec4 col;\r\n" + 
			"\r\n" + 
			"const float cIntensity = 0.7f;\r\n" + 
			"uniform sampler2D textures[32];\r\n" + 
			"\r\n" + 
			"void main(){\r\n" + 
			"\r\n" + 
			"    col = fs_in.color;\r\n" + 
			"    if(fs_in.texId > 0.0)\r\n" + 
			"    {\r\n" + 
			"       int tid = int(fs_in.texId - 0.5);\r\n" + 
			"       col = texture(textures[tid], fs_in.uvs);\r\n" + 
			"    }\r\n" + 
			"}";
	
	private static String someVS = "#version 330 core\r\n" + 
			"\r\n" + 
			"layout (location = 0) in vec4 position;\r\n" + 
			"layout (location = 1) in vec2 uv;\r\n" + 
			"layout (location = 2) in vec2 mask_uv;\r\n" + 
			"layout (location = 3) in float tid;\r\n" + 
			"layout (location = 4) in float mid;\r\n" + 
			"layout (location = 5) in vec4 color;\r\n" + 
			"\r\n" + 
			"uniform mat4 sys_ProjectionMatrix;\r\n" + 
			"uniform mat4 sys_ViewMatrix;\r\n" + 
			"uniform mat4 sys_ModelMatrix;\r\n" + 
			"\r\n" + 
			"uniform mat4 sys_MaskMatrix;\r\n" + 
			"\r\n" + 
			"out DATA\r\n" + 
			"{\r\n" + 
			"	vec4 position;\r\n" + 
			"	vec2 uv;\r\n" + 
			"	vec2 mask_uv;\r\n" + 
			"	float tid;\r\n" + 
			"	float mid;\r\n" + 
			"	vec4 color;\r\n" + 
			"} vs_out;\r\n" + 
			"\r\n" + 
			"void main()\r\n" + 
			"{\r\n" + 
			"	gl_Position = sys_ProjectionMatrix * position;\r\n" + 
			"	vs_out.position = position;\r\n" + 
			"	vs_out.uv = uv;\r\n" + 
			"	vs_out.tid = tid;\r\n" + 
			"	vs_out.mid = mid;\r\n" + 
			"	vs_out.color = color;\r\n" + 
			"	vs_out.mask_uv = mask_uv;\r\n" + 
			"};";
	
	private static String someFS = "#version 330 core\r\n" + 
			"\r\n" + 
			"layout (location = 0) out vec4 color;\r\n" + 
			"\r\n" + 
			"in DATA\r\n" + 
			"{\r\n" + 
			"	vec4 position;\r\n" + 
			"	vec2 uv;\r\n" + 
			"	vec2 mask_uv;\r\n" + 
			"	float tid;\r\n" + 
			"	float mid;\r\n" + 
			"	vec4 color;\r\n" + 
			"} fs_in;\r\n" + 
			"\r\n" + 
			"uniform sampler2D textures[32];\r\n" + 
			"\r\n" + 
			"void main()\r\n" + 
			"{\r\n" + 
			"	vec4 texColor = fs_in.color;\r\n" + 
			"	vec4 maskColor = vec4(1.0, 1.0, 1.0, 1.0);\r\n" + 
			"	if (fs_in.tid > 0.0)\r\n" + 
			"	{\r\n" + 
			"		int tid = int(fs_in.tid - 0.5);\r\n" + 
			"		texColor = fs_in.color * texture(textures[tid], fs_in.uv);\r\n" + 
			"	}\r\n" + 
			"	if (fs_in.mid > 0.0)\r\n" + 
			"	{\r\n" + 
			"		int mid = int(fs_in.mid - 0.5);\r\n" + 
			"		maskColor = texture(textures[mid], fs_in.mask_uv);\r\n" + 
			"	}\r\n" + 
			"	color = texColor * maskColor; // vec4(1.0 - maskColor.x, 1.0 - maskColor.y, 1.0 - maskColor.z, 1.0);\r\n" + 
			"};";
	
	public static void main(String[] args) {
		
	}
	
	
}
