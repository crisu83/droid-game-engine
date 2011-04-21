package com.cniska.game.engine.system;

import android.graphics.Rect;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class CollisionBox
{
	private Rect box;

	public CollisionBox(int x, int y, int width, int height)
	{
		box = new Rect(x, y, width, height);
	}

	public boolean intersects(Rect other)
	{
		return box.intersect(other);
	}

	public Rect getBox()
	{
		return box;
	}
}
