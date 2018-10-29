package vgl.desktop.utils;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import vgl.platform.logging.IPromptLogger;

public class DesktopPromptLogger implements IPromptLogger{

	@Override
	public void promptMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public void promptError(Supplier<StackTraceElement[]> error) {
		//Not yet working. needs to pause context
		JOptionPane.showMessageDialog(null,"Error at : "+ Arrays.stream(error.get())
				                                                .map(StackTraceElement::toString)
				                                                .map(s -> "at -> "+s)
				                                                .collect(Collectors.joining("\n")));
	}

	@Override
	public String getInputMessageBox(String message) {
		return JOptionPane.showInputDialog(message);
	}

	

	
}
