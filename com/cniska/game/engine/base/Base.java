package com.cniska.game.engine.base;

/**
 * Base object class file.
 * All classes that require an update should be extened from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public abstract class Base
{
	// ----------------
	// Abstract methods
	// ----------------

	/**
	 * Updates this object.
	 * @param parent The parent object.
	 */
	public void update(Base parent)
	{
		// Base class does nothing.
	}

	/**
	 * Resets this object.
	 */
	public abstract void reset();
}
