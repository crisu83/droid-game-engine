package com.cniska.game.engine.component;

import com.cniska.game.engine.base.Base;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class PhysicsComponent extends BaseComponent
{
	// ----------
	// Properties
	// ----------

	public static final float DEFAULT_MASS = 1.0f; // ~10kg
	public static final float DEFAULT_INTERNIA = 0.01f;
	public static final float DEFAULT_STATIC_FRICTION = 0.05f;
	public static final float DEFAULT_KINETIC_FRICTION = 0.02f;

	private float mass;
	private float internia; // resistance of the object to a change in its state of motion
	private float staticFriction;
	private float kineticFriction;

	private VelocityComponent velocityComponent;

	// -------
	// Methods
	// -------

	/**
	 * Creates the component.
	 */
	public PhysicsComponent()
	{
		super();
		reset();
		setState(ComponentState.PHYSICS.ordinal());
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Resets this object.
	 */
	@Override
	public void reset()
	{
		mass = DEFAULT_MASS;
		internia = DEFAULT_INTERNIA;
		staticFriction = DEFAULT_STATIC_FRICTION;
		kineticFriction = DEFAULT_KINETIC_FRICTION;
		velocityComponent = null;
	}

	/**
	 * Updates the component.
	 * @param parent The parent object.
	 */
	public void update(Base parent)
	{
		// TODO: Implement mass, internia, friction etc.
	}

	// -------------------
	// Getters and setters
	// -------------------

	public void setMass(float mass)
	{
		this.mass = mass;
	}

	public void setInternia(float internia)
	{
		this.internia = internia;
	}

	public void setStaticFriction(float koefficient)
	{
		staticFriction = koefficient;
	}

	public void setKineticFriction(float koefficient)
	{
		kineticFriction = koefficient;
	}

	public void setVelocityComponent(VelocityComponent component)
	{
		velocityComponent = component;
	}
}
