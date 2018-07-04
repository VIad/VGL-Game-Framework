package test;

import vgl.core.exception.VGLException;
import vgl.core.gfx.shader.ShaderCompiler;
import vgl.core.gfx.shader.ShaderTokener;
import vgl.core.gfx.shader.ShaderType;
import vgl.core.internal.GlobalDetails;
import vgl.main.Application;
import vgl.platform.Platform;
import vgl.tools.ShaderTools;

public class ShaderToolsTest {

	static String vsShdGLES = "precision highp float;                             \n"
	        + "                                                     \n"
	        + "                                 \n"
	        + "                                                     \n"
	        + "uniform mat4 projection;                            \n"
	        + "uniform mat4 trans;                      \n" + "                            \n"
	        + "attribute vec2 position;                             \n"
	        + "attribute vec2 uvs;                             \n"
	        + "varying vec4 fs_position;                           \n"
	        + "                                                     \n"
	        + "void main()                                          \n"
	        + "{                                                    \n"
	        + "     fs_position =  trans * vec4(position, 0.0,1.0);                               \n"
	        + "     gl_Position =  projection * fs_position;   \n" + "}";
	
	static String fsShdGLES =  " #version 330 core                          \n"
	        + "uniform vec4 color;                                                \n"
	        + "uniform vec2 lightPos;                            \n"
	        + "in vec4 fs_position;                        \n"
	        + "out vec4 outputColor;                                                 \n"
	        + "void main()                                      \n"
	        + "{                                                \n"
	        + "    float intensity = 1.0 / length(fs_position.xy - lightPos) * 0.7; \n"
	        + "    vec4 col = vec4(vec3(color.r, color.g,color.b) * intensity, 1.0);                                                                  \n"
	        + "    outputColor = col;   \n" + "}";
	
	private static String vs = "#version 330 core\n"
	           +"                        "
	           +"                        "
	           +"in vec2 pos;      \n"
	           +"in vec2 texCoords;     \n "
	           +"out vec2 fs_tex;      \n"
	           +"void main(){            \n            "
	           +"fs_tex = texCoords;       \n                 "
	           +"  gl_Position = vec4(pos, 1.0,1.0);\n       "
	           +"}                        ";
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
			"out vec4 pass_Color;\r\n" + 
			"\r\n" + 
			"const float cIntensity = 0.7f;\r\n" + 
			"uniform sampler2D textures[32];\r\n" + 
			"\r\n" + 
			"void main(){\r\n" + 
			"\r\n" + 
			"    pass_Color = fs_in.color;\r\n" + 
			"    if(fs_in.texId > 0.0)\r\n" + 
			"    {\r\n" + 
			"       int tid = int(fs_in.texId - 0.5);\r\n" + 
			"       pass_Color = texture(textures[tid], fs_in.uvs);\r\n" + 
			"    }\r\n" + 
			"}";
private static String fs = "#version 330 core\n"
            +"                        "
            +"      "
            +"uniform sampler2D tex;    \n  "
            +"in vec2 fs_tex;      \n"
            +"out vec4 gl_FragColor;    \n"
            +"void main(){               \n         "
            +"  gl_FragColor = texture(tex, fs_tex);     \n  "
            +"}";

private static String someShd = "uniform mat4 u_MVPMatrix;       // A constant representing the combined model/view/projection matrix.\r\n" + 
		"uniform mat4 u_MVMatrix;        // A constant representing the combined model/view matrix.\r\n" + 
		" \r\n" + 
		"attribute vec4 a_Position;      // Per-vertex position information we will pass in.\r\n" + 
		"attribute vec4 a_Color;         // Per-vertex color information we will pass in.\r\n" + 
		"attribute vec3 a_Normal;        // Per-vertex normal information we will pass in.\r\n" + 
		"attribute vec2 a_TexCoordinate; // Per-vertex texture coordinate information we will pass in.\r\n" + 
		" \r\n" + 
		"varying vec3 v_Position;        // This will be passed into the fragment shader.\r\n" + 
		"varying vec4 v_Color;           // This will be passed into the fragment shader.\r\n" + 
		"varying vec3 v_Normal;          // This will be passed into the fragment shader.\r\n" + 
		"varying vec2 v_TexCoordinate;   // This will be passed into the fragment shader.\r\n" + 
		" \r\n" + 
		"// The entry point for our vertex shader.\r\n" + 
		"void main()\r\n" + 
		"{\r\n" + 
		"    // Transform the vertex into eye space.\r\n" + 
		"    v_Position = vec3(u_MVMatrix * a_Position);\r\n" + 
		" \r\n" + 
		"    // Pass through the color.\r\n" + 
		"    v_Color = a_Color;\r\n" + 
		" \r\n" + 
		"    // Pass through the texture coordinate.\r\n" + 
		"    v_TexCoordinate = a_TexCoordinate;\r\n" + 
		" \r\n" + 
		"    // Transform the normal's orientation into eye space.\r\n" + 
		"    v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));\r\n" + 
		" \r\n" + 
		"    // gl_Position is a special variable used to store the final position.\r\n" + 
		"    // Multiply the vertex by the matrix to get the final point in normalized screen coordinates.\r\n" + 
		"    gl_Position = u_MVPMatrix * a_Position;\r\n" + 
		"}";

private static String someShdF = "precision mediump float;        // Set the default precision to medium. We don't need as high of a\r\n" + 
		"                                // precision in the fragment shader.\r\n" + 
		"uniform vec3 u_LightPos;        // The position of the light in eye space.\r\n" + 
		"uniform sampler2D u_Texture;    // The input texture.\r\n" + 
		" \r\n" + 
		"varying vec3 v_Position;        // Interpolated position for this fragment.\r\n" + 
		"varying vec4 v_Color;           // This is the color from the vertex shader interpolated across the\r\n" + 
		"                                // triangle per fragment.\r\n" + 
		"varying vec3 v_Normal;          // Interpolated normal for this fragment.\r\n" + 
		"varying vec2 v_TexCoordinate;   // Interpolated texture coordinate per fragment.\r\n" + 
		" \r\n" + 
		"// The entry point for our fragment shader.\r\n" + 
		"void main()\r\n" + 
		"{\r\n" + 
		"    // Will be used for attenuation.\r\n" + 
		"    float distance = length(u_LightPos - v_Position);\r\n" + 
		" \r\n" + 
		"    // Get a lighting direction vector from the light to the vertex.\r\n" + 
		"    vec3 lightVector = normalize(u_LightPos - v_Position);\r\n" + 
		" \r\n" + 
		"    // Calculate the dot product of the light vector and vertex normal. If the normal and light vector are\r\n" + 
		"    // pointing in the same direction then it will get max illumination.\r\n" + 
		"    float diffuse = max(dot(v_Normal, lightVector), 0.0);\r\n" + 
		" \r\n" + 
		"    // Add attenuation.\r\n" + 
		"    diffuse = diffuse * (1.0 / (1.0 + (0.10 * distance)));\r\n" + 
		" \r\n" + 
		"    // Add ambient lighting\r\n" + 
		"    diffuse = diffuse + 0.3;\r\n" + 
		" \r\n" + 
		"    // Multiply the color by the diffuse illumination level and texture value to get final output color.\r\n" + 
		"    gl_FragColor = (v_Color * diffuse * texture2D(u_Texture, v_TexCoordinate));\r\n" + 
		"  }";

private static String sparky_batch_vs = "#version 330 core\r\n" + 
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
	
	public static void main(String[] args) {
		GlobalDetails.set(Platform.WEB);
		ShaderTokener tokener = null;
		System.out.println((tokener = new ShaderTokener(vs, fs)).translate(someShdF, ShaderType.FRAGMENT));
		System.out.println(tokener.getData().getAttributes());
		System.out.println(tokener.getData().getUniforms());
	}
	
}
