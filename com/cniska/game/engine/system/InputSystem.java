package com.cniska.game.engine.system;

import android.view.MotionEvent;
import com.cniska.game.engine.debug.Logger;
import com.cniska.game.engine.input.InputTouch;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class InputSystem extends BaseSystem
{
	// ----------
	// Properties
	// ----------

	private static final InputSystem instance = new InputSystem();
	private InputTouch touch;

	// -------
	// Methods
	// -------

	/**
	 * Creates the system.
	 */
	private InputSystem()
	{
		super();
		touch = new InputTouch();
		reset();
	}

	/**
	 * Returns the singleton instance of this class.
	 * @return The system.
	 */
	public static InputSystem getInstance()
	{
		return instance;
	}

	/**
	 * Actions to be taken when a touch event is received.
	 * @param event The touch event.
	 */
	public void onTouchEvent(MotionEvent event)
	{
		float x, y;
		int action, actionCode, pointerId;

		for (int i = 0, pointerCount = event.getPointerCount(); i < pointerCount; i++)
		{
			action = event.getAction();
			actionCode = action & MotionEvent.ACTION_MASK;
			pointerId = event.getPointerId(i);
			x = event.getX(i);
			y = event.getY(i);

			switch (actionCode)
			{
				case MotionEvent.ACTION_DOWN: // first touch
				case MotionEvent.ACTION_POINTER_DOWN: // multi touch
					registerTouch(pointerId, x, y);
					break;

				case MotionEvent.ACTION_MOVE: // any touch
					moveTouch(pointerId, x, y);
					break;

				case MotionEvent.ACTION_UP: // first touch
				case MotionEvent.ACTION_POINTER_UP: // multi touch
				case MotionEvent.ACTION_CANCEL: // any touch
					removeTouch(pointerId);
					break;

				default:
			}
		}
	}

	/**
	 * Actions to be taken when touching the screen.
	 * @param pointerId The pointer identifier.
	 * @param x The x-coordinate of the touch.
	 * @param y The y-coordinate of the touch.
	 */
	private void registerTouch(int pointerId, float x, float y)
	{
		touch.addPointer(pointerId, x, y);
	}

	/**
	 * Actions to be taken when moving the finger on the screen.
	 * @param pointerId The pointer identifier.
	 * @param x The x-coordinate of the touch.
	 * @param y The y-coordinate of the touch.
	 */
	private void moveTouch(int pointerId, float x, float y)
	{
		touch.movePointer(pointerId, x, y);
	}

	/**
	 * Actions to be taken when lifting the finger from the screen.
	 * @param pointerId The pointer identifier.
	 */
	private void removeTouch(int pointerId)
	{
		touch.resetPointer(pointerId);
	}

	public void dumpMotionEvent(MotionEvent event)
	{
		String names[] = {
			"DOWN",
			"UP",
			"MOVE",
			"CANCEL",
			"OUTSIDE",
			"POINTER_DOWN",
			"POINTER_UP",
			"7?",
			"8?",
			"9?"
		};

		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		String dump = "event ACTION_" + names[actionCode];
		if (actionCode ==  MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
		{
			dump += "(pid " + action + ")";
		}
		dump += "[";

		for (int i = 0, length = event.getPointerCount(); i < length; i++)
		{
			dump += "#" + i + "(pid " + event.getPointerId(i) + ")=" + event.getX(i) + "," + event.getY(i);

			if ((i + 1) < length)
			{
				dump += ";";
			}
		}

		dump += "]";

		Logger.debug(Logger.TAG_CORE, dump);
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Resets this system.
	 */
	@Override
	public void reset()
	{
		touch.resetAllPointers();
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @return The touch input method.
	 */
	public InputTouch getTouch()
	{
		return touch;
	}
}
