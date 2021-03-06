package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import vgl.audio.AudioManager;
import vgl.audio.Sound;
import vgl.core.audio.AudioSystem;
import vgl.core.exception.VGLException;
import vgl.core.gfx.Color;
import vgl.core.gfx.Image;
import vgl.core.gfx.Image.Format;
import vgl.core.gfx.font.IFont;
import vgl.core.gfx.gl.Texture;
import vgl.core.gfx.layer.GFX2D;
import vgl.core.gfx.layer.ILayer2D;
import vgl.core.gfx.layer.LayeredLayout;
import vgl.core.gfx.renderable.Sprite;
import vgl.core.input.Key;
import vgl.desktop.DesktopSpecific;
import vgl.desktop.VGLApplication;
import vgl.main.VGL;
import vgl.tools.IResourceLoader;

public class LayerTesting {

	private static VGLApplication	app;

	private static LayeredLayout	layout;

	private static Texture			tex;

	private static IResourceLoader	resLoader;
	
	private static IFont font;

	public static void main(String[] args) {
		app = new App("Sad", 640, 480);
		app.setLayout(layout = new LayeredLayout());
		app.setUpdatesPerSecond(60);
		app.setFixedUpdateTimestamp(2.5f);
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

		private boolean first = true;

		@Override
		public void init() throws VGLException {
			resLoader = VGL.factory.createResourceLoader();
			System.out.println(GL11.glGetString(GL11.GL_RENDERER));
			AudioSystem.initialize();
			AudioManager.insert("sounds_2d", "music",
			        new Sound(DesktopSpecific.AudioDecoder.decodeOGG("resources/bet.ogg")));
			AudioManager.fetch("sounds_2d", "music").setGain(0.1f)
			                                        .setPitch(1f)
			                                        .play();
			resLoader.loadTexture(VGL.files.resource("resources/tex.png"), texture -> {
				tex = texture;
				System.out.println("tex loaded");
			});

			resLoader.loadImage(VGL.files.resource("resources/1.jpg"), image -> {
				System.out.println("Image loaded");
			});
			resLoader.loadImage(VGL.files.resource("resources/1.png"), image -> {
				System.out.println("Image loaded");
			});
			resLoader.loadImage(VGL.files.resource("resources/2.jpg"), image -> {
				System.out.println("Image loaded");
			});
			resLoader.loadImage(VGL.files.resource("resources/2.png"), image -> {
				System.out.println("Image loaded");
			});
			resLoader.loadImage(VGL.files.resource("resources/3.jpg"), image -> {
				System.out.println("Image loaded");
			});
			resLoader.loadImage(VGL.files.resource("resources/3.png"), image -> {
				System.out.println("Image loaded");
			});
			resLoader.loadImage(VGL.files.resource("resources/0.png"), image -> {
				System.out.println("Image loaded");
			});
			resLoader.loadFont(VGL.files.resource("resources/fonts/font_test.fnt"), font -> {
				LayerTesting.font = font;
			});
			resLoader.begin();
			System.out.println("After begin");
			// AudioManager.reconfigure("music", 0.1f, 1f);
			// JOptionPane.showMessageDialog(null, new
			// ImageIcon(imageToBufferedImage(loadImage("resources/0.png"))));
			// AudioManager.reconfigure("music", 0.1f, 1.5f).play();
			VGL.errorChannel.setErrorHandler(ech -> {
				ech.printStackTrace();
			});
			VGL.display.setClearColor(Color.WHITE);
			VGL.display.setDisplayFps(true);
			layout.pushLayer(new ILayer2D(16f, 9f) {
				float angle = 0f;
				long start = System.currentTimeMillis() + 5000;

				@Override
				public void update() {
					angle += 0.5f;
					if(VGL.input.isKeyDown(Key.S)) {
						getCamera().moveBy(0f, -1f);
					}
					if(VGL.input.isKeyDown(Key.W)) {
						getCamera().moveBy(0f, 1f);
					}
					if(VGL.input.isKeyDown(Key.A)) {
						getCamera().moveBy(1f, 0f);
					}
					if(VGL.input.isKeyDown(Key.D)) {
						getCamera().moveBy(-1f, 0f);
					}
					
				}

				@Override
				public void render(GFX2D graphics) {
					if (tex != null)
						graphics.drawSprite(new Sprite(tex), 2f, 2f, 3f, 3f);
					if(font != null) {
						graphics.setFont(font);
					}
					if(graphics.getFont() != null)
					graphics.drawTextCentered("This is on text", 5f, 10f);
					graphics.setColor(Color.CADET_BLUE);
					graphics.drawLine(0f, 0f, 3f, 3f, 0.01f);
					graphics.fillRect(5f, 3f, 5f, 5f);
					graphics.setColor(Color.LIGHT_GOLDEN_ROD_YELLOW);
					graphics.drawRectangle(5f, 8f, 5f, 5f);
					// graphics.renderSprite(new ImageSprite(tex), new Transform2D(new Vector2f(2,
					// 2)));
					// graphics.renderSprite(new ColoredSprite(Color.LAVENDER, 2f, 2f),
					// new Transform2D(new Vector2f(8, 4.5f), angle));
				}
			});

		}

		@Override
		public void render() throws VGLException {
			// System.out.println("draaw");
		}

		@Override
		public void update() throws VGLException {
			if (VGL.input.isKeyDown(Key.E))
				AudioManager.fetch("sounds_2d", "music").play();
		}

		@Override
		public void finish() throws VGLException {
			// TODO Auto-generated method stub

		}

	}

}
