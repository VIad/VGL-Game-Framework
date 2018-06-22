package vgl.desktop.system;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import vgl.core.gfx.render.VertexLayout;
import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector3f;
import vgl.maths.vector.Vector4f;

//============================================================================
//Name        : Size
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018
//Description : A Java implementation of C / C++ sizeof operator, returning
//a variable / class's size in bytes
//============================================================================

/**
 * A Java implementation of C / C++ sizeof operator, consisting of methods,
 * returning the size (in bytes) of the given variable / class
 * 
 * @author vgl
 *
 */
public class Size {

	private Size() {
	}

	public static int of(int a) {
		return Integer.BYTES;
	}

	public static int of(short a) {
		return Short.BYTES;
	}

	public static int of(double a) {
		return Double.BYTES;
	}

	public static int of(long a) {
		return Long.BYTES;
	}

	public static int of(float a) {
		return Float.BYTES;
	}

	public static int of(char a) {
		return Character.BYTES;
	}

	public static int of(byte a) {
		return Byte.BYTES;
	}

	public static int of(boolean a) {
		return Byte.BYTES;
	}

	/**
	 * cache all primitives for later access
	 */
	private static final java.util.Map<Class<?>, Byte> WRAPPER_TYPES = new java.util.HashMap<>();

	static {
		WRAPPER_TYPES.put(boolean.class, (byte) 1);
		WRAPPER_TYPES.put(char.class, (byte) (1 << 1));
		WRAPPER_TYPES.put(byte.class, (byte) 1);
		WRAPPER_TYPES.put(short.class, (byte) (1 << 1));
		WRAPPER_TYPES.put(int.class, (byte) (1 << 2));
		WRAPPER_TYPES.put(long.class, (byte) (1 << 3));
		WRAPPER_TYPES.put(float.class, (byte) (1 << 2));
		WRAPPER_TYPES.put(double.class, (byte) (1 << 3));
		WRAPPER_TYPES.put(void.class, (byte) 0);
	}

	private static boolean isWrapperType(Class<?> clazz) {
		return WRAPPER_TYPES.containsKey(clazz);
	}

	private static boolean isArray(Class<?> clazz) {
		return clazz.isArray();
	}

	private static byte wrapperSize(Class<?> wrapper) {
		return WRAPPER_TYPES.get(wrapper);
	}

	// private static int ofSuperClass(Class<?> clazz) {
	//
	// }

	private static int ofSuperClass(Class<?> superClass) {
		int sum = 0;
		if (superClass.getSuperclass() != null) {
			sum += ofSuperClass(superClass.getSuperclass());
		}
		if (isArray(superClass)) {
			throw new vgl.core.exception.VGLRuntimeException("exc");
		}
		if (isWrapperType(superClass)) {
			sum += wrapperSize(superClass);
		}
		for (Field field : superClass.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers()))
				continue;
			if (!field.isAccessible())
				field.setAccessible(true);
			if (isWrapperType(field.getType())) {
				sum += wrapperSize(field.getType());
				continue;
			}
			sum += ofSuperClass(superClass);
		}
		return sum;
	}

	public static int __sizeofVL(Class<? extends VertexLayout> clazz) {
		int sum = 0;
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getType() == int.class) {
				sum += Integer.BYTES;
			} else if (field.getType() == float.class) {
				sum += Float.BYTES;
			} else if (field.getType() == Vector2f.class) {
				sum += Vector2f.SIZE_BYTES;
			} else if (field.getType() == Vector3f.class) {
				sum += Vector3f.SIZE_BYTES;
			} else if (field.getType() == Vector4f.class) {
				sum += Vector4f.SIZE_BYTES;
			} else {
				throw new vgl.core.exception.VGLRuntimeException(
				        "Type >> " + field.getType().getName() + " not supported by GLSL");
			}
		}
		return sum;
	}

	private static int ofClass(Class<?> clazz) {
		int sum = 0;
		if (clazz == null)
			return 0;
		for (Field f : clazz.getDeclaredFields()) {
			if (Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			if (isWrapperType(f.getType())) {
				sum += wrapperSize(f.getType());
				continue;
			}
			if (isArray(f.getType())) {
				if (f.getType() == int[].class || f.getType() == Integer[].class) {
					sum += 4;
				}
				if (f.getType() == boolean[].class || f.getType() == Boolean[].class) {
					sum += 1;
				}
				if (f.getType() == char[].class || f.getType() == Character[].class) {
					sum += 2;
				}
				if (f.getType() == short[].class || f.getType() == Short[].class) {
					sum += 2;
				}
				if (f.getType() == long[].class || f.getType() == Long[].class) {
					sum += 8;
				}
				if (f.getType() == double[].class || f.getType() == Double[].class) {
					sum += 8;
				}
				if (f.getType() == float[].class || f.getType() == Float[].class) {
					sum += 4;
				}
				if (f.getType() == byte[].class || f.getType() == Byte[].class) {
					sum += 1;
				}
				continue;
			}
			sum += ofClass(f.getType());
		}
		return sum;
	}

	/**
	 * Returns the size (in bytes) of all instance fields represented by the
	 * <code> Class<?> </code> object <br>
	 * <strong> NOTE : this only takes into account the instance fields of the class
	 * (NOT THE STATIC ONES) </strong> <br>
	 * if the <code> Class </code>object is null, the method will return 0
	 * 
	 * @param clazz
	 * @return size of the Class object in bytes
	 */
	public static int of(Class<?> clazz) {
		System.out.println("Called with : " + clazz);
		return ofClass(clazz);
	}

	private static boolean hasArray(Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers()))
				continue;
			if (field.getType().isPrimitive())
				continue;
			if (!field.isAccessible())
				field.setAccessible(true);
			if (field.getType().getSuperclass() != null) {
				return hasArray(clazz.getSuperclass());
			}
			if (field.getType().isArray())
				return true;
			if (hasArray(field.getType()))
				return true;
		}
		return false;
	}

	private static int ofSuperClass(Object obj, Class<?> superClass) {
		int sum = 0;
		if (superClass.getSuperclass() != null) {
			sum += ofSuperClass(obj, superClass.getSuperclass());
		}
		if (isArray(superClass)) {
			sum += arrayHandle(obj);
		}
		if (isWrapperType(superClass)) {
			sum += primitiveHandle(obj);
		}
		for (Field field : superClass.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers()))
				continue;
			if (!field.isAccessible())
				field.setAccessible(true);
			if (isWrapperType(field.getType())) {
				sum += wrapperSize(field.getType());
				continue;
			}
			Object fieldVal = extractFromField(obj, field);
			if (fieldVal == null)
			continue;
			if (isArray(field.getType())) {
				sum += arrayHandle(fieldVal);
				continue;
			}
			sum += ofSuperClass(fieldVal, superClass);
		}
		return sum;
	}

	/**
	 * Returns the size (in bytes) of all <strong> not null</strong> fields
	 * represented by the <code> Object</code> passed <br>
	 * <strong> NOTE : this only takes into account the instance fields of the class
	 * (NOT THE STATIC ONES) </strong> <br>
	 * if the <code> Object </code>passed is null, the method will return 0
	 * 
	 * @param obj
	 * @return
	 */
	public static int of(Object obj) {
		if (obj == null)
			return 0;
		int sum = 0;
		Class<?> clazz = obj.getClass();
		if (clazz.getSuperclass() != null) {
			sum += ofSuperClass(obj, clazz.getSuperclass());
		}
		if (isArray(clazz)) {
			sum += arrayHandle(obj);
		}
		if (isWrapperType(clazz)) {
			sum += primitiveHandle(obj);
		}
		for (Field field : clazz.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers()))
				continue;
			if (!field.isAccessible())
				field.setAccessible(true);
			if (isWrapperType(field.getType())) {
				sum += wrapperSize(field.getType());
				continue;
			}
			Object fieldVal = extractFromField(obj, field);
			if (isArray(field.getType())) {
				if (fieldVal == null)
					continue;
				sum += arrayHandle(fieldVal);
				continue;
			}
			sum += of(fieldVal);
		}
		return sum;
	}

	private static Object extractFromField(Object from, Field field) {
		try {
			return field.get(from);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return null;
		}
	}

	private static int primitiveHandle(Object obj) {
		return wrapperSize(obj.getClass());
	}

	private static int arrayHandle(Object obj) {
		if (obj instanceof boolean[]) {
			boolean[] arr = (boolean[]) obj;
			return arr.length * 1;
		}
		if (obj instanceof byte[]) {
			byte[] arr = (byte[]) obj;
			return arr.length * Byte.BYTES;
		}
		if (obj instanceof short[]) {
			short[] arr = (short[]) obj;
			return arr.length * Short.BYTES;
		}
		if (obj instanceof char[]) {
			char[] arr = (char[]) obj;
			return arr.length * Character.BYTES;
		}
		if (obj instanceof int[]) {
			int[] arr = (int[]) obj;
			return arr.length * Integer.BYTES;
		}
		if (obj instanceof float[]) {
			float[] arr = (float[]) obj;
			return arr.length * Float.BYTES;
		}
		if (obj instanceof long[]) {
			long[] arr = (long[]) obj;
			return arr.length * Long.BYTES;
		}
		if (obj instanceof double[]) {
			double[] arr = (double[]) obj;
			return arr.length * Double.BYTES;
		}
		Object[] arr = (Object[]) obj;
		int sum = 0;
		for (Object ob : arr) {
			sum += of(ob);
		}
		return sum;
	}

}
