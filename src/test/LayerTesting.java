package test;

import vgl.core.exception.VGLException;
import vgl.core.geom.Transform2D;
import vgl.core.gfx.Color;
import vgl.core.gfx.layer.LayeredLayout;
import vgl.core.gfx.renderable.ColoredSprite;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.desktop.VGLApplication;
import vgl.desktop.Window;
import vgl.desktop.gfx.layer.GFX2D;
import vgl.desktop.gfx.layer.ILayer2D;
import vgl.maths.vector.Vector2f;

public class LayerTesting {

	private static VGLApplication	app;

	private static LayeredLayout	layout;

	public static void main(String[] args) {
		app = new App("Sad", 640, 480);
		app.setLayout(layout = new LayeredLayout());
		app.setUpdatesPerSecond(60);
		app.startApplication();

	}

	static class App extends VGLApplication {

		public App(String title, int window_width, int window_height) {
			super(title, window_width, window_height);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void init() throws VGLException {
			// AudioSystem.initialize();
			// AudioManager.create();
			// AudioManager.add("music", new Sound("resources/test_track.ogg")).play();
			Window.setClearColor(Color.CYAN);

			Window.logFps(true);
			layout.pushLayer(new ILayer2D(16f, 9f) {
				float angle = 0f;

				@Override
				public void update() {
					angle += 0.5f;
				}

				@Override
				public void render(GFX2D graphics) {
					graphics.renderSprite(new ColoredSprite(Color.BEIGE, 5f, 3f), new Transform2D(new Vector2f(2, 2)));
					graphics.renderSprite(new ColoredSprite(Color.LAVENDER, 2f, 2f),
					        new Transform2D(new Vector2f(8, 4.5f), angle));
				}
			});
			
		}

		@Override
		public void render() throws VGLException {
			// TODO Auto-generated method stub

		}

		@Override
		public void update() throws VGLException {
			// TODO Auto-generated method stub

		}

		@Override
		public void finish() throws VGLException {
			// TODO Auto-generated method stub

		}

	}

}
