package test;

import java.io.IOException;

import vgl.audio.Sound;
import vgl.core.audio.AudioManager;
import vgl.core.audio.AudioSystem;
import vgl.core.internal.gpu_device_l;

public class AudioSandbox {

	// TODO WORK ON
	public static void main(String[] args) throws InterruptedException, IOException {
		AudioSystem.initialize(5);
		AudioManager.create();
//		AudioManager.add("music", new Sound(AudioSystem.));
//		AudioManager.add("music2", new Sound("resources/bet.ogg"));
		AudioManager.reconfigure("music", 0.5f, 1f).play();
		float add = 1f;
		boolean paused = false;
		while (true) {
			char read = (char) System.in.read();

			if (read == 'w') {
				AudioManager.reconfigure("musicc", 0.5f,add+=0.1f);

			}
		}
		// AudioSystem.destroy();
	}

}
