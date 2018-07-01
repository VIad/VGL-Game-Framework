package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import vgl.audio.AudioManager;
import vgl.audio.AudioSystem;
import vgl.audio.Sound;
import vgl.core.exception.VGLException;
import vgl.core.gfx.Color;
import vgl.core.gfx.Image;
import vgl.core.gfx.Image.Format;
import vgl.core.gfx.gl.Texture;
import vgl.core.gfx.renderable.ImageSprite;
import vgl.core.internal.ProcessManager;
import vgl.desktop.DesktopSpecific;
import vgl.desktop.VGLApplication;
import vgl.desktop.gfx.layer.GFX2D;
import vgl.desktop.gfx.layer.ILayer2D;
import vgl.desktop.gfx.layer.LayeredLayout;
import vgl.desktop.input.Key;
import vgl.desktop.tools.DesktopResourceLoader;
import vgl.desktop.utils.BasicUtils;
import vgl.main.VGL;
import vgl.tools.IResourceLoader;
import vgl.tools.functional.callback.Callback;

public class LayerTesting {

	private static VGLApplication	app;

	private static LayeredLayout	layout;

	private static Texture			tex;

	private static IResourceLoader	resLoader;

	public static void main(String[] args) {
		app = new App("Sad", 640, 480);
		app.setLayout(layout = new LayeredLayout());
		app.setUpdatesPerSecond(60);
		app.startApplication();
	}

	private static Image loadImage(String file) {
		Image result = null;
		try {
			BufferedImage buffered = ImageIO.read(new File(file));
			result = new Image(buffered.getWidth(), buffered.getHeight(), Format.ARGB);
			for (int y = 0; y < buffered.getHeight(); y++) {
				for (int x = 0; x < buffered.getWidth(); x++) {
					result.setPixel(x, y, Color.fromARGB(buffered.getRGB(x, y)));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	//
	private static BufferedImage imageToBufferedImage(Image image) {
		System.out.println(image);
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < result.getWidth(); x++) {
			for (int y = 0; y < result.getHeight(); y++) {
				result.setRGB(x, y, image.getPixel(x, y).getARGB());
			}
		}
		return result;
	}

	static class App extends VGLApplication {

		public App(String title, int window_width, int window_height) {
			super(title, window_width, window_height);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void init() throws VGLException {
			AudioSystem.initialize();
			AudioManager.create();
			Sound s = new Sound(DesktopSpecific.AudioDecoder.decodeAudio("resources/untitled.wav"));
			s.play();
			BasicUtils.delayThread(300);
			s.play();
			// AudioManager.reconfigure("music", 0.1f, 1f);
			// JOptionPane.showMessageDialog(null, new
			// ImageIcon(imageToBufferedImage(loadImage("resources/0.png"))));
			// AudioManager.reconfigure("music", 0.1f, 1.5f).play();
			VGL.errorChannel.setErrorHandler(ech -> {
				ech.printStackTrace();
			});
			VGL.display.setClearColor(Color.WHITE);
			resLoader = new DesktopResourceLoader();

			resLoader.loadTexture(VGL.io.file("resources/tex.png"), texture -> {
				tex = texture;
			});

			resLoader.loadImage(VGL.io.file("resources/1.jpg"), image -> {
				System.out.println("Image loaded");
			});
			resLoader.loadImage(VGL.io.file("resources/1.png"), image -> {
				System.out.println("Image loaded");
			});
			resLoader.begin();
			System.out.println(resLoader.getReadyPercentage());
			VGL.display.setDisplayFps(true);
			layout.pushLayer(new ILayer2D(16f, 9f) {
				float angle = 0f;

				@Override
				public void update() {
					angle += 0.5f;
				}

				@Override
				public void render(GFX2D graphics) {
					if (tex != null)
						graphics.renderSprite(new ImageSprite(tex), 2f, 2f, 3f, 3f);
					// graphics.renderSprite(new ImageSprite(tex), new Transform2D(new Vector2f(2,
					// 2)));
					// graphics.renderSprite(new ColoredSprite(Color.LAVENDER, 2f, 2f),
					// new Transform2D(new Vector2f(8, 4.5f), angle));
				}
			});

		}

		@Override
		public void render() throws VGLException {

		}

		@Override
		public void update() throws VGLException {
			if (VGL.input.isKeyDown(Key.C)) {
			}
		}

		@Override
		public void finish() throws VGLException {
			// TODO Auto-generated method stub

		}

	}

}
