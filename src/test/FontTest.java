package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import vgl.desktop.gfx.font.VFont;

public class FontTest {

	public static void run() {
		Font f = new Font("Arial", Font.PLAIN, 50);
		new VFont(f);
	}

}
