package com.cniska.game.engine.base;

/**
 * Stateful object class file.
 * This class provides functionality for updating objects in a specific state.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public abstract class Stateful extends Base
{
	// ----------
	// Properties
	// ----------

	private int state;

	// -------
	// Methods
	// -------

	/**
	 * Creates the stateful object.
	 */
	public Stateful()
	{
		super();
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @param state The state that this object is updated in.
	 */
	public void setState(int state)
	{
		this.state = state;
	}

	/**
	 * @return The state that this object is updated in.
	 */
	public int getState()
	{
		return state;
	}
}
