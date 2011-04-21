package com.cniska.game.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Game template.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class Template extends Game
{
	/**
	 * Creates the game.
	 * @param context the context.
	 * @param gameWidth the width of the game.
	 * @param gameHeight the height of the game.
	 * @param period the update period in nano seconds.
	 */
	public Template(Context context, int gameWidth, int gameHeight, long period)
	{
		super(context, gameWidth, gameHeight, period);
	}

	/**
	 * Initializes the game.
	 */
	@Override
	public void init()
	{
	}
}
