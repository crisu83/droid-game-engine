package com.cniska.game.engine.component;

import com.cniska.game.engine.collision.CollisionVolume;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public abstract class ReactionComponent extends BaseComponent
{
	public enum OverlapType
	{
		LEFT,
		TOP,
		RIGHT,
		BOTTOM,
	};

	// -------
	// Methods
	// -------

	/**
	 * Creates the component.
	 */
	public ReactionComponent()
	{
		super();
		reset();
		setState(ComponentState.AFTER_COLLISION.ordinal());
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
	}

	// ----------------
	// Abstract methods
	// ----------------

	/**
	 * Actions to be taken when a collision occurs.
	 * @param volume The collision volume.
	 * @param other The counterpart collision volume.
	 */
	 public abstract void onImpact(CollisionVolume volume, CollisionVolume other);
}
