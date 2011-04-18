package com.cniska.gameengine.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.cniska.gameengine.CGameEngine;
import com.cniska.gameengine.entity.sprite.CSprite;

/**
 * Core entity class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class CEntity
{
	protected CGameEngine game;
	protected CSprite sprite;

	protected double x;
	protected double y;
	protected int vx;
	protected int vy;

	/**
	 * Creates the entity.
	 * @param bitmap the bitmap to represent this unit.
	 * @param game the game instance.
	 */
	public CEntity(Bitmap bitmap, CGameEngine game)
	{
		this.x = 0;
		this.y = 0;
		this.vx = 0;
		this.vy = 0;

		this.game = game;
		sprite = new CSprite(bitmap);
	}

	/**
	 * Sets the velocity
	 * @param vx
	 * @param vy
	 */
	public void setVelocity(int vx, int vy)
	{
		this.vx = vx;
		this.vy = vy;
	}

	/**
	 * Updates this entity.
	 * @param timePassed the time that has passed.
	 */
	public void update(long timePassed)
	{
		if ((x + getWidth()) == game.getWidth())
		{
			vx = -1;
		}

		if (x == 0)
		{
			vx = 1;
		}

		if ((y + getHeight()) == game.getHeight())
		{
			vy = -1;
		}

		if (y == 0)
		{
			vy = 1;
		}

		if (vx != 0)
		{
			x += vx/* * timePassed*/;
		}

		if (vy != 0)
		{
			y += vy/* * timePassed*/;
		}
	}

	/**
	 * Renders this entity.
	 * @param c the canvas.
	 */
	public void render(Canvas c)
	{
		sprite.render(c, (int) Math.round(x), (int) Math.round(y));
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
