package com.cniska.game.engine.input;

import com.cniska.game.engine.util.Vector2;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class InputTouchPointer extends BaseInput
{
	// ----------
	// Properties
	// ----------

	private Vector2 initialPosition;
	private Vector2 position;
	private boolean moved = false;

	// -------
	// Methods
	// -------

	/**
	 * Creates the touch pointer.
	 */
	public InputTouchPointer()
	{
		initialPosition = new Vector2();
		position = new Vector2();
	}

	/**
	 * Resets this pointer.
	 */
	public void reset()
	{
		initialPosition.zero();
		position.zero();
		moved = false;
		setActive(false);
	}

	/**
	 * Sets the original position of this pointer.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public void setInitialPosition(float x, float y)
	{
		initialPosition.set(x, y);
		setPosition(x, y);
		setActive(true);
	}

	/**
	 * Sets a new position for this pointer.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public void setPosition(float x, float y)
	{
		position.set(x, y);
		moved = true;
	}

	/**
	 * @return The current position of this pointer.
	 */
	public Vector2 getPosition()
	{
		return position;
	}

	/**
	 * @return Whether this pointer has been moved.
	 */
	public boolean getMoved()
	{
		return moved;
	}
}
