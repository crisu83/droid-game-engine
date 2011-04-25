package com.cniska.game.engine.component;

import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.util.Vector;

/**
 * Spatial component class file.
 * This class providers the functionality for positioning entities.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class SpatialComponent extends BaseComponent
{
	// ----------
	// Properties
	// ----------

	private Vector position;
	private Vector size;

	// -------
	// Methods
	// -------

	/**
	 * Creates the component.
	 */
	public SpatialComponent()
	{
		super();
		position = new Vector();
		size = new Vector();
		reset();
		setState(ComponentState.BEFORE_RENDER.ordinal());
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Resets the component.
	 */
	@Override
	public void reset()
	{
		position.reset();
		size.reset();
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * Sets a new position.
	 * @param x The new x-coordinate.
	 * @param y The new y-coordinate.
	 */
	public void setPosition(float x, float y)
	{
		position.set(x, y);
	}

	/**
	 * @param value The position vector.
	 */
	public void setPosition(Vector value)
	{
		position = value;
	}

	/**
	 *
	 * @return The size vector.
	 */
	public Vector getPosition()
	{
		return position;
	}

	/**
	 * Sets a new size.
	 * @param width The new width.
	 * @param height The new height.
	 */
	public void setSize(float width, float height)
	{
		size.set(width, height);
	}

	/**
	 * @param value The size vector.
	 */
	public void setSize(Vector value)
	{
		size = value;
	}

	/**
	 *
	 * @return The size vector.
	 */
	public Vector getSize()
	{
		return size;
	}
}
