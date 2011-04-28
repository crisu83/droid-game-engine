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

	/**
	 * Returns the collision type based on the collision depths for the volume being collided into.
	 * @param depthLeft The collision depth on the left side.
	 * @param depthTop The collision depth on the top side.
	 * @param depthRight The collision depth on the right side.
	 * @param depthBottom The collision depth on the bottom side.
	 * @return The collision type.
	 */
	protected CollisionType resolveCollisionType(float depthLeft, float depthTop, float depthRight, float depthBottom)
	{
		// Create an array for the collision depths to map the values to types.
		final CollisionDepth[] depths = new CollisionDepth[4];
		depths[0] = new CollisionDepth(depthLeft, CollisionType.LEFT);
		depths[1] = new CollisionDepth(depthTop, CollisionType.TOP);
		depths[2] = new CollisionDepth(depthRight, CollisionType.RIGHT);
		depths[3] = new CollisionDepth(depthBottom, CollisionType.BOTTOM);

		CollisionDepth smallest = null;

		// Loop through the depths and figure out which one is closest to zero.
		// The smallest depth equals the collision direction.
		for (int i = 0, length = depths.length; i < length; i++)
		{
			CollisionDepth current = depths[i];

			if (smallest != null)
			{
				if (Math.abs(current.value) < Math.abs(smallest.value))
				{
					smallest = current;
				}
			}
			else
			{
				smallest = current;
			}
		}

		return smallest != null ? smallest.type : CollisionType.UNKNOWN;
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
