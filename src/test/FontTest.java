package test;

import vgl.audio.AudioManager;
import vgl.audio.Sound;
import vgl.core.audio.AudioSystem;
import vgl.core.exception.VGLException;
import vgl.core.exception.VGLRuntimeException;
import vgl.core.gfx.font.BMFont;
import vgl.core.gfx.layer.GFX2D;
import vgl.core.gfx.layer.ILayer2D;
import vgl.core.gfx.layer.LayeredLayout;
import vgl.core.internal.Checks;
import vgl.desktop.DesktopSpecific;
import vgl.desktop.VGLApplication;
import vgl.desktop.input.Key;
import vgl.main.VGL;
import vgl.tools.managers.Managers;

public class FontTest {

	private static BMFont			font;

	private static long				start	= System.currentTimeMillis();

	private static LayeredLayout	layeredLayout;

	static class App extends VGLApplication {

		public App(String title, int window_width, int window_height) {
			super(title, window_width, window_height);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void update() throws VGLException {
			if(VGL.input.isKeyDown(Key.F10)) {
				AudioManager.fetch("thats").play();
			}
		}

		@Override
		public void render() throws VGLException {
			// TODO Auto-generated method stub

		}

		@Override
		public void init() throws VGLException {
			BMFont.load(VGL.io.file("resources/fonts/test_1.fnt"), (font, error) -> {
				FontTest.font = font;
			});
			AudioSystem.initialize();
			AudioManager.insert("thatSnd",
			        new Sound(DesktopSpecific.AudioDecoder.decodeOGG("resources/test_track.ogg")));
			setLayout(layeredLayout = new LayeredLayout());
			layeredLayout.pushLayer(new ILayer2D(16f, 9f) {

				@Override
				public void update() {
					// TODO Auto-generated method stub

				}

				@Override
				public void render(GFX2D graphics) {
					// graphics.renderSprite(new ColoredSprite(Color.BLANCHED_ALMOND, 3f, 2f), new
					// Vector2f(5f, 2f));
				}
			});
		}

		@Override
		public void finish() throws VGLException {
			// TODO Auto-generated method stub

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
