package com.cniska.gameengine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.text.DecimalFormat;

/**
 * Game template.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class Template extends CGameEngine
{
	/**
	 * Create the game engine.
	 * @param context the activity.
	 * @param period the period in nanoseconds.
	 */
	public Template(Context context, long period)
	{
		super(context, period);
	}

	/**
	 * Initializes the game.
	 */
	@Override
	public void gameInit()
	{
	}

	/**
	 * Updates the game state.
	 */
	@Override
	public void gameUpdate()
	{
	}

	/**
	 * Renders the game.
	 * @param canvas the canvas.
	 */
	@Override
	public void gameRender(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);

		renderStats(canvas);
	}
}
