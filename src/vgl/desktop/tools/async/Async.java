package vgl.desktop.tools.async;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

public class Async {

	static {
		
	}

	public static void invoke(float time, Object obj, String methodName) {
		int t = (int) (time * 1000f);
		System.out.println(t);
		new AsynchronousInvokation(t, obj, methodName);
	}

	public static void invokeStatic(float time, Class<?> clazz, String methodName) {
		int t = (int) (time * 1000f);
		System.out.println(t);
		new AsynchronousInvokation(t, clazz, methodName);
	}

	private static class AsynchronousInvokation {
		AsynchronousInvokation(long time, Class<?> clazz, String methodName) {
			Timer t = new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					AsynchronousInvokation.this.invokeStatic(clazz, methodName);
					System.out.println("here");
					t.cancel();
				}
			}, time);
		}

		AsynchronousInvokation(long time, Object obj, String methodName) {
			Timer t = new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					AsynchronousInvokation.this.invoke(obj, methodName);
					System.out.println("here");
					t.cancel();
				}
			}, time);
		}

		private void invoke(Object obj, String methodName) {
			for (Method m : obj.getClass().getDeclaredMethods()) {
				if (m.getName().equals(methodName)) {
					try {
						m.invoke(obj, (Object[]) null);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		private void invokeStatic(Class<?> clazz, String methodName) {
			for (Method m : clazz.getDeclaredMethods()) {
				if (m.getName().equals(methodName)) {
					try {
						m.setAccessible(true);
						m.invoke(null, (Object[]) null);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
