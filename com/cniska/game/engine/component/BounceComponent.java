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
