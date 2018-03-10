package vgl.core.gfx.layer;

import java.util.ArrayList;
import java.util.List;

import vgl.core.annotation.VGLInternal;
import vgl.core.geom.Transform2D;
import vgl.core.gfx.Color;
import vgl.core.gfx.font.VFont;
import vgl.core.gfx.render.IRenderer2D;
import vgl.core.gfx.render.Renderer2D;
import vgl.core.gfx.renderable.ColoredSprite;
import vgl.core.gfx.renderable.PRenderable2D;
import vgl.core.gfx.renderable.Renderable2D;
import vgl.core.gfx.renderable.TextRenderable;
import vgl.core.gfx.shader.ShaderFactory;
import vgl.core.gfx.shader.ShaderProgram;
import vgl.core.internal.Checks;
import vgl.maths.Projection;
import vgl.maths.vector.Matrix4f;
import vgl.maths.vector.Vector2f;

@SuppressWarnings("deprecation")
abstract public class ILayer2D implements ILayer {

	// TODO rework shader framework, add ShaderValidator class
	// TODO add possibility for custom shaders in layers

	protected float					maxX, maxY;

	protected ShaderProgram			shader;

	protected IRenderer2D			layerRenderer;

	protected List<Renderable2D>	submitted;

	protected GFX2D					graphicsInstance;

	protected Color					layerBackground;

	public ILayer2D(IRenderer2D layerRenderer, float maxXProj, float maxYProj) {
		Checks.checkIfInitialized();
		this.maxX = maxXProj;
		this.maxY = maxYProj;
		this.layerRenderer = layerRenderer;
		this.graphicsInstance = new GFX2D(this);
		this.shader = ShaderFactory.default2DShader();
		this.submitted = new ArrayList<>();
		this.layerBackground = Color.TRANSPARENT;
		this.initializeTextureSamplers();
		this.uploadProjection(Projection.topLeftOrthographic(maxXProj, maxYProj));
		if (layerRenderer instanceof Renderer2D)
			((Renderer2D) layerRenderer).setScaling(maxXProj, maxYProj);

	}

	public ILayer2D(IRenderer2D layerRenderer, Vector2f bottomRight) {
		this(layerRenderer, bottomRight.x, bottomRight.y);
	}

	public ILayer2D(float bottomRightX, float bottomRightY) {
		this(new Renderer2D(), bottomRightX, bottomRightY);
	}

	public ILayer2D(Vector2f bottomRight) {
		this(new Renderer2D(), bottomRight.x, bottomRight.y);
	}

	private void uploadProjection(Matrix4f projection) {
		this.shader.uniformMat4f("projectionMatrix", projection);
	}

	private void initializeTextureSamplers() {
		shader.uniform1iv("textures", new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
		        20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31 });
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

	void submitText(String text, float x, float y, VFont font) {
		submitted.add(new TextRenderable(text, x, y, font));
	}

	void submitSprite(Renderable2D renderable, float x, float y, float width, float height, Transform2D transform) {
		submitted.add(new PRenderable2D(renderable, x, y, width, height, transform));
	}

	/**
	 * ENCAPSULATE
	 */
	@VGLInternal
	public void _renderInternal() {
		render(graphicsInstance);
		layerRenderer.begin();
		layerRenderer.renderSprite(new ColoredSprite(layerBackground, maxX, maxY), 0, 0);
		for (Renderable2D renderable2d : submitted) {
			if (renderable2d instanceof TextRenderable) {
				TextRenderable r = (TextRenderable) renderable2d;
				layerRenderer.drawText(r.getText(), r.getX(), r.getY(), r.getFont());
				continue;
			}
			PRenderable2D renderable = (PRenderable2D) renderable2d;
			layerRenderer.renderSprite(renderable.getRenderable(), renderable.x, renderable.y, renderable.width,
			        renderable.height, renderable.transform != null ? renderable.transform.toMatrix() : null);
		}
		layerRenderer.end();
		layerRenderer.render();
	}

	abstract public void render(GFX2D graphics);

	abstract public void update();

}
