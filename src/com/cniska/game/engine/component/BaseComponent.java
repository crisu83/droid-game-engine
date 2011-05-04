package com.cniska.game.engine.component;

import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.base.Stateful;
import com.cniska.game.engine.entity.Entity;

/**
 * Base component class file.
 * All components must be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public abstract class BaseComponent extends Stateful
{
	/**
	 * Component states in which each component can choose to be executed.
	 */
	public static enum ComponentState
	{
		LOGIC,
		PHYSICS,
		MOVEMENT,
		COLLISION,
		AFTER_COLLISION,
		BEFORE_RENDER,
		RENDER,
	}

	// ----------
	// Properties
	// ----------

	private boolean active = true; // components are active by default.
	private Entity owner;

	// -------
	// Methods
	// -------

	/**
	 * Creates the component.
	 */
	public BaseComponent()
	{
		super();
	}
	
	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @param active Whether this component is active.
	 */
	public void setActive(boolean active)
	{
		this.active = active;
	}

	/**
	 * @return Whether this component is active.
	 */
	public boolean getActive()
	{
		return active;
	}

	/**
	 * @param owner The owner of this component.
	 */
	public void setOwner(Entity owner)
	{
		this.owner = owner;
	}

	/**
	 * @return The owner of this component.
	 */
	public Entity getOwner()
	{
		return owner;
	}
}
