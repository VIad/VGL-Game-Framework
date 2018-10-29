package vgl.web.utils;

import java.util.function.Supplier;

import com.google.gwt.user.client.Window;

import vgl.platform.logging.IPromptLogger;

public class WebPromptLogger implements IPromptLogger {

	@Override
	public void promptMessage(String message) {
		Window.alert(message);
	}

	@Override
	public void promptError(Supplier<StackTraceElement[]> error) {
		Window.alert("");
	}

	@Override
	public String getInputMessageBox(String message) {
		return Window.prompt(message, "");
	}

	

}
