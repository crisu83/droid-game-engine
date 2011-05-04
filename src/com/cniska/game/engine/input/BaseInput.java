package com.cniska.game.engine.input;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public abstract class BaseInput
{
	// ----------
	// Properties
	// ----------

	private boolean active = false;

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @param active Whether this input is active.
	 */
	public void setActive(boolean active)
	{
		this.active = active;
	}

	/**
	 * @return Whether this input is active.
	 */
	public boolean getActive()
	{
		return active;
	}
}
