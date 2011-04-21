package com.cniska.game.engine.component;

import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.util.Vector;

/**
 * Velocity component class file.
 * This class provides the functionality for setting the velocity for entities.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class VelocityComponent extends BaseComponent
{
	// ----------
	// Properties
	// ----------

	private Vector velocity;
	private SpatialComponent positionComponent;

	// -------
	// Methods
	// -------

	/**
	 * Creates the component.
	 */
	public VelocityComponent()
	{
		super();
		velocity = new Vector();
		reset();
		setState(ComponentState.MOVEMENT.ordinal());
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
		velocity.reset();
		positionComponent = null;
	}

	/**
	 * Updates the component.
	 * @param parent The parent object.
	 */
	@Override
	public void update(Base parent)
	{
		if (positionComponent != null)
		{
			Vector position = positionComponent.getPosition();
			float newX = position.x + velocity.x;
			float newY = position.y + velocity.y;
			positionComponent.setPosition(newX, newY);
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * Sets a new velocity.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public void setVelocity(float x, float y)
	{
		velocity.set(x, y);
	}

	/**
	 * @param value The new velocity vector.
	 */
	public void setVelocity(Vector value)
	{
		velocity = value;
	}

	/**
	 * @return The velocity vector.
	 */
	public Vector getVelocity()
	{
		return velocity;
	}

	/**
	 * @param value The associated position component.
	 */
	public void setPositionComponent(SpatialComponent value)
	{
		positionComponent = value;
	}
}
