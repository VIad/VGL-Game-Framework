package vgl.web;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
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
	}

	@Override
	public void toggleLooping() {
		super.toggleInternalOperations();
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
	
	private long last = System.currentTimeMillis();
	
	@Override
	public void loop(@UnusedParameter(reason = "Required for requesting animation frames in JS") double unused) throws VGLException {
		try {
			ProcessManager.get().runOnUpdate();
			if (System.currentTimeMillis() > last) {
				last = System.currentTimeMillis() + 1000;
				application.fixedUpdate();
			}
			application.update();
			VGL.input.updateDeltas();
			application.render();
		} catch (VGLException e) {

		}
		VGWT.requestAnimation();
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
		WebGL10.glClear(WebGL10.GL_COLOR_BUFFER_BIT | WebGL10.GL_DEPTH_BUFFER_BIT);
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
		return true;
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
