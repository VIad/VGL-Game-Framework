package vgl.core.gfx.shader;

import java.io.File;

import org.apache.commons.collections.list.SetUniqueList;

import vgl.core.internal.GlobalDetails;
import vgl.desktop.io.FileInput;
import vgl.platform.Platform;

public class ShaderFactory {
	
	private static String batch2DVS = "#version 400 core\r\n" + 
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
	
	
	private static String batch2DFS = "#version 400 core\r\n" + 
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
	
	private static String vsBatch = "precision highp float;" + 
			"\r\n" + 
			"attribute vec3 positions;\r\n" + 
			"attribute vec4 color;\r\n" + 
			"attribute vec2 uvs;\r\n" + 
			"attribute float texId;\r\n" + 
			"\r\n" + 
			"uniform mat4 transformationMatrix;\r\n" + 
			"uniform mat4 projectionMatrix;\r\n" + 
			"\r\n" + 
			 
			"varying vec3 fs_in_positions;\r\n" + 
			"varying vec4 fs_in_color;\r\n" + 
			"varying vec2 fs_in_uvs;\r\n" + 
			"varying float fs_in_texId;\r\n" +  
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"void main(void){\r\n" + 
			"  gl_Position = projectionMatrix * transformationMatrix * vec4(positions,1.0);\r\n" + 
			"  fs_in_positions = positions;\r\n" + 
			"  fs_in_color = color;\r\n" + 
			"  fs_in_uvs = uvs;\r\n" + 
			"  fs_in_texId = texId;\r\n" + 
			"}";
	
	private static String fsBatch = "precision highp float;" + 
			"\r\n" + 
			"\r\n" +  
			"varying vec3 fs_in_positions;\r\n" + 
			"varying vec4 fs_in_color;\r\n" + 
			"varying vec2 fs_in_uvs;\r\n" + 
			"varying float fs_in_texId;\r\n" +   
			"\r\n" + 
			"uniform sampler2D textures_0;\r\n" + 
			"uniform sampler2D textures_1;\r\n" +
			"uniform sampler2D textures_2;\r\n" +
			"uniform sampler2D textures_3;\r\n" +
			"uniform sampler2D textures_4;\r\n" +
			"uniform sampler2D textures_5;\r\n" +
			"uniform sampler2D textures_6;\r\n" +
			"uniform sampler2D textures_7;\r\n" +
			"\r\n" + 
			"void main(){\r\n" + 
			"\r\n" + 
			"    vec4 color = vec4(1.0);\n"+
			"    color = fs_in_color;\r\n" + 
			"    if(fs_in_texId > 0.0)\r\n" + 
			"    {\r\n" + 
			"       int tid = int(fs_in_texId - 0.5);\r\n" + 
			"       if(tid == 0)\n "+
			"       color = texture2D(textures_0, fs_in_uvs);\r\n" +
			"       if(tid == 1)\n "+
			"       color = texture2D(textures_1, fs_in_uvs);\r\n" + 
			"       if(tid == 2)\n "+
			"       color = texture2D(textures_2, fs_in_uvs);\r\n" + 
			"       if(tid == 3)\n "+
			"       color = texture2D(textures_3, fs_in_uvs);\r\n" + 
			"       if(tid == 4)\n "+
			"       color = texture2D(textures_4, fs_in_uvs);\r\n" + 
			"       if(tid == 5)\n "+
			"       color = texture2D(textures_5, fs_in_uvs);\r\n" + 
			"       if(tid == 6)\n "+
			"       color = texture2D(textures_6, fs_in_uvs);\r\n" + 
			"       if(tid == 7)\n "+
			"       color = texture2D(textures_7, fs_in_uvs);\r\n" + 
			"    }\r\n" + 
			"    gl_FragColor = color;"+
			"}";

	public static ShaderProgram batch2DGLSL() {
		Platform platform = GlobalDetails.getPlatform();
		if (platform != Platform.WEB) {
			ShaderProgram def = new ShaderProgram(
			       batch2DVS,
			       batch2DFS
            ) {
				@Override
				public void getAllUniformLocations() {
					start();
					uniform1iv("textures", new int[] {
							0,1,2,3,4,5,6,7,
							8,9,10,11,12,13,14,
							15,16,17,18,19,20,21,22,
							23,24,25,26,27,28,29,30,31
					});
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
		}else {
			ShaderProgram def = new ShaderProgram(
					vsBatch,
					fsBatch) {
				
				@Override
				public void getAllUniformLocations() {
					start();
					uniformInt("textures_0", 0);
					uniformInt("textures_1", 1);
					uniformInt("textures_2", 2);
					uniformInt("textures_3", 3);
					uniformInt("textures_4", 4);
					uniformInt("textures_5", 5);
					uniformInt("textures_6", 6);
					uniformInt("textures_7", 7);
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
	}

}
