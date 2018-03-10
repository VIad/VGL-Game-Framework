package test;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import vgl.audio.AudioSystem;
import vgl.audio.Sound;
import vgl.audio.manager.AudioManager;
import vgl.core.exception.VGLException;
import vgl.core.gfx.Color;
import vgl.core.gfx.camera.PerspectiveCamera;
import vgl.core.gfx.font.VFont;
import vgl.core.gfx.render.Renderer2D;
import vgl.core.gfx.renderable.ColoredSprite;
import vgl.core.gfx.renderable.ImageSprite;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.core.gfx.shader.ShaderFactory;
import vgl.core.gfx.shader.ShaderProgram;
import vgl.core.gfx.texture.Texture;
import vgl.core.main.VGLApplication;
import vgl.core.main.Window;
import vgl.core.main.input.Key;
import vgl.core.main.input.Keyboard;
import vgl.core.main.input.Mouse;
import vgl.maths.Projection;
import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector3f;
import vgl.maths.vector.VectorMaths;
import vgl.tools.async.Async;
import vgl.utils.LogLevel;
import vgl.utils.Logger;

public class Test extends VGLApplication {

	private static ShaderProgram			defaultShader;
	private static Renderer2D				bRenderer;
	private static ArrayList<Renderable2D>	sprites;

	public Test(String title, int window_width, int window_height) {
		super(title, window_width, window_height);
	}

	private static class CameraImpl extends PerspectiveCamera {

		public CameraImpl(final float pitch, final float yaw, final float roll) {
			super(pitch, yaw, roll);
		}

		public CameraImpl(final Vector3f vec) {
			super(vec);
		}

		@Override
		public void update() {
			if (Keyboard.isInitialized()) {
				if (Keyboard.isKeyDown(Key.W))
					getPosition().y -= 0.003;
				if (Keyboard.isKeyDown(Key.S))
					getPosition().z += 0.003;
				if (Keyboard.isKeyDown(Key.Q))
					setRotY(getRotY() - 0.04f);
				if (Keyboard.isKeyDown(Key.E))
					setRotY(getRotY() + 0.04f);
				if (Keyboard.isKeyDown(Key.A))
					getPosition().x -= 0.003;
				if (Keyboard.isKeyDown(Key.D))
					getPosition().x += 0.003;
			}
		}
	}

	static Matrix4f				projMat;
	static Matrix4f				transMat;

	public static final float[]	CUBE_VERTEX_ARRAY	= { -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f,
	        0.5f, 0.5f, -0.5f,

	        -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,

	        0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,

	        -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,

	        -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,

	        -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f };

	public static int[]			indices				= { 0, 1, 3, 3, 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11, 11, 9, 10, 12, 13,
	        15, 15, 13, 14, 16, 17, 19, 19, 17, 18, 20, 21, 23, 23, 21, 22 };
	private static VFont		font;

	public static void main(final String[] args) throws Exception {
		engineTest();
	}

	static Texture	tex;
	static Texture	tex2;
	static Texture	tex3;

	private static void engineTest() {
		VGLApplication app = new Test("Test", 1280, 720);
		app.setVerticalSynchronized(false);
		app.setResizable(true);
		app.setUpdatesPerSecond(60);
		app.startApplication();

	}

	private static float	transY		= 0f, transX = 0f;

	private static float	angleRotY	= 0;

	private static class DefaultShader extends ShaderProgram {

		public DefaultShader(final String vertexFilePath, final String fragmentFilePath) {
			super(vertexFilePath, fragmentFilePath);
		}

		@Override
		public void bindVertexShaderInputAttribs() {
			super.bindAttribute(0, "positions");
			super.bindAttribute(1, "color");
			super.bindAttribute(2, "uvs");
			super.bindAttribute(3, "texId");
		}

		@Override
		public void getAllUniformLocations() {
		}
	}

	static ArrayList<Renderable2D> list = new ArrayList<>();

	@Override
	public void init() throws VGLException {
		AudioSystem.initialize(100);
		AudioManager.create();
		AudioManager.add("music", new Sound("resources/test_track.ogg"));
		AudioManager.reconfigure("music", 1f, 1f).play();

		for (int i = 0; i < 10000; i++)
			list.add(new ColoredSprite(
			        5,
			        5,
			        new Color(rand.nextInt(0xffffff)),
			        new Color(rand.nextInt(0xffffff)),
			        new Color(rand.nextInt(0xffffff)),
			        new Color(rand.nextInt(0xffffff))));
		tex = new Texture("src/1.png");
		tex2 = new Texture("src/0.png");
		tex3 = new Texture("src/1.jpg");

		Window.setClearColor(Color.BLACK);

		defaultShader = ShaderFactory.default2DShader();
		defaultShader.start();
		defaultShader.uniform1iv("textures", new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
		        18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 });
		// projMat = Matrix4f.orthographic(0, 16, 9, 0, -1, 1);
		// projMat = Maths.topLeftOrthographic(16, 9);
		projMat = Projection.topLeftOrthographic(16, 9);
		defaultShader.uniformMat4f("projectionMatrix", projMat);
		defaultShader.stop();
		// final VertexArray m = new VertexArray();
		// m.setVertexBuffer(new float[] {
		// 0, 3, 0, 0, 0, 0, 8, 0, 0, 8, 3, 0,
		// }, 0, 3);
		final Random rand = new Random();
		// m.addBuffer(new float[] {
		// Math.abs(rand.nextFloat()), Math.abs(rand.nextFloat()),
		// Math.abs(rand.nextFloat()),
		// Math.abs(rand.nextFloat()), Math.abs(rand.nextFloat()),
		// Math.abs(rand.nextFloat()),
		// Math.abs(rand.nextFloat()), Math.abs(rand.nextFloat()),
		// Math.abs(rand.nextFloat()),
		// Math.abs(rand.nextFloat()), Math.abs(rand.nextFloat()),
		// Math.abs(rand.nextFloat()),
		// }, 1, 3);
		// m.addIndicesBuffer(new int[] {
		// 0, 1, 2, 0, 2, 3
		// });
		// m.addVertexBuffer(CUBE_VERTEX_ARRAY, indices, 0, 3);
		// float[] cols = new float[CUBE_VERTEX_ARRAY.length * 3];
		// for (int i = 0; i < cols.length; i++) {
		// cols[i] = Math.abs(rand.nextFloat());
		// }
		// m.addBuffer(cols, 1, 3);
		// CameraImpl cam = new CameraImpl(new Vector3f(0, 0, 3f));
		sprites = new ArrayList<>();
		// for (float x = 0f; x < 16f; x += 0.5f) {
		// for (float y = 0f; y < 9f; y += 0.5f) {
		// sprites.add(new Sprite(
		// new Vector2f(x, y),
		// new Vector2f(0.4f, 0.4f),
		// new Color(rand.nextInt(60), rand.nextInt(10), rand.nextInt(256)),
		// rand.nextBoolean() ? tex : rand.nextBoolean() ? tex2 : tex3));
		// }
		// }

		// for (int i = 0; i < 200; i++) {
		// for (int j = 0; j < 100; j++) {
		// sprites.add(new Sprite(new Vector2f(i * 10, j * 10), new Vector2f(10, 6), new
		// Color(rand.nextInt(0xffffff)), rand.nextBoolean() ? tex : rand.nextBoolean()
		// ? tex2 : tex3));
		// }
		// }

		// sprites.add(new Sprite(new Vector2f(1,1),new Vector2f(4,4), new
		// Color(rand.nextInt(0xffffff)),tex));
		// sprites.add(new Sprite(new Vector2f(6,1),new Vector2f(4,4), new
		// Color(rand.nextInt(0xffffff)),tex2));
		// sprites.add(new Sprite(new Vector2f(11,1),new Vector2f(4,4), new
		// Color(rand.nextInt(0xffffff)),tex3));
		font = new VFont(new Font("Times New Roman", Font.PLAIN, 100));

		bRenderer = new Renderer2D();
		sprites.add(new ColoredSprite(Color.LAVENDER, 5, 5));
		sprites.add(new ColoredSprite(5, 5, new Color(rand.nextInt(0xffffff)), new Color(rand.nextInt(0xffffff))));
		sprites.add(new ColoredSprite(
		        5,
		        5,
		        new Color(rand.nextInt(0xffffff)),
		        new Color(rand.nextInt(0xffffff)),
		        new Color(rand.nextInt(0xffffff)),
		        new Color(rand.nextInt(0xffffff))));
		System.out.println("Rendering >> " + sprites.size() + " sprites !");

		Window.updateOnResize(true);
		Window.logFps(true);
		Keyboard.create();
		Mouse.create();
		defaultShader.start();
		randStr = "((";
		for (int i = 0; i < 200; i++) {
			randStr += (char) (rand.nextInt(95) + 32);
		}
	}

	boolean			f		= true;

	static Random	rand	= new Random();

	static String	randStr;

	static float	theta	= 0f;

	@Override
	public void render() throws VGLException {
		if (System.currentTimeMillis() - time > 2000) {
			time = System.currentTimeMillis() + 50000000;
			// Async.invoke(5.5f, list, "clear");
			// Async.invokeStatic(5.5f, Test.class, "exit");
		}
		bRenderer.begin();
		bRenderer.renderSprite(new ImageSprite(tex2), 5, 5, 5, 5,
		        VectorMaths.translationMatrix(new Vector3f(-10f, 0f, 0f)).multiply(
		                VectorMaths.rotationMatrix(0, 0, theta += 0.1f)));
		for (int i = 0; i < list.size(); i++) {
			bRenderer.renderSprite(list.get(i), i * 7, 5);
		}
		//
		// for (int i = 0; i < sprites.size(); i++) {
		// if (i > 1)
		// bRenderer.renderSprite(sprites.get(i), i * 6, 16, null);
		// else
		// bRenderer.renderSprite(sprites.get(i), i * 6, 10, null);
		// }
		// bRenderer.drawText(randStr, 0, 10, font);
		// bRenderer.drawText("0", 0, 0, font);
		// bRenderer.drawText("Enjoy your stay", 0, 7, font);
		// bRenderer.drawText("^\\gggggg", 0, 3, font);
		bRenderer.end();
		bRenderer.render();
		// for (Sprite sprite : sprites) {
		// renderer.submit(sprite);
		// }
		// renderer.render();
		// renderer.end(); // TODO Auto-generated method stub

	}

	long time = System.currentTimeMillis();

	@Override
	public void update() throws VGLException {
		if (Keyboard.isKeyDown(Key.W)) {
			transY += 0.05f;
		}
		if (Keyboard.isKeyDown(Key.S)) {
			transY -= 0.05f;
		}
		if (Keyboard.isKeyDown(Key.A)) {
			transX += 0.05f;
		}
		if (Keyboard.isKeyDown(Key.D)) {
			transX -= 0.05f;
		}
		if (Keyboard.isKeyDown(Key.Q)) {
			angleRotY += 0.05f;
		}
		if (Keyboard.isKeyDown(Key.E)) {
			angleRotY -= 0.05f;
		}
		// transX -= 0.05f;
		// transMat = VectorMaths.translationMatrix(new Vector3f(transX, transY,
		// 0)).multiply(Matrix4f.rotation(angleRotY, Vector3f.TO_NEGATIVE_Z));
		// transMat = VectorMaths.translationMatrix(new Vector3f(transX, transY,
		// 0f)).multiply(
		// VectorMaths.rotationMatrix(0, 0, angleRotY)).multiply(
		transMat = VectorMaths.translationMatrix(new Vector3f(transX, transY, 0f));

		defaultShader.uniformMat4f("transformationMatrix", transMat);
		defaultShader.uniformVec2f("lightPos",
		        new Vector2f(((Mouse.getX() * 16) / Window.getWidth()), ((Mouse.getY() * 9) / Window.getHeight())));

	}

	@Override
	public void finish() throws VGLException {
		System.out.println("Cleanup");
		Texture.cleanTextureCache();
		AudioManager.destroy();
	}

	@Override
	public void fixedUpdate() throws VGLException {

	}

}