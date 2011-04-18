package com.cniska.game.engine.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;
import com.cniska.game.engine.entity.sprite.Sprite;

/**
 * Entity class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class Entity
{
	protected SurfaceView view;
	
	protected Sprite sprite;

	protected double x;
	protected double y;
	protected double vx;
	protected double vy;

	/**
	 * Creates the entity.
	 * @param bitmap the bitmap to represent this unit.
	 * @param view the view this entity exists in.
	 */
	public Entity(Bitmap bitmap, SurfaceView view)
	{
		this.x = 0;
		this.y = 0;
		this.vx = 0;
		this.vy = 0;

		this.view = view;
		sprite = new Sprite(bitmap);
	}

	/**
	 * Sets the velocity
	 * @param vx the velocity on the x-axis.
	 * @param vy the velocity on the y-axis.
	 */
	public void setVelocity(int vx, int vy)
	{
		this.vx = vx;
		this.vy = vy;
	}

	/**
	 * Updates this entity.
	 */
	public void update()
	{
		if ((x + getWidth()) == view.getWidth())
		{
			vx = -1;
		}

		if (x == 0)
		{
			vx = 1;
		}

		if ((y + getHeight()) == view.getHeight())
		{
			vy = -1;
		}

		if (y == 0)
		{
			vy = 1;
		}

		if (vx != 0)
		{
			x += vx;
		}

		if (vy != 0)
		{
			y += vy;
		}
	}

	/**
	 * Renders this entity.
	 * @param canvas the canvas.
	 */
	public void render(Canvas canvas)
	{
		sprite.render(canvas, (int) Math.round(x), (int) Math.round(y));
	}

	/**
	 * Returns the width of this entity.
	 * @return the width.
	 */
	public int getWidth()
	{
		return sprite.getWidth();
	}

	/**
	 * Returns the height of this entity.
	 * @return the height.
	 */
	public int getHeight()
	{
		return sprite.getHeight();
	}
}
