package vgl.desktop;

import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import vgl.core.exception.VGLException;
import vgl.core.exception.VGLFatalError;
import vgl.core.internal.Checks;
import vgl.desktop.gfx.layer.LayeredLayout;
import vgl.desktop.gl.VertexArray;
import vgl.desktop.input.DesktopInputSystem;
import vgl.main.VGL;
import vgl.natives.NativeUtils;
import vgl.platform.AbstractContext;
import vgl.platform.IPlatformContext;

public class DesktopContext extends AbstractContext<VGLApplication> implements IPlatformContext{

	public DesktopContext(VGLApplication app) {
		super(app);
	}

	private boolean f = true;
	
	@Override
	protected void preLoop() {
		Checks.checkIfInitialized();
		GLFW.glfwSetWindowSize(((Window)VGL.display).__nativePtr(), 1920, 1080);
		if (f) {
			f = false;
			GLFW.glfwSetWindowSize(((Window)VGL.display).__nativePtr(), application.getWindowWidth(), application.getWindowHeight());
		}
		((DesktopInputSystem) VGL.input).onGlfwInit();
	}

	@Override
	protected void postLoop() throws VGLException {
		VertexArray.freeBuffers();
		NativeUtils.clearOnFinish();
		if(VGL.api_afx.isInitialized())
			VGL.api_afx.destroyOnExit();
		application.finish();
		glfwDestroyWindow(((Window)VGL.display).__nativePtr());
	}

	@Override
	protected void loopEnd() {
		glfwSwapBuffers(((Window)VGL.display).__nativePtr());
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwPollEvents();
	}

	@Override
	protected void platformSpecificRender() {
		if (application.getLayout() != null)
			application.getLayout().render();
	}

	@Override
	protected boolean shouldStop() {
		return glfwWindowShouldClose(((Window)VGL.display).__nativePtr());
	}

	@Override
	public void createContext() {
		if(!glfwInit())
			throw new VGLFatalError("Unable to init GLFW >> Shutting down !");
		GLFWErrorCallback.createPrint(System.err).set();
		glfwDefaultWindowHints();
	}
	
	@Override
	protected long platformCurrentTime() {
		return System.nanoTime();
	}


	@Override
	protected void initGL() {
		Checks.__setglinit(true);
		((Window) VGL.display).create();
		glfwMakeContextCurrent(((Window)VGL.display).__nativePtr());
		glfwShowWindow(((Window)VGL.display).__nativePtr());
		GL.createCapabilities();
		if(VGL.display.isVerticalSyncRequested())
			glfwSwapInterval(1);
		else
			glfwSwapInterval(0);
		VGL.logger.info("VGL " + VGL.build + " | OpenGL " + glGetString(GL_VERSION));
	}

	@Override
	protected void startLoop() throws VGLException{
		loop(0.0f);
	}

	@Override
	public void toggleLooping() {
		super.toggleInternalOperations();
	}

	@Override
	protected void platformSpecificUpdate() {
		if (application.getLayout() != null)
			if (application.getLayout() instanceof LayeredLayout)
				((LayeredLayout) application.getLayout()).update();
	}


}
