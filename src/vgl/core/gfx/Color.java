package vgl.core.gfx;

import vgl.core.buffers.MemoryBufferFloat;
import vgl.maths.vector.Vector4f;

public final class Color implements java.io.Serializable, Comparable<Color> {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -5968556428516551716L;
	/**
	*<font color="F0F8FF"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="F0F8FF"><strong> 1234567890 </strong> </font>
	*/
	public static final Color ALICE_BLUE = new Color(0xF0F8FF);
	/**
	*<font color="FAEBD7"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FAEBD7"><strong> 1234567890 </strong> </font>
	*/
	public static final Color ANTIQUE_WHITE = new Color(0xFAEBD7);
	/**
	*<font color="00FFFF"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="00FFFF"><strong> 1234567890 </strong> </font>
	*/
	public static final Color AQUA = new Color(0x00FFFF);
	/**
	*<font color="7FFFD4"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="7FFFD4"><strong> 1234567890 </strong> </font>
	*/
	public static final Color AQUAMARINE = new Color(0x7FFFD4);
	/**
	*<font color="F0FFFF"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="F0FFFF"><strong> 1234567890 </strong> </font>
	*/
	public static final Color AZURE = new Color(0xF0FFFF);
	/**
	*<font color="F5F5DC"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="F5F5DC"><strong> 1234567890 </strong> </font>
	*/
	public static final Color BEIGE = new Color(0xF5F5DC);
	/**
	*<font color="FFE4C4"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFE4C4"><strong> 1234567890 </strong> </font>
	*/
	public static final Color BISQUE = new Color(0xFFE4C4);
	/**
	*<font color="000000"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="000000"><strong> 1234567890 </strong> </font>
	*/
	public static final Color BLACK = new Color(0x000000);
	/**
	*<font color="FFEBCD"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFEBCD"><strong> 1234567890 </strong> </font>
	*/
	public static final Color BLANCHED_ALMOND = new Color(0xFFEBCD);
	/**
	*<font color="0000FF"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="0000FF"><strong> 1234567890 </strong> </font>
	*/
	public static final Color BLUE = new Color(0x0000FF);
	/**
	*<font color="8A2BE2"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="8A2BE2"><strong> 1234567890 </strong> </font>
	*/
	public static final Color BLUE_VIOLET = new Color(0x8A2BE2);
	/**
	*<font color="A52A2A"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="A52A2A"><strong> 1234567890 </strong> </font>
	*/
	public static final Color BROWN = new Color(0xA52A2A);
	/**
	*<font color="DEB887"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="DEB887"><strong> 1234567890 </strong> </font>
	*/
	public static final Color BURLY_WOOD = new Color(0xDEB887);
	/**
	*<font color="5F9EA0"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="5F9EA0"><strong> 1234567890 </strong> </font>
	*/
	public static final Color CADET_BLUE = new Color(0x5F9EA0);
	/**
	*<font color="7FFF00"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="7FFF00"><strong> 1234567890 </strong> </font>
	*/
	public static final Color CHARTREUSE = new Color(0x7FFF00);
	/**
	*<font color="D2691E"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="D2691E"><strong> 1234567890 </strong> </font>
	*/
	public static final Color CHOCOLATE = new Color(0xD2691E);
	/**
	*<font color="FF7F50"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FF7F50"><strong> 1234567890 </strong> </font>
	*/
	public static final Color CORAL = new Color(0xFF7F50);
	/**
	*<font color="6495ED"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="6495ED"><strong> 1234567890 </strong> </font>
	*/
	public static final Color CORNFLOWER_BLUE = new Color(0x6495ED);
	/**
	*<font color="FFF8DC"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFF8DC"><strong> 1234567890 </strong> </font>
	*/
	public static final Color CORNSILK = new Color(0xFFF8DC);
	/**
	*<font color="DC143C"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="DC143C"><strong> 1234567890 </strong> </font>
	*/
	public static final Color CRIMSON = new Color(0xDC143C);
	/**
	*<font color="00FFFF"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="00FFFF"><strong> 1234567890 </strong> </font>
	*/
	public static final Color CYAN = new Color(0x00FFFF);
	/**
	*<font color="00008B"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="00008B"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_BLUE = new Color(0x00008B);
	/**
	*<font color="008B8B"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="008B8B"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_CYAN = new Color(0x008B8B);
	/**
	*<font color="B8860B"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="B8860B"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_GOLDEN_ROD = new Color(0xB8860B);
	/**
	*<font color="A9A9A9"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="A9A9A9"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_GRAY = new Color(0xA9A9A9);
	/**
	*<font color="A9A9A9"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="A9A9A9"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_GREY = new Color(0xA9A9A9);
	/**
	*<font color="006400"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="006400"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_GREEN = new Color(0x006400);
	/**
	*<font color="BDB76B"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="BDB76B"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_KHAKI = new Color(0xBDB76B);
	/**
	*<font color="8B008B"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="8B008B"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_MAGENTA = new Color(0x8B008B);
	/**
	*<font color="556B2F"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="556B2F"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_OLIVE_GREEN = new Color(0x556B2F);
	/**
	*<font color="FF8C00"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FF8C00"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_ORANGE = new Color(0xFF8C00);
	/**
	*<font color="9932CC"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="9932CC"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_ORCHID = new Color(0x9932CC);
	/**
	*<font color="8B0000"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="8B0000"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_RED = new Color(0x8B0000);
	/**
	*<font color="E9967A"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="E9967A"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_SALMON = new Color(0xE9967A);
	/**
	*<font color="8FBC8F"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="8FBC8F"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_SEA_GREEN = new Color(0x8FBC8F);
	/**
	*<font color="483D8B"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="483D8B"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_SLATE_BLUE = new Color(0x483D8B);
	/**
	*<font color="2F4F4F"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="2F4F4F"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_SLATE_GRAY = new Color(0x2F4F4F);
	/**
	*<font color="2F4F4F"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="2F4F4F"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_SLATE_GREY = new Color(0x2F4F4F);
	/**
	*<font color="00CED1"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="00CED1"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_TURQUOISE = new Color(0x00CED1);
	/**
	*<font color="9400D3"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="9400D3"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DARK_VIOLET = new Color(0x9400D3);
	/**
	*<font color="FF1493"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FF1493"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DEEP_PINK = new Color(0xFF1493);
	/**
	*<font color="00BFFF"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="00BFFF"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DEEP_SKY_BLUE = new Color(0x00BFFF);
	/**
	*<font color="696969"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="696969"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DIM_GRAY = new Color(0x696969);
	/**
	*<font color="696969"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="696969"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DIM_GREY = new Color(0x696969);
	/**
	*<font color="1E90FF"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="1E90FF"><strong> 1234567890 </strong> </font>
	*/
	public static final Color DODGER_BLUE = new Color(0x1E90FF);
	/**
	*<font color="B22222"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="B22222"><strong> 1234567890 </strong> </font>
	*/
	public static final Color FIRE_BRICK = new Color(0xB22222);
	/**
	*<font color="FFFAF0"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFFAF0"><strong> 1234567890 </strong> </font>
	*/
	public static final Color FLORAL_WHITE = new Color(0xFFFAF0);
	/**
	*<font color="228B22"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="228B22"><strong> 1234567890 </strong> </font>
	*/
	public static final Color FOREST_GREEN = new Color(0x228B22);
	/**
	*<font color="FF00FF"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FF00FF"><strong> 1234567890 </strong> </font>
	*/
	public static final Color FUCHSIA = new Color(0xFF00FF);
	/**
	*<font color="DCDCDC"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="DCDCDC"><strong> 1234567890 </strong> </font>
	*/
	public static final Color GAINSBORO = new Color(0xDCDCDC);
	/**
	*<font color="F8F8FF"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="F8F8FF"><strong> 1234567890 </strong> </font>
	*/
	public static final Color GHOST_WHITE = new Color(0xF8F8FF);
	/**
	*<font color="FFD700"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFD700"><strong> 1234567890 </strong> </font>
	*/
	public static final Color GOLD = new Color(0xFFD700);
	/**
	*<font color="DAA520"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="DAA520"><strong> 1234567890 </strong> </font>
	*/
	public static final Color GOLDEN_ROD = new Color(0xDAA520);
	/**
	*<font color="808080"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="808080"><strong> 1234567890 </strong> </font>
	*/
	public static final Color GRAY = new Color(0x808080);
	/**
	*<font color="808080"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="808080"><strong> 1234567890 </strong> </font>
	*/
	public static final Color GREY = new Color(0x808080);
	/**
	*<font color="008000"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="008000"><strong> 1234567890 </strong> </font>
	*/
	public static final Color GREEN = new Color(0x008000);
	/**
	*<font color="ADFF2F"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="ADFF2F"><strong> 1234567890 </strong> </font>
	*/
	public static final Color GREEN_YELLOW = new Color(0xADFF2F);
	/**
	*<font color="F0FFF0"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="F0FFF0"><strong> 1234567890 </strong> </font>
	*/
	public static final Color HONEY_DEW = new Color(0xF0FFF0);
	/**
	*<font color="FF69B4"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FF69B4"><strong> 1234567890 </strong> </font>
	*/
	public static final Color HOT_PINK = new Color(0xFF69B4);
	/**
	*<font color="CD5C5C"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="CD5C5C"><strong> 1234567890 </strong> </font>
	*/
	public static final Color INDIAN_RED = new Color(0xCD5C5C);
	/**
	*<font color="4B0082"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="4B0082"><strong> 1234567890 </strong> </font>
	*/
	public static final Color INDIGO = new Color(0x4B0082);
	/**
	*<font color="FFFFF0"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFFFF0"><strong> 1234567890 </strong> </font>
	*/
	public static final Color IVORY = new Color(0xFFFFF0);
	/**
	*<font color="F0E68C"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="F0E68C"><strong> 1234567890 </strong> </font>
	*/
	public static final Color KHAKI = new Color(0xF0E68C);
	/**
	*<font color="E6E6FA"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="E6E6FA"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LAVENDER = new Color(0xE6E6FA);
	/**
	*<font color="FFF0F5"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFF0F5"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LAVENDER_BLUSH = new Color(0xFFF0F5);
	/**
	*<font color="7CFC00"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="7CFC00"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LAWN_GREEN = new Color(0x7CFC00);
	/**
	*<font color="FFFACD"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFFACD"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LEMON_CHIFFON = new Color(0xFFFACD);
	/**
	*<font color="ADD8E6"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="ADD8E6"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_BLUE = new Color(0xADD8E6);
	/**
	*<font color="F08080"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="F08080"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_CORAL = new Color(0xF08080);
	/**
	*<font color="E0FFFF"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="E0FFFF"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_CYAN = new Color(0xE0FFFF);
	/**
	*<font color="FAFAD2"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FAFAD2"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_GOLDEN_ROD_YELLOW = new Color(0xFAFAD2);
	/**
	*<font color="D3D3D3"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="D3D3D3"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_GRAY = new Color(0xD3D3D3);
	/**
	*<font color="D3D3D3"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="D3D3D3"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_GREY = new Color(0xD3D3D3);
	/**
	*<font color="90EE90"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="90EE90"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_GREEN = new Color(0x90EE90);
	/**
	*<font color="FFB6C1"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFB6C1"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_PINK = new Color(0xFFB6C1);
	/**
	*<font color="FFA07A"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFA07A"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_SALMON = new Color(0xFFA07A);
	/**
	*<font color="20B2AA"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="20B2AA"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_SEA_GREEN = new Color(0x20B2AA);
	/**
	*<font color="87CEFA"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="87CEFA"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_SKY_BLUE = new Color(0x87CEFA);
	/**
	*<font color="778899"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="778899"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_SLATE_GRAY = new Color(0x778899);
	/**
	*<font color="778899"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="778899"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_SLATE_GREY = new Color(0x778899);
	/**
	*<font color="B0C4DE"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="B0C4DE"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_STEEL_BLUE = new Color(0xB0C4DE);
	/**
	*<font color="FFFFE0"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFFFE0"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIGHT_YELLOW = new Color(0xFFFFE0);
	/**
	*<font color="00FF00"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="00FF00"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIME = new Color(0x00FF00);
	/**
	*<font color="32CD32"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="32CD32"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LIME_GREEN = new Color(0x32CD32);
	/**
	*<font color="FAF0E6"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FAF0E6"><strong> 1234567890 </strong> </font>
	*/
	public static final Color LINEN = new Color(0xFAF0E6);
	/**
	*<font color="FF00FF"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FF00FF"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MAGENTA = new Color(0xFF00FF);
	/**
	*<font color="800000"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="800000"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MAROON = new Color(0x800000);
	/**
	*<font color="66CDAA"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="66CDAA"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MEDIUM_AQUA_MARINE = new Color(0x66CDAA);
	/**
	*<font color="0000CD"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="0000CD"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MEDIUM_BLUE = new Color(0x0000CD);
	/**
	*<font color="BA55D3"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="BA55D3"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MEDIUM_ORCHID = new Color(0xBA55D3);
	/**
	*<font color="9370DB"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="9370DB"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MEDIUM_PURPLE = new Color(0x9370DB);
	/**
	*<font color="3CB371"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="3CB371"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MEDIUM_SEA_GREEN = new Color(0x3CB371);
	/**
	*<font color="7B68EE"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="7B68EE"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MEDIUM_SLATE_BLUE = new Color(0x7B68EE);
	/**
	*<font color="00FA9A"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="00FA9A"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MEDIUM_SPRING_GREEN = new Color(0x00FA9A);
	/**
	*<font color="48D1CC"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="48D1CC"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MEDIUM_TURQUOISE = new Color(0x48D1CC);
	/**
	*<font color="C71585"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="C71585"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MEDIUM_VIOLET_RED = new Color(0xC71585);
	/**
	*<font color="191970"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="191970"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MIDNIGHT_BLUE = new Color(0x191970);
	/**
	*<font color="F5FFFA"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="F5FFFA"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MINT_CREAM = new Color(0xF5FFFA);
	/**
	*<font color="FFE4E1"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFE4E1"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MISTY_ROSE = new Color(0xFFE4E1);
	/**
	*<font color="FFE4B5"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFE4B5"><strong> 1234567890 </strong> </font>
	*/
	public static final Color MOCCASIN = new Color(0xFFE4B5);
	/**
	*<font color="FFDEAD"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFDEAD"><strong> 1234567890 </strong> </font>
	*/
	public static final Color NAVAJO_WHITE = new Color(0xFFDEAD);
	/**
	*<font color="000080"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="000080"><strong> 1234567890 </strong> </font>
	*/
	public static final Color NAVY = new Color(0x000080);
	/**
	*<font color="FDF5E6"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FDF5E6"><strong> 1234567890 </strong> </font>
	*/
	public static final Color OLD_LACE = new Color(0xFDF5E6);
	/**
	*<font color="808000"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="808000"><strong> 1234567890 </strong> </font>
	*/
	public static final Color OLIVE = new Color(0x808000);
	/**
	*<font color="6B8E23"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="6B8E23"><strong> 1234567890 </strong> </font>
	*/
	public static final Color OLIVE_DRAB = new Color(0x6B8E23);
	/**
	*<font color="FFA500"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFA500"><strong> 1234567890 </strong> </font>
	*/
	public static final Color ORANGE = new Color(0xFFA500);
	/**
	*<font color="FF4500"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FF4500"><strong> 1234567890 </strong> </font>
	*/
	public static final Color ORANGE_RED = new Color(0xFF4500);
	/**
	*<font color="DA70D6"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="DA70D6"><strong> 1234567890 </strong> </font>
	*/
	public static final Color ORCHID = new Color(0xDA70D6);
	/**
	*<font color="EEE8AA"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="EEE8AA"><strong> 1234567890 </strong> </font>
	*/
	public static final Color PALE_GOLDEN_ROD = new Color(0xEEE8AA);
	/**
	*<font color="98FB98"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="98FB98"><strong> 1234567890 </strong> </font>
	*/
	public static final Color PALE_GREEN = new Color(0x98FB98);
	/**
	*<font color="AFEEEE"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="AFEEEE"><strong> 1234567890 </strong> </font>
	*/
	public static final Color PALE_TURQUOISE = new Color(0xAFEEEE);
	/**
	*<font color="DB7093"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="DB7093"><strong> 1234567890 </strong> </font>
	*/
	public static final Color PALE_VIOLET_RED = new Color(0xDB7093);
	/**
	*<font color="FFEFD5"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFEFD5"><strong> 1234567890 </strong> </font>
	*/
	public static final Color PAPAYA_WHIP = new Color(0xFFEFD5);
	/**
	*<font color="FFDAB9"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFDAB9"><strong> 1234567890 </strong> </font>
	*/
	public static final Color PEACH_PUFF = new Color(0xFFDAB9);
	/**
	*<font color="CD853F"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="CD853F"><strong> 1234567890 </strong> </font>
	*/
	public static final Color PERU = new Color(0xCD853F);
	/**
	*<font color="FFC0CB"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFC0CB"><strong> 1234567890 </strong> </font>
	*/
	public static final Color PINK = new Color(0xFFC0CB);
	/**
	*<font color="DDA0DD"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="DDA0DD"><strong> 1234567890 </strong> </font>
	*/
	public static final Color PLUM = new Color(0xDDA0DD);
	/**
	*<font color="B0E0E6"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="B0E0E6"><strong> 1234567890 </strong> </font>
	*/
	public static final Color POWDER_BLUE = new Color(0xB0E0E6);
	/**
	*<font color="800080"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="800080"><strong> 1234567890 </strong> </font>
	*/
	public static final Color PURPLE = new Color(0x800080);
	/**
	*<font color="663399"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="663399"><strong> 1234567890 </strong> </font>
	*/
	public static final Color REBECCA_PURPLE = new Color(0x663399);
	/**
	*<font color="FF0000"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FF0000"><strong> 1234567890 </strong> </font>
	*/
	public static final Color RED = new Color(0xFF0000);
	/**
	*<font color="BC8F8F"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="BC8F8F"><strong> 1234567890 </strong> </font>
	*/
	public static final Color ROSY_BROWN = new Color(0xBC8F8F);
	/**
	*<font color="4169E1"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="4169E1"><strong> 1234567890 </strong> </font>
	*/
	public static final Color ROYAL_BLUE = new Color(0x4169E1);
	/**
	*<font color="8B4513"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="8B4513"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SADDLE_BROWN = new Color(0x8B4513);
	/**
	*<font color="FA8072"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FA8072"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SALMON = new Color(0xFA8072);
	/**
	*<font color="F4A460"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="F4A460"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SANDY_BROWN = new Color(0xF4A460);
	/**
	*<font color="2E8B57"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="2E8B57"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SEA_GREEN = new Color(0x2E8B57);
	/**
	*<font color="FFF5EE"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFF5EE"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SEA_SHELL = new Color(0xFFF5EE);
	/**
	*<font color="A0522D"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="A0522D"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SIENNA = new Color(0xA0522D);
	/**
	*<font color="C0C0C0"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="C0C0C0"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SILVER = new Color(0xC0C0C0);
	/**
	*<font color="87CEEB"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="87CEEB"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SKY_BLUE = new Color(0x87CEEB);
	/**
	*<font color="6A5ACD"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="6A5ACD"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SLATE_BLUE = new Color(0x6A5ACD);
	/**
	*<font color="708090"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="708090"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SLATE_GRAY = new Color(0x708090);
	/**
	*<font color="708090"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="708090"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SLATE_GREY = new Color(0x708090);
	/**
	*<font color="FFFAFA"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFFAFA"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SNOW = new Color(0xFFFAFA);
	/**
	*<font color="00FF7F"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="00FF7F"><strong> 1234567890 </strong> </font>
	*/
	public static final Color SPRING_GREEN = new Color(0x00FF7F);
	/**
	*<font color="4682B4"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="4682B4"><strong> 1234567890 </strong> </font>
	*/
	public static final Color STEEL_BLUE = new Color(0x4682B4);
	/**
	*<font color="D2B48C"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="D2B48C"><strong> 1234567890 </strong> </font>
	*/
	public static final Color TAN = new Color(0xD2B48C);
	/**
	*<font color="008080"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="008080"><strong> 1234567890 </strong> </font>
	*/
	public static final Color TEAL = new Color(0x008080);
	/**
	*<font color="D8BFD8"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="D8BFD8"><strong> 1234567890 </strong> </font>
	*/
	public static final Color THISTLE = new Color(0xD8BFD8);
	/**
	*<font color="FF6347"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FF6347"><strong> 1234567890 </strong> </font>
	*/
	public static final Color TOMATO = new Color(0xFF6347);
	/**
	*<font color="40E0D0"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="40E0D0"><strong> 1234567890 </strong> </font>
	*/
	public static final Color TURQUOISE = new Color(0x40E0D0);
	/**
	*<font color="EE82EE"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="EE82EE"><strong> 1234567890 </strong> </font>
	*/
	public static final Color VIOLET = new Color(0xEE82EE);
	/**
	*<font color="F5DEB3"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="F5DEB3"><strong> 1234567890 </strong> </font>
	*/
	public static final Color WHEAT = new Color(0xF5DEB3);
	/**
	*<font color="FFFFFF"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFFFFF"><strong> 1234567890 </strong> </font>
	*/
	public static final Color WHITE = new Color(0xFFFFFF);
	/**
	*<font color="F5F5F5"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="F5F5F5"><strong> 1234567890 </strong> </font>
	*/
	public static final Color WHITE_SMOKE = new Color(0xF5F5F5);
	/**
	*<font color="FFFF00"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="FFFF00"><strong> 1234567890 </strong> </font>
	*/
	public static final Color YELLOW = new Color(0xFFFF00);
	/**
	*<font color="9ACD32"><strong> Color Sample </strong> </font>
	*<br>
	*<font color="9ACD32"><strong> 1234567890 </strong> </font>
	*/
	public static final Color YELLOW_GREEN = new Color(0x9ACD32);
	
	public static final Color TRANSPARENT = new Color(0xffffff00, true);

	public static final byte SIZE_BYTES = 4 * Float.BYTES;

	private float r, g, b, a;

	public Color(int r, int g, int b, int a) {
		checkIllegalColor(r, g, b, a);
		this.r = (float) (r / 255f);
		this.g = (float) (g / 255f);
		this.b = (float) (b / 255f);
		this.a = (float) (a / 255f);
	}

	public Color(int r, int g, int b) {
		checkIllegalColor(r, g, b);
		this.r = (float) (r / 255f);
		this.g = (float) (g / 255f);
		this.b = (float) (b / 255f);
		this.a = (float) 1f;
	}

	public Color() {
		this.r = this.g = this.b = 0f;
		this.a = 1f;
	}
	
	

	public Color(int rgb) {
		checkIllegalColor(rgb);
		short red = (short) ((rgb >> 16) & 0x0000ff);
		short green = (short) ((rgb >> 8) & 0x00ff);
		short blue = (short) ((rgb >> 0) & 0xff);
		this.r = (float) (red / 255f);
		this.g = (float) (green / 255f);
		this.b = (float) (blue / 255f);
		this.a = 1f;
	}

	public Color(int rgba, boolean hasAlpha) {
		if (hasAlpha) {

			checkIllegalColorAlpha(rgba);
			short alpha = (short) ((rgba >> 24) & 0x000000ff);
			short red = (short) ((rgba >> 16) & 0x0000ff);
			short green = (short) ((rgba >> 8) & 0x00ff);
			short blue = (short) ((rgba >> 0) & 0xff);
			this.r = (float) (red / 255f);
			this.g = (float) (green / 255f);
			this.b = (float) (blue / 255f);
			this.a = (float) (alpha / 255f);
		} else {
			int val = rgba | 0xff000000;
			checkIllegalColor(val);
			short red = (short) ((val >> 16) & 0x0000ff);
			short green = (short) ((val >> 8) & 0x00ff);
			short blue = (short) ((val >> 0) & 0xff);
			this.r = (float) (red / 255f);
			this.g = (float) (green / 255f);
			this.b = (float) (blue / 255f);
			this.a = 1f;
		}
	}

	public Color(float r, float g, float b) {
		checkIllegalColor(r, g, b);
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1f;
	}

	public Color(float r, float g, float b, float a) {
		checkIllegalColor(r, g, b, a);
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color(Color other) {
		this.a = other.a;
		this.r = other.r;
		this.g = other.g;
		this.b = other.b;
	}
	
	public static int toRGB(int r, int g, int b) {
		return r << 16 | g << 8 | b;
	}

	public static int toRGB(float r, float g, float b) {
		return toRGB((int) (r * 255f), (int) (g * 255f), (int) (b * 255f));
	}

	private static void checkIllegalColor(int rgb) {
		if (rgb < 0 && rgb > 0xffffff)
			throw new IllegalArgumentException("RGB Color code cannot be less than 0");
		checkIllegalColor(
				((rgb >> 16) & 0x0000ff),
				((rgb >> 8) & 0x00ff),
				((rgb >> 0) & 0xff),
				0xff
		);
	}

	private static void checkIllegalColorAlpha(int rgba) {
		checkIllegalColor(
				(short) ((rgba >> 16) & 0x0000ff),
				(short) ((rgba >> 8) & 0x00ff),
				(short) ((rgba >> 0) & 0xff),
				(short) ((rgba >> 24) & 0x000000ff)
		);
	}

	private static void checkIllegalColor(int r, int g, int b) {
		checkIllegalColor(r, g, b, 255);
	}

	private static void checkIllegalColor(int red, int green, int blue, int alpha) {
		String excMesage = null;
		if (alpha > 255 || alpha < 0) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			excMesage += " alpha : " + alpha;
		}
		if (red > 255 || red < 0) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			else
				excMesage += ", ";
			excMesage += " red : " + red;
		}
		if (green > 255 || green < 0) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			else
				excMesage += ", ";
			excMesage += " green : " + green;
		}
		if (blue > 255 || blue < 0) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			else
				excMesage += ", ";
			excMesage += " green : " + green;
		}
		if (excMesage != null)
			throw new IllegalArgumentException(excMesage);
	}

	private static void checkIllegalColor(float r, float g, float b) {
		checkIllegalColor(r, g, b, 1f);
	}

	private static void checkIllegalColor(float r, float g, float b, float a) {
		String excMesage = null;
		if (r > 1f || r < 0f) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			excMesage += "red : " + r;
		}
		if (g > 1f || g < 0f) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			else
				excMesage += ", ";
			excMesage += "green : " + g;
		}
		if (b > 1f || b < 0f) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			else
				excMesage += ", ";
			excMesage += "blue : " + b;
		}
		if (a > 1f || a < 0f) {
			if (excMesage == null)
				excMesage = "Illegal channel value :";
			else
				excMesage += ", ";
			excMesage += "alpha : " + a;
		}
		if (excMesage != null)
			throw new IllegalArgumentException(excMesage);
	}

	private static final float BRIGHTNESS_ALTER_FACTOR = 0.2f;

	public Color brighter() {
		float r = vgl.maths.Maths.clamp(this.r + BRIGHTNESS_ALTER_FACTOR, 1f, 0f);
		float g = vgl.maths.Maths.clamp(this.g + BRIGHTNESS_ALTER_FACTOR, 1f, 0f);
		float b = vgl.maths.Maths.clamp(this.b + BRIGHTNESS_ALTER_FACTOR, 1f, 0f);
		return new Color(r, g, b);
	}

	public Color darker() {
		float r = vgl.maths.Maths.clamp(this.r - BRIGHTNESS_ALTER_FACTOR, 1f, 0f);
		float g = vgl.maths.Maths.clamp(this.g - BRIGHTNESS_ALTER_FACTOR, 1f, 0f);
		float b = vgl.maths.Maths.clamp(this.b - BRIGHTNESS_ALTER_FACTOR, 1f, 0f);
		return new Color(r, g, b);
	}

	public float getAlpha() {
		return a;
	}

	public float getRed() {
		return r;
	}

	public float getBlue() {
		return b;
	}

	public float getGreen() {
		return g;
	}

	public int getRGB() {
		int r = (int) (this.r * 255f);
		int g = (int) (this.g * 255f);
		int b = (int) (this.b * 255f);
		return ((r & 0xff) << 16)
				| ((g & 0xff) << 8)
				| ((b & 0xff) << 0);
	}

	public int getARGB() {
		int a = (int) (this.a * 255f);
		int r = (int) (this.r * 255f);
		int g = (int) (this.g * 255f);
		int b = (int) (this.b * 255f);
		return ((a & 0xff) << 24)
				| ((r & 0xff) << 16)
				| ((g & 0xff) << 8)
				| ((b & 0xff) << 0);
	}

	public static Color fromARGB(int argb) {
		short alpha =   (short)  ((argb >> 24) & 0x000000ff);
		short red = (short)  ((argb >> 16) & 0x0000ff);
		short green =  (short)  ((argb >> 8)  & 0x00ff);
		short blue = (short)  ((argb >> 0)  & 0xff);
		return new Color(
				(float) (red / 255.0f),
				(float) (green / 255.0f),
				(float) (blue / 255.0f),
				(float) (alpha / 255.0f)
				        );
	}
	
	public int getRGBA() {
		int a = (int) (this.a * 255f);
		int r = (int) (this.r * 255f);
		int g = (int) (this.g * 255f);
		int b = (int) (this.b * 255f);
		return 
				  ((r & 0xff) << 0)
				| ((g & 0xff) << 8)
				| ((b & 0xff) << 16)
				| ((a & 0xff) << 24);
	}
	
	public boolean hasTransparency() {
		return a < 1f;
	}
	
	private void checkChannelValue(float a) {
		if(a < 0.0f || a > 1.0f)
			throw new IllegalArgumentException("The value entered ["+a+"] is out of bounds [0;1]");
	}

	public vgl.maths.vector.Vector4f toVector() {
		return new vgl.maths.vector.Vector4f(r, g, b, a);
	}
	
	/**
	 * Stores color in a buffer
	 * ordering = RGBA
	 * @param buffer
	 */
	public void store(MemoryBufferFloat buffer) {
		buffer 
		      .put(r).put(g).put(b).put(a);
	}
	
	public static boolean isValid(float r, float g, float b, float a) {		
		return r >= 0f && g >= 0f && b >= 0f && a >= 0f && r <= 1f && g <= 1f
				&& b <= 1f && a <= 1f;
	}
	
	public static boolean isValid(Vector4f color) {
		return isValid(color.x, color.y, color.z, color.w);
	}

	@Override
	public String toString() {
		return "Color [r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(a);
		result = prime * result + Float.floatToIntBits(b);
		result = prime * result + Float.floatToIntBits(g);
		result = prime * result + Float.floatToIntBits(r);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		if (Float.floatToIntBits(a) != Float.floatToIntBits(other.a))
			return false;
		if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b))
			return false;
		if (Float.floatToIntBits(g) != Float.floatToIntBits(other.g))
			return false;
		if (Float.floatToIntBits(r) != Float.floatToIntBits(other.r))
			return false;
		return true;
	}

	@Override
	public int compareTo(Color o) {
		int rgb = getRGB();
		int rgbo = o.getRGB();
		return rgb >= rgbo ?
				1 : rgb == rgbo ?
				0 : -1;
	}
}
