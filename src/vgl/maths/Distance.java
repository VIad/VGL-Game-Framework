package vgl.maths;

import vgl.maths.vector.Vector2f;
import vgl.maths.vector.Vector2i;
import vgl.maths.vector.Vector3f;
import vgl.maths.vector.Vector3i;
//============================================================================
//Name        : Distance
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : Class with methods for high precision distance finding between 
//positional vectors
//============================================================================

/**
 * 
 * @author vgl
 *
 */
strictfp public class Distance {

	/**
	 * No instances of this class should exist
	 */
	private Distance() {}

	/**
	 * Finds the distance between the two vectors using Pythagoras' theorem 
	 * and square roots
	 * @param v
	 * @param v2
	 * @return The Distance between the two positions in float
	 */
	public static float between(final Vector3f v, final Vector3f v2) {
		return (float) Math.sqrt(((v.x - v2.x) * (v.x - v2.x)) + ((v.y - v2.y) * (v.y - v2.y)) + ((v.z - v2.z) * (v.z - v2.z)));
	}

	/**
	 * Finds the distance between the two vectors using Pythagoras' theorem 
	 * and square roots
	 * @param v
	 * @param v2
	 * @return The Distance between the two positions in float
	 */
	public static float between(final Vector3f v, final Vector3i v2) {
		return (float) Math.sqrt(((v.x - v2.x) * (v.x - v2.x)) + ((v.y - v2.y) * (v.y - v2.y)) + ((v.z - v2.z) * (v.z - v2.z)));
	}

	/**
	 * Finds the distance between the two vectors using Pythagoras' theorem 
	 * and square roots
	 * @param v
	 * @param v2
	 * @return The Distance between the two positions in float
	 */
	public static float between(final Vector3i v, final Vector3i v2) {
		return (float) Math.sqrt(((v.x - v2.x) * (v.x - v2.x)) + ((v.y - v2.y) * (v.y - v2.y)) + ((v.z - v2.z) * (v.z - v2.z)));
	}

	/**
	 * Finds the distance between the two vectors using Pythagoras' theorem 
	 * and square roots
	 * @param v
	 * @param v2
	 * @return The Distance between the two positions in float
	 */
	public static float between(final Vector2f v, final Vector2f v2) {
		return (float) Math.sqrt(((v.x - v2.x) * (v.x - v2.x)) + ((v.y - v2.y) * (v.y - v2.y)));
	}

	/**
	 * Finds the distance between the two vectors using Pythagoras' theorem 
	 * and square roots
	 * @param v
	 * @param v2
	 * @return The Distance between the two positions in float
	 */
	public static float between(final Vector2f v, final Vector2i v2) {
		return (float) Math.sqrt(((v.x - v2.x) * (v.x - v2.x)) + ((v.y - v2.y) * (v.y - v2.y)));
	}

	/**
	 * Finds the distance between the two vectors using Pythagoras' theorem 
	 * and square roots
	 * @param v
	 * @param v2
	 * @return The Distance between the two positions in float
	 */
	public static float between(final Vector2i v, final Vector2i v2) {
		return (float) Math.sqrt(((v.x - v2.x) * (v.x - v2.x)) + ((v.y - v2.y) * (v.y - v2.y)));
	}
}
