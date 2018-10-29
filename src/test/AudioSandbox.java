package test;

import java.io.IOException;

import vgl.audio.Sound;
import vgl.core.audio.AudioSystem;
import vgl.core.exception.VGLException;
import vgl.core.input.Key;
import vgl.desktop.DesktopSpecific;
import vgl.main.VGL;

public class AudioSandbox {

	public static void main(String[] args) throws InterruptedException, IOException {

		VGLTestApp testApp = new VGLTestApp() {

			Sound	sound;

			boolean	played			= false;

			boolean	loadShortSound	= false;

			@Override
			public void update() throws VGLException {
				if (VGL.input.isKeyDown(Key.P)) {
					if (!played) {
						sound.play();
						played = true;
					}
				}

				if (VGL.input.isKeyDown(Key.L)) {
					if (!played) {
						sound.loop();
						played = true;
					}
				}

				if (VGL.input.isKeyDown(Key.DOWN)) {
					sound.setGain(sound.getGain() - 0.05f);
				}

				if (VGL.input.isKeyDown(Key.UP)) {
					sound.setGain(sound.getGain() + 0.05f);
				}
				
				if(VGL.input.isKeyDown(Key.SPACE)) {
					if(played) {
						sound.togglePause();
					}
				}
			}

			@Override
			public void render() throws VGLException {
			}

			@Override
			public void init() throws VGLException {
				VGL.logger.info("Perform init");
				AudioSystem.initialize();
				if (loadShortSound)
					sound = new Sound(DesktopSpecific.AudioDecoder.decodeAudio("resources/untitled.wav"));
				else
					sound = new Sound(DesktopSpecific.AudioDecoder.decodeOGG("resources/bet.ogg"));
			}

			@Override
			public void finish() throws VGLException {
				VGL.logger.info("Perform finish");

			}
		};

		testApp.startApplication();

	}

}
