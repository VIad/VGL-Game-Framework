package vgl.maths;

//============================================================================
//Name        : Maths
//Author      : Vladimir Ivanov
//Version     : 1.0
//Copyright   : MIT License Copyright (c) 2018 
//Description : Class for doing basic mathematical functions, useful in games
//============================================================================


public class Maths {

	public static final double	PI			= Math.PI;
	public static final double	QUARTER_PI	= PI / 4;
	public static final double	HALF_PI		= PI / 2;

	private Maths() {}

	public static float clamp(float number, float max, float min) {
		return number > max ? max : number < min ? min : number;
	}

	public static float max(float n1, float n2) {
		return n1 >= n2 ? n1 : n2;
	}
	
	public static int max(int n1, int n2) {
		return n1 >= n2 ? n1 : n2;
	}

	public static float min(float n1, float n2) {
		return n1 <= n2 ? n1 : n2;
	}

	public static float exp(float num) {
		return num - (int) num;
	}

	public static double average(double first, double... rest) {
		int c = rest.length + 1;
		if (c == 0)
			return first;
		double sum = first;
		for (double d : rest) {
			sum += d;
		}
		if (sum == 0)
			return 0;
		return sum / c;
	}

}
