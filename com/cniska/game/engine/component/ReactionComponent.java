package com.cniska.game.engine.component;

import com.cniska.game.engine.collision.CollisionVolume;
import com.cniska.game.engine.entity.Entity;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public abstract class ReactionComponent extends BaseComponent
{
	public enum CollisionType
	{
		UNKNOWN,
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
	 * @param entity The colliding entity.
	 * @param volume The collision volume.
	 * @param otherEntity The counterpart entity.
	 * @param otherVolume The collision volume for the counterpart.
	 */
	 public abstract void onImpact(Entity entity, CollisionVolume volume,
	                               Entity otherEntity, CollisionVolume otherVolume);

	// -------------
	// Inner classes
	// -------------

	/**
	 * Collision depth class.
	 * This class represents a collision depth on a specific side of the volume being collided into.
	 */
	protected class CollisionDepth
	{
		public CollisionType type;
		public float value;

		public CollisionDepth(float value, CollisionType type)
		{
			this.value = value;
			this.type = type;
		}
	}
}
