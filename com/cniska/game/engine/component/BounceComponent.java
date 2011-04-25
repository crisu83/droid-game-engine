package com.cniska.game.engine.component;

import android.os.Debug;
import com.cniska.game.engine.collision.CollisionVolume;
import com.cniska.game.engine.debug.Logger;
import com.cniska.game.engine.entity.Entity;
import com.cniska.game.engine.util.Util;
import com.cniska.game.engine.util.Vector;

import java.util.ArrayList;

/**
 * Bouce component class file.
 * This class is a reaction component that provides functionality for responding to a collision by bouncing back.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class BounceComponent extends ReactionComponent
{
	// ----------
	// Properties
	// ----------

	private SpatialComponent spatialComponent;
	private VelocityComponent velocityComponent;

	/**
	 * Creates the component.
	 */
	public BounceComponent()
	{
		super();
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Actions to be taken when a collision occurs.
	 * @param entity The colliding entity.
	 * @param volume The collision volume.
	 * @param otherEntity The counterpart entity.
	 * @param otherVolume The collision volume for the counterpart.
	 */
	public void onImpact(Entity entity, CollisionVolume volume, Entity otherEntity, CollisionVolume otherVolume)
	{
		if (spatialComponent != null && velocityComponent != null)
		{
			Vector position = spatialComponent.getPosition();
			Vector size = spatialComponent.getSize();
			Vector velocity = velocityComponent.getVelocity();

			float depthLeft = volume.getMaxX() - otherVolume.getMinX();
			float depthRight = volume.getMinX() - otherVolume.getMaxX();
			float depthTop = volume.getMaxY() - otherVolume.getMinY();
			float depthBottom = volume.getMinY() - otherVolume.getMaxY();

			CollisionType type = resolveCollisionType(depthLeft, depthTop, depthRight, depthBottom);

			switch (type)
			{
				// Collision from the left.
				case LEFT:
					Logger.debug(Logger.TAG_CORE, entity.getName() + " collided into " + otherEntity.getName()
							+ " from the left.");
					position.x = otherVolume.getMinX() - size.x - 0.5f;

					if (velocity.x > 0.0f)
					{
						velocity.x = velocity.x * -1.0f;
					}

					break;

				// Collision from above.
				case TOP:
					Logger.debug(Logger.TAG_CORE, entity.getName() + " collided into " + otherEntity.getName()
							+ " from above.");
					position.y = otherVolume.getMinY() - size.y - 0.5f;

					if (velocity.y > 0.0f)
					{
						velocity.y = velocity.y * -1.0f;
					}

					break;

				// Collision from the right.
				case RIGHT:
					Logger.debug(Logger.TAG_CORE, entity.getName() + " collided into " + otherEntity.getName()
							+ " from the right.");
					position.x = otherVolume.getMaxX() + 0.5f;

					if (velocity.x < 0.0f)
					{
						velocity.x = velocity.x * -1.0f;
					}

					break;

				// Collision from below.
				case BOTTOM:
					Logger.debug(Logger.TAG_CORE, entity.getName() + " collided into " + otherEntity.getName()
							+ " from below.");
					position.y = otherVolume.getMaxY() + 0.5f;

					if (velocity.y < 0.0f)
					{
						velocity.y = velocity.y * -1.0f;
					}

					break;

				// Unknown direction.
				case UNKNOWN:
				default:
					// This should never happen.
					Logger.error(Logger.TAG_CORE, entity.getName() + " collision into " + otherEntity.getName()
							+ " from an unknown direction!");

					// We can safely fall through because nothing will be changed.
			}

			spatialComponent.setPosition(position);
			velocityComponent.setVelocity(velocity);
		}
	}

	/**
	 * Returns the collision type based on the collision depths for the volume being collided into.
	 * @param depthLeft The collision depth on the left side.
	 * @param depthTop The collision depth on the top side.
	 * @param depthRight The collision depth on the right side.
	 * @param depthBottom The collision depth on the bottom side.
	 * @return The collision type.
	 */
	private CollisionType resolveCollisionType(float depthLeft, float depthTop, float depthRight, float depthBottom)
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

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @param component The associated spatial component.
	 */
	public void setSpatialComponent(SpatialComponent component)
	{
		spatialComponent = component;
	}

	/**
	 * @param component The associated velocity component.
	 */
	public void setVelocityComponent(VelocityComponent component)
	{
		velocityComponent = component;
	}
}
