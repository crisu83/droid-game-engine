package com.cniska.game.engine.collision;

import android.graphics.Rect;

/**
 * Collision volume class file.
 * This class represents a rectangular shaped collision volume.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class CollisionVolume
{
	// ----------
	// Properties
	// ----------

	private Rect volume;

	// -------
	// Methods
	// -------

	/**
	 * Creates the collision volume.
	 * @param left The minimum value on the x-axis.
	 * @param top The minimum value on the y-axis.
	 * @param right The maximum value on the x-axis.
	 * @param bottom The maximum value on the y-axis.
	 */
	public CollisionVolume(int left, int top, int right, int bottom)
	{
		volume = new Rect(left, top, right, bottom);
	}

	/**
	 * @return The minimum value on the x-axis.
	 */
	public int getMinX()
	{
		return volume.left;
	}

	/**
	 * @return The maximum value on the x-axis.
	 */
	public int getMaxX()
	{
		return volume.right;
	}

	/**
	 * @return The minimum value on the y-axis.
	 */
	public int getMinY()
	{
		return volume.bottom;
	}

	/**
	 * @return The maximum value on the y-axis.
	 */
	public int getMaxY()
	{
		return volume.top;
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @return The volume.
	 */
	public Rect getVolume()
	{
		return volume;
	}
}
