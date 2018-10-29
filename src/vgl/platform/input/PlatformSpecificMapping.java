package vgl.platform.input;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.KeyCodes;

import vgl.core.input.Key;
import vgl.core.internal.GlobalDetails;
import vgl.platform.Platform;

abstract public class PlatformSpecificMapping {
	
	private PlatformSpecificMapping() {
		
	}
	
	private static Map<Integer, Integer> webPlatformKeyMap;
	
	public static void init() {
		webPlatformKeyMap = new HashMap<>();
		/*
		 * Letters
		 */
		webPlatformKeyMap.put(Key.A, KeyCodes.KEY_A);
		webPlatformKeyMap.put(Key.B, KeyCodes.KEY_B);
		webPlatformKeyMap.put(Key.C, KeyCodes.KEY_C);
		webPlatformKeyMap.put(Key.D, KeyCodes.KEY_D);
		webPlatformKeyMap.put(Key.E, KeyCodes.KEY_E);
		webPlatformKeyMap.put(Key.F, KeyCodes.KEY_F);
		webPlatformKeyMap.put(Key.G, KeyCodes.KEY_G);
		webPlatformKeyMap.put(Key.H, KeyCodes.KEY_H);
		webPlatformKeyMap.put(Key.I, KeyCodes.KEY_I);
		webPlatformKeyMap.put(Key.J, KeyCodes.KEY_J);
		webPlatformKeyMap.put(Key.K, KeyCodes.KEY_K);
		webPlatformKeyMap.put(Key.L, KeyCodes.KEY_L);
		webPlatformKeyMap.put(Key.M, KeyCodes.KEY_M);
		webPlatformKeyMap.put(Key.N, KeyCodes.KEY_N);
		webPlatformKeyMap.put(Key.O, KeyCodes.KEY_O);
		webPlatformKeyMap.put(Key.P, KeyCodes.KEY_P);
		webPlatformKeyMap.put(Key.Q, KeyCodes.KEY_Q);
		webPlatformKeyMap.put(Key.R, KeyCodes.KEY_R);
		webPlatformKeyMap.put(Key.S, KeyCodes.KEY_S);
		webPlatformKeyMap.put(Key.T, KeyCodes.KEY_T);
		webPlatformKeyMap.put(Key.U, KeyCodes.KEY_U);
		webPlatformKeyMap.put(Key.V, KeyCodes.KEY_V);
		webPlatformKeyMap.put(Key.W, KeyCodes.KEY_W);
		webPlatformKeyMap.put(Key.X, KeyCodes.KEY_X);
		webPlatformKeyMap.put(Key.Y, KeyCodes.KEY_Y);
		webPlatformKeyMap.put(Key.Z, KeyCodes.KEY_Z);
		/**
		 * Numbers
		 */
		webPlatformKeyMap.put(Key.KEY_0, KeyCodes.KEY_ZERO);
		webPlatformKeyMap.put(Key.KEY_1, KeyCodes.KEY_ONE);
		webPlatformKeyMap.put(Key.KEY_2, KeyCodes.KEY_TWO);
		webPlatformKeyMap.put(Key.KEY_3, KeyCodes.KEY_THREE);
		webPlatformKeyMap.put(Key.KEY_4, KeyCodes.KEY_FOUR);
		webPlatformKeyMap.put(Key.KEY_5, KeyCodes.KEY_FIVE);
		webPlatformKeyMap.put(Key.KEY_6, KeyCodes.KEY_SIX);
		webPlatformKeyMap.put(Key.KEY_7, KeyCodes.KEY_SEVEN);
		webPlatformKeyMap.put(Key.KEY_8, KeyCodes.KEY_EIGHT);
		webPlatformKeyMap.put(Key.KEY_9, KeyCodes.KEY_NINE);
		/**
		 * Numpad numbers
		 */
		webPlatformKeyMap.put(Key.NUMPAD_0, KeyCodes.KEY_NUM_ZERO);
		webPlatformKeyMap.put(Key.NUMPAD_1, KeyCodes.KEY_NUM_ONE); 
		webPlatformKeyMap.put(Key.NUMPAD_2, KeyCodes.KEY_NUM_TWO); 
		webPlatformKeyMap.put(Key.NUMPAD_3, KeyCodes.KEY_NUM_THREE);
		webPlatformKeyMap.put(Key.NUMPAD_4, KeyCodes.KEY_NUM_FOUR);
		webPlatformKeyMap.put(Key.NUMPAD_5, KeyCodes.KEY_NUM_FIVE);
		webPlatformKeyMap.put(Key.NUMPAD_6, KeyCodes.KEY_NUM_SIX); 
		webPlatformKeyMap.put(Key.NUMPAD_7, KeyCodes.KEY_NUM_SEVEN);
		webPlatformKeyMap.put(Key.NUMPAD_8, KeyCodes.KEY_NUM_EIGHT);
		webPlatformKeyMap.put(Key.NUMPAD_9, KeyCodes.KEY_NUM_NINE);
		/**
		 * Numpad actions
		 */
		webPlatformKeyMap.put(Key.NUMPAD_ADD, KeyCodes.KEY_NUM_PLUS);
		webPlatformKeyMap.put(Key.NUMPAD_SUBTRACT, KeyCodes.KEY_NUM_MINUS);
		webPlatformKeyMap.put(Key.NUMPAD_DIVIDE, KeyCodes.KEY_NUM_DIVISION);
		webPlatformKeyMap.put(Key.NUMPAD_MULTIPLY, KeyCodes.KEY_NUM_MULTIPLY);
		webPlatformKeyMap.put(Key.NUMPAD_DECIMAL, KeyCodes.KEY_NUM_PERIOD);
		/**
		 * F keys
		 */
		webPlatformKeyMap.put(Key.F1, KeyCodes.KEY_F1);
		webPlatformKeyMap.put(Key.F2, KeyCodes.KEY_F2);
		webPlatformKeyMap.put(Key.F3, KeyCodes.KEY_F3);
		webPlatformKeyMap.put(Key.F4, KeyCodes.KEY_F4);
		webPlatformKeyMap.put(Key.F5, KeyCodes.KEY_F5);
		webPlatformKeyMap.put(Key.F6, KeyCodes.KEY_F6);
		webPlatformKeyMap.put(Key.F7, KeyCodes.KEY_F7);
		webPlatformKeyMap.put(Key.F8, KeyCodes.KEY_F8);
		webPlatformKeyMap.put(Key.F9, KeyCodes.KEY_F9);
		webPlatformKeyMap.put(Key.F10, KeyCodes.KEY_F10);
		webPlatformKeyMap.put(Key.F11, KeyCodes.KEY_F11);
		webPlatformKeyMap.put(Key.F12, KeyCodes.KEY_F12);
		/**
		 * Arrow keys
		 */
		webPlatformKeyMap.put(Key.UP,KeyCodes.KEY_UP);
		webPlatformKeyMap.put(Key.DOWN,KeyCodes.KEY_DOWN);
		webPlatformKeyMap.put(Key.LEFT,KeyCodes.KEY_LEFT);
		webPlatformKeyMap.put(Key.RIGHT,KeyCodes.KEY_RIGHT);
		/**
		 * Rest
		 */
		webPlatformKeyMap.put(Key.LEFT_SHIFT, KeyCodes.KEY_SHIFT);
		webPlatformKeyMap.put(Key.LEFT_CONTROL, KeyCodes.KEY_CTRL);
		webPlatformKeyMap.put(Key.SPACE, KeyCodes.KEY_SPACE);
		webPlatformKeyMap.put(Key.CAPS_LOCK, KeyCodes.KEY_CAPS_LOCK);
		webPlatformKeyMap.put(Key.TAB, KeyCodes.KEY_TAB);
		webPlatformKeyMap.put(Key.LEFT_ALT, KeyCodes.KEY_ALT);
		webPlatformKeyMap.put(Key.BACKSPACE, KeyCodes.KEY_BACKSPACE);
		webPlatformKeyMap.put(Key.DELETE, KeyCodes.KEY_DELETE);
		webPlatformKeyMap.put(Key.END, KeyCodes.KEY_END);
		webPlatformKeyMap.put(Key.ESCAPE, KeyCodes.KEY_ESCAPE);
		webPlatformKeyMap.put(Key.PAGE_UP, KeyCodes.KEY_PAGEUP);
		webPlatformKeyMap.put(Key.PAGE_DOWN, KeyCodes.KEY_PAGEDOWN);
		webPlatformKeyMap.put(Key.PRINT_SCREEN, KeyCodes.KEY_PRINT_SCREEN);
		webPlatformKeyMap.put(Key.INSERT, KeyCodes.KEY_INSERT);
	}
	
	public static int forKey(int key) {
		if (GlobalDetails.getPlatform() == Platform.WEB)
			if (webPlatformKeyMap.containsKey(key))
				return webPlatformKeyMap.get(key);
		return key;
	}
	
}
