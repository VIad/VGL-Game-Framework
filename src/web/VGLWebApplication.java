package vgl.web;

import vgl.core.internal.GlobalDetails;
import vgl.platform.Application;
import vgl.platform.Platform;

abstract public class VGLWebApplication extends Application {
	public VGLWebApplication() {
		GlobalDetails.set(Platform.WEB);
	}
}
