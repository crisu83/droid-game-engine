package com.cniska.game.engine;

import android.content.Context;
import com.cniska.game.engine.base.Base;

/**
 * Game parameters class file.
 * Container for parameters passed when creating the game.
 * This class can be accessed through the SystemRegistery.
 * @see com.cniska.game.engine.system.SystemRegistry
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class GameParams
{
	// ----------
	// Properties
	// ----------

	public Context context;
	public int gameWidth;
	public int gameHeight;

	// -------
	// Methods
	// -------

	/**
	 * Creates the parameters.
	 */
	public GameParams()
	{
		super();
	}
}
