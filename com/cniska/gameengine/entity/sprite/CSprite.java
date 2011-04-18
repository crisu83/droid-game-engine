package com.cniska.gameengine.entity.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Core sprite class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class CSprite
{
	protected Bitmap bitmap;

	protected int x;
	protected int y;
	protected int width;
	protected int height;

	/**
	 * Creates the sprite.
	 * @param bitmap the bitmap that represents the sprite.
	 */
	public CSprite(Bitmap bitmap)
	{
		this.bitmap = bitmap;
		this.x = 0;
		this.y = 0;
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	}

	/**
	 * Updates the sprite.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
	}

	/**
	 * Renders the sprite.
	 * @param c the canvas.
	 */
	public void render(Canvas c, int x, int y)
	{
		c.drawBitmap(bitmap, x, y, null);
	}

	/**
	 * Returns the width of this sprite.
	 * @return the width.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Returns the height of this sprite.
	 * @return the height.
	 */
	public int getHeight()
	{
		return height;
	}
}
