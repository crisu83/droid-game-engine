package com.cniska.game.engine.input;

import com.cniska.game.engine.debug.Logger;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class InputTouch
{
	// ----------
	// Properties
	// ----------

	private final int MAX_TOUCH_POINTS = 5;
	private InputTouchPointer pointers[];

	// -------
	// Methods
	// -------

	/**
	 * Creates the input method.
	 */
	public InputTouch()
	{
		pointers = new InputTouchPointer[MAX_TOUCH_POINTS];

		for (int i = 0; i < MAX_TOUCH_POINTS; i++)
		{
			pointers[i] = new InputTouchPointer();
		}
	}

	/**
	 * Simulates touching the screen.
	 * @param index The index for the touch.
	 * @param x The x-coordinate of the touch.
	 * @param y The y-coordinate of the touch.
	 */
	public void addPointer(int index, float x, float y)
	{
		if (index < MAX_TOUCH_POINTS)
		{
			pointers[index].setInitialPosition(x, y);
		}
	}

	/**
	 * Simulates moving the finger on the screen.
	 * @param index The index for the touch.
	 * @param x The x-coordinate of the touch.
	 * @param y The y-coordinate of the touch.
	 */
	public void movePointer(int index, float x, float y)
	{
		if (index < MAX_TOUCH_POINTS)
		{
			pointers[index].setPosition(x, y);
		}
	}

	/**
	 * Simulates lifting a specific finger from the screen.
	 * @param index The index for the touch.
	 */
	public void resetPointer(int index)
	{
		if (index < MAX_TOUCH_POINTS)
		{
			pointers[index].reset();
		}
	}

	/**
	 * Simulates lifting of all fingers from the screen.
	 */
	public void resetAllPointers()
	{
		for (int i = 0; i < MAX_TOUCH_POINTS; i++)
		{
			pointers[i].reset();
		}
	}

	/**
	 * @return The active pointers.
	 */
	public InputTouchPointer[] getActivePointers()
	{
		InputTouchPointer[] activePointers = new InputTouchPointer[MAX_TOUCH_POINTS];

		for (int i = 0; i < MAX_TOUCH_POINTS; i++)
		{
			if (pointers[i].getActive())
			{
				activePointers[i] = pointers[i];
			}
		}

		return activePointers;
	}
}
