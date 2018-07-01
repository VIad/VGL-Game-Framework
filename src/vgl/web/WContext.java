package vgl.web;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.shc.gwtal.client.openal.AudioDecoder;
import com.shc.webgl4j.client.WebGL10;
import com.shc.webgl4j.client.WebGL20;
import com.shc.webgl4j.client.WebGLContext;
import com.vgl.gwtreq.client.CanvasDetails;
import com.vgl.gwtreq.client.Dim;
import com.vgl.gwtreq.client.VGWT;

import vgl.core.annotation.UnusedParameter;
import vgl.core.exception.VGLException;
import vgl.core.internal.ProcessManager;
import vgl.main.VGL;
import vgl.platform.AbstractContext;
import vgl.platform.IPlatformContext;

public class WContext extends AbstractContext<VGLWebApplication> implements IPlatformContext {

	private Canvas			canvas;

	private WebGLContext	wglContext;

	private CanvasDetails	details;

	public WContext(VGLWebApplication app) {
		super(app);
		this.ns = 1000.0d / (double) app.getRequestedUPS();
	}

	@Override
	public void toggleLooping() {
		super.toggleInternalOperations();
	}

	@Override
	public void setRequestedUPS(int ups) {
		this.ns = 1000.0d / (double) ups;
	}

	@Override
	public void createContext() {
		CanvasElement ce;
		canvas = Canvas.wrap(ce = (CanvasElement) Document.get().getElementById(application.renderTargetID));
		// canvasDetails = VGWT.getDetailsFromDocument(app.renderTargetID);
		initGL();

		application.set(new Dim(ce.getWidth(), ce.getHeight()));
		this.details = new CanvasDetails(ce);
	}

	private long	last	= System.currentTimeMillis();
	private long	lastTimeFixedUpdate = System.currentTimeMillis();
	private boolean	first	= true;

	@Override
	public void loop(@UnusedParameter(reason = "Required for requesting animation frames in JS") double unused)
	        throws VGLException {
		try {
			ups++;
			ProcessManager.get().runOnUpdate();
			application.update();
			VGL.input.updateDeltas();
			if ((System.currentTimeMillis() - last) > 1000) {
				last += 1000;
				if (VGL.display.isDisplayingFps())
					VGL.logger.info("FPS : " + fps + ", UPS : " + ups);
				fps = ups = 0;
			}
			if ((System.currentTimeMillis() - lastTimeFixedUpdate) > futs) {
				lastTimeFixedUpdate = System.currentTimeMillis();
				application.fixedUpdate();
			}
			loopEnd();
			application.render();
			fps++;
		} catch (VGLException e) {
			VGL.errorChannel.forward(() -> e);
		}
		if (!shouldStop()) {
			VGWT.requestAnimation();
		}
	}

	@Override
	protected void startLoop() throws VGLException {
		VGWT.requestAnimation();
	}

	@Override
	protected void initGL() {
		if (WebGL20.isSupported())
			wglContext = WebGL20.createContext(canvas);
		else
			wglContext = WebGL10.createContext(canvas);
	}

	@Override
	protected void preLoop() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void postLoop() throws VGLException {
		VGWT.requestAnimation();
	}

	@Override
	protected void loopEnd() {
		WebGL10.glClear(WebGL10.GL_COLOR_BUFFER_BIT);
	}

	@Override
	protected void platformSpecificRender() {
		// TODO Auto-generated method stub
	}

	public void set() {
		wglContext.makeCurrent();
	}

	@Override
	protected boolean shouldStop() {
		return false;
	}

	public Canvas display() {
		return canvas;
	}

	public CanvasDetails getCanvasDetails() {
		return details;
	}

	@Override
	protected void platformSpecificUpdate() {
		// TODO Auto-generated method stub

	}

}
