package com.cniska.game.engine.component;

import com.cniska.game.engine.collision.CollisionVolume;
import com.cniska.game.engine.entity.Entity;
import com.cniska.game.engine.util.Util;
import com.cniska.game.engine.util.Vector;

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
	 * @param volume The collision volume.
	 * @param other The counterpart collision volume.
	 */
	@Override
	public void onImpact(CollisionVolume volume, CollisionVolume other)
	{
		if (spatialComponent != null && velocityComponent != null)
		{
			Vector position = spatialComponent.getPosition();
			Vector size = spatialComponent.getSize();
			Vector velocity = velocityComponent.getVelocity();

			// Collision from the left.
			if (volume.getMinX() >= other.getMinX() && isCollisionHorizonal(volume, other))
			{
				//position.x = other.getMaxX();
				velocity.x = Util.invert(velocity.x);
			}
			// Collision from above.
			else if (volume.getMinY() <= other.getMinY() && isCollisionVertical(volume, other))
			{
				//position.y = other.getMinY();
				velocity.y = Util.invert(velocity.y);
			}
			// Collision from the right.
			else if (volume.getMaxX() <= other.getMaxX() && isCollisionHorizonal(volume, other))
			{
				//position.x = other.getMinX() - size.x;
				velocity.x = Util.invert(velocity.x);
			}
			// Collision from below.
			else if (volume.getMaxY() >= other.getMaxY() && isCollisionVertical(volume, other))
			{
				//position.y = other.getMaxY() - size.y;
				velocity.y = Util.invert(velocity.y);
			}

			spatialComponent.setPosition(position);
			velocityComponent.setVelocity(velocity);
		}
	}

	private boolean isCollisionHorizonal(CollisionVolume v1, CollisionVolume v2)
	{
		return v1.getMinY() < v2.getMaxY() && v1.getMaxY() > v2.getMinY();
	}

	private boolean isCollisionVertical(CollisionVolume v1, CollisionVolume v2)
	{
		return v1.getMinX() < v2.getMaxX() && v1.getMaxX() > v2.getMinX();
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
