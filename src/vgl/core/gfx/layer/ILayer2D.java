package vgl.core.gfx.layer;

import java.util.ArrayList;
import java.util.List;

import vgl.core.annotation.VGLInternal;
import vgl.core.geom.Size2f;
import vgl.core.gfx.Color;
import vgl.core.gfx.camera.OrthographicCamera;
import vgl.core.gfx.render.IRenderer2D;
import vgl.core.gfx.render.RenderContext;
import vgl.core.gfx.renderable.ColoredSprite;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.core.gfx.shader.ShaderFactory;
import vgl.core.gfx.shader.ShaderProgram;
import vgl.core.internal.Checks;
import vgl.core.internal.GlobalDetails;
import vgl.main.VGL;
import vgl.maths.Projection;
import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;
import vgl.platform.Platform;

@SuppressWarnings("deprecation")
abstract public class ILayer2D implements ILayer {

	// TODO rework shader framework, add ShaderValidator class
	// TODO add possibility for custom shaders in layers

	private float				maxX, maxY;

	ShaderProgram				shader;

	IRenderer2D					layerRenderer;

	RenderContext				renderContext;

	private OrthographicCamera	layerCam;

	private List<Renderable2D>	drawCommand;

	private GFX2D				graphicsInstance;

	private Color				layerBackground;

	public ILayer2D(IRenderer2D layerRenderer, float maxXProj, float maxYProj) {
		Checks.checkIfInitialized();
		this.maxX = maxXProj;
		this.maxY = maxYProj;
		this.layerRenderer = layerRenderer;
		this.graphicsInstance = new GFX2D(this);
		this.shader = ShaderFactory.batch2DGLSL();
		this.drawCommand = new ArrayList<>();
		this.layerBackground = Color.BLACK;
		this.layerCam = new OrthographicCamera(new Vector2f(), new Size2f(maxXProj, maxYProj));
		shader.start();
		this.layerCam.uploadToShader("transformationMatrix", shader);
		this.initializeTextureSamplers();
		this.uploadProjection(Projection.topLeftOrthographic(maxXProj, maxYProj));
		shader.stop();
		layerRenderer.setScaling(maxXProj, maxYProj);
		this.renderContext = RenderContext.create(maxXProj, maxYProj);
	}

	public ILayer2D(IRenderer2D layerRenderer, Vector2f bottomRight) {
		this(layerRenderer, bottomRight.x, bottomRight.y);
	}

	public ILayer2D(float bottomRightX, float bottomRightY) {
		this(VGL.factory.newPlatformOptimalRenderer2D(1000)
				        .usingOverflowPolicy(IRenderer2D.OverflowPolicy.DO_RENDER),
				        bottomRightX, bottomRightY);
	}

	public ILayer2D(Vector2f bottomRight) {
		this(VGL.factory.newPlatformOptimalRenderer2D(1000)
				        .usingOverflowPolicy(IRenderer2D.OverflowPolicy.DO_RENDER),
				        bottomRight.x, bottomRight.y);
	}

	private void uploadProjection(Matrix4f projection) {
		this.shader.uniformMat4f("projectionMatrix", projection);
	}

	private void initializeTextureSamplers() {
		if (GlobalDetails.getPlatform() != Platform.WEB)
			shader.uniform1iv("textures", new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
			        19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 });
	}

	public OrthographicCamera getCamera() {
		return layerCam;
	}

	public void setLayerBackground(Color background) {
		this.layerBackground = background;
	}

	public float getProjectionBottomRightX() {
		return maxX;
	}

	public float getProjectionBottomRightY() {
		return maxY;
	}

	public Color getLayerBackground() {
		return new Color(layerBackground);
	}

	// void submitText(String text, float x, float y, IFont font) {
	// drawCommand.add(new TextRenderable(text, x, y, font));
	// }
	//
	// void submitSprite(Renderable2D renderable, float x, float y, float width,
	// float height, Transform2D transform) {
	// drawCommand.add(new PRenderable2D(renderable, x, y, width, height,
	// transform));
	// }

	/**
	 * ENCAPSULATE
	 */
	@VGLInternal
	public void _renderInternal() {
		shader.start();
		layerCam.uploadToShader("transformationMatrix", shader);
		layerRenderer.begin();
		if (!layerBackground.equals(Color.TRANSPARENT))
			layerRenderer.draw(new ColoredSprite(layerBackground, maxX, maxY), 0, 0);
		render(graphicsInstance);
		layerRenderer.end();
		layerRenderer.render();
		shader.stop();
	}

	abstract public void render(GFX2D graphics);

	abstract public void update();

}
