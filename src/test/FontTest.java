package test;

import java.util.ArrayList;
import java.util.List;

import glm_.vec2.Vec2;
import glm_.vec4.Vec4;
import imgui.Context;
import imgui.IO;
import imgui.ImGui;
import imgui.impl.ImplGL3;
import imgui.impl.LwjglGlfw;
import imgui.impl.LwjglGlfw.GlfwClientApi;
import uno.glfw.GlfwWindow;
import vgl.audio.AudioManager;
import vgl.core.exception.VGLException;
import vgl.core.gfx.font.BMFont;
import vgl.core.gfx.layer.GFX2D;
import vgl.core.gfx.layer.ILayer2D;
import vgl.core.gfx.layer.LayeredLayout;
import vgl.core.input.Key;
import vgl.core.input.KeyEvent.EventType;
import vgl.core.internal.Checks;
import vgl.desktop.VGLApplication;
import vgl.desktop.Window;
import vgl.main.VGL;

public class FontTest {

	private static BMFont			font;

	private static long				start	= System.currentTimeMillis();

	private static LayeredLayout	layeredLayout;

	static class App extends VGLApplication {

		public App(String title, int window_width, int window_height) {
			super(title, window_width, window_height);
			setVerticalSynchronized(false);
			setFixedUpdateTimestamp(.1f);
		}

		@Override
		public void update() throws VGLException {
			if (VGL.input.isKeyDown(Key.F10)) {
				AudioManager.fetch("thats").play();
				
			}
		}

		char[] buffer = new char[1024];
		
		@Override
		public void render() throws VGLException {
			lwjglGlfw.newFrame();
//			imgui.text("Hello, world!"); // Display some text (you can use a format string too)
//			imgui.sliderFloat("float", f, 0f, 1f, "%.3f", 1f); // Edit 1 float using a slider from 0.0f to 1.0f
//			imgui.colorEdit3("clear color", clearColor, 0); // Edit 3 floats representing a color
//			imgui.sliderFloat2("labeline", vec.getArray(), 0f, 16f, "%.3f", 1f);
//			imgui.checkbox("Demo Window", showDemo); // Edit bools storing our windows open/close state
//			imgui.checkbox("Another Window", showAnotherWindow);
//
//			if (imgui.button("Button", new Vec2())) // Buttons return true when clicked (NB: most widgets return true
//			                                        // when edited/activated)
//				counter[0]++;
//
//			imgui.sameLine(0f, -1f);
//			imgui.text("counter = "+counter[0]);
//
//			imgui.text("Application average %.3f ms/frame (%.1f FPS)", 1_000f / io.getFramerate(), io.getFramerate());
//
//			// 2. Show another simple window. In most cases you will use an explicit
//			// begin/end pair to name the window.
//			if (showAnotherWindow[0]) {
//				imgui.begin("Another Window", showAnotherWindow, 0);
//				imgui.text("Hello from another window!");
//				if (imgui.button("Close Me", new Vec2()))
//					showAnotherWindow[0] = false;
//				imgui.end();
//			}
//
//			/*
//			 * 3. Show the ImGui demo window. Most of the sample code is in
//			 * imgui.showDemoWindow(). Read its code to learn more about Dear ImGui!
//			 */
//			if (showDemo[0]) {
//				/*
//				 * Normally user code doesn't need/want to call this because positions are saved
//				 * in .ini file anyway. Here we just want to make the demo initial state a bit
//				 * more friendly!
//				 */
//				imgui.setNextWindowPos(new Vec2(650, 20), Cond.FirstUseEver, new Vec2());
//				imgui.showDemoWindow(showDemo);
//			}
			imgui.begin("What the fuck", new boolean[] {true}, 0);
			imgui.button("ButtHole", new Vec2());
			imgui.text("Frametime : "+ imgui.getIo().getFramerate());
			
			imgui.plotLines("Frames", frameRates, frameRates.length, "", 0f, 500f, new Vec2(200,30), 1);
			
//			imgui.plotHistogram("Frames", arr, 0, "", 0f, 500f, new Vec2(200,30), 0);
//			imgui.plotLines(label, values, valuesOffset, overlayText, scaleMin, scaleMax, graphSize, stride);;
			imgui.sliderVec2("Box Transform", vec, 0f, 16f, "%.3f", 1f);
			imgui.dragVec2("Transform", vec, 0.2f, 0f, 16f, "%.3f", 1f);
			
			if(imgui.inputText("Get text boi", new char[200], 0)) {				
				
			}
			
			imgui.end();
			// Rendering
			gln.GlnKt.glViewport(window.getFramebufferSize());
			

			imgui.onRender();
			implGL3.renderDrawData(imgui.getDrawData());

			gln.GlnKt.checkError("loop", true); // TODO remove
		}

		private GlfwWindow	window;
		private Context		ctx;
		private IO			io;

		@Override
		public void init() throws VGLException {
			window = new GlfwWindow(((Window) VGL.display).__nativePtr());
			window.init(true);

			// Setup ImGui binding
			// setGlslVersion(330); // set here your desidered glsl version
			ctx = new Context(null);
			// io.configFlags = io.configFlags or ConfigFlag.NavEnableKeyboard // Enable
			// Keyboard Controls
			// io.configFlags = io.configFlags or ConfigFlag.NavEnableGamepad // Enable
			// Gamepad Controls
			lwjglGlfw.init(window, true, GlfwClientApi.OpenGL);
			io = imgui.getIo();

			// Setup style
			imgui.styleColorsDark(null);
			setLayout(layeredLayout = new LayeredLayout());
			layeredLayout.pushLayer(new ILayer2D(16f, 9f) {
				
				@Override
				public void update() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void render(GFX2D graphics) {
					graphics.drawRectangle(vec.r(), vec.g(), 3f, 3f);
				}
			});
			

		}

		private Vec2 vec = new Vec2(2f,2f);	
		private float[]			f					= { 0f };
		private Vec4			clearColor			= new Vec4(0.45f, 0.55f, 0.6f, 1f);
		private boolean[]		showAnotherWindow	= { false };
		private boolean[]		showDemo			= { true };
		private int[]			counter				= { 0 };

		private uno.glfw.glfw	glfw				= uno.glfw.glfw.INSTANCE;
		private LwjglGlfw		lwjglGlfw			= LwjglGlfw.INSTANCE;
		private ImplGL3			implGL3				= ImplGL3.INSTANCE;
		private ImGui			imgui				= ImGui.INSTANCE;

		@Override
		public void finish() throws VGLException {
			// TODO Auto-generated method stub

		}

		private float[] frameRates = new float[100];
		
		private int lastIndex = 0;
		@Override
		public void fixedUpdate() throws VGLException {
			frameRates[lastIndex++] = io.getFramerate();
			if(lastIndex == 100) {
				lastIndex = 0;
			for (int i = 0; i < frameRates.length; i++) {
				frameRates[i] = 0;
			}
			}
		}
		
	}

	public static void main(String[] args) {
		Checks.__setglinit(true);
		VGLApplication app = new App("title", 800, 600);
		app.setUpdatesPerSecond(60);
		app.setResizable(true);
		app.startApplication();
	}

}
