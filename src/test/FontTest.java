package test;

import java.awt.Font;

import vgl.core.geom.Transform;
import vgl.desktop.gfx.font.VFont;
import vgl.maths.vector.Vector3f;

public class FontTest {

	public static void run() {
		Font f = new Font("Arial", Font.PLAIN, 50);
		new VFont(f);
	}

}
