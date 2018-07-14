package vgl.maths.geom.shape2d;

public class SquareFloat extends RectFloat {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7568422327821175645L;

	public SquareFloat(float x, float y, float side) {
		super(x, y, side, side);
	}

	public SquareFloat(float x, float y) {
		super(x, y, 0, 0);
	}

}
