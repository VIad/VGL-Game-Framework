package test;

import java.io.IOException;

import vgl.desktop.audio.AudioManager;
import vgl.desktop.audio.AudioSystem;
import vgl.desktop.audio.Sound;
import vgl.desktop.utils.DesktopLogger;
import vgl.main.VGL;

public class AudioSandbox {

	// TODO WORK ON
	public static void main(String[] args) throws InterruptedException, IOException {
		AudioSystem.initialize(5);
		AudioManager.create();
		AudioManager.add("music", new Sound("resources/bet.ogg"));
		AudioManager.reconfigure("music", 0.5f, 1f).play();
		float add = 0.1f;
		boolean paused = false;
		while (true) {
			char read = (char) System.in.read();
			if (read == ' ') {
				paused = !paused;
				if (paused)
					AudioManager.pause("music");
				else
					AudioManager.resume("music");
			}
			if (read == 'w') {
				AudioManager.reconfigure("music", 0f, 1f);
				if (paused)
					VGL.logger.info(add);
				else
					VGL.logger.error(add);
			}
		}
		// AudioSystem.destroy();
	}

}
