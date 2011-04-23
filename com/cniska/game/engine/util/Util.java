package com.cniska.game.engine.util;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class Util
{
	public static int invert(int value)
	{
		return value > 0 ? -Math.abs(value) : Math.abs(value);
	}

	public static float invert(float value)
	{
		return value > 0.0f ? -Math.abs(value) : Math.abs(value);
	}
}
