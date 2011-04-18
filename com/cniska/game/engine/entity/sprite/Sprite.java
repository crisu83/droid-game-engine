package com.cniska.game.engine.entity.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Sprite class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class Sprite
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
	public Sprite(Bitmap bitmap)
	{
		this.bitmap = bitmap;
		this.x = 0;
		this.y = 0;
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	}

	/**
	 * Updates the sprite.
	 */
	public void update()
	{
	}

	/**
	 * Renders the sprite.
	 * @param canvas the canvas.
	 */
	public void render(Canvas canvas, int x, int y)
	{
		canvas.drawBitmap(bitmap, x, y, null);
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
