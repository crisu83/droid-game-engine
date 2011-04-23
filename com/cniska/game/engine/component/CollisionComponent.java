package com.cniska.game.engine.component;

import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.collision.CollisionVolume;
import com.cniska.game.engine.entity.Entity;
import com.cniska.game.engine.system.CollisionSystem;
import com.cniska.game.engine.system.SystemRegistry;
import com.cniska.game.engine.util.Vector;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class CollisionComponent extends BaseComponent
{
	// ----------
	// Properties
	// ----------

	private SpatialComponent spatialComponent;
	private ReactionComponent reactionComponent;

	// -------
	// Methods
	// -------

	/**
	 * Creates the component.
	 */
	public CollisionComponent()
	{
		super();
		reset();
		setState(ComponentState.COLLISION.ordinal());
	}

	// -----------------
	// Overridden method
	// -----------------

	/**
	 * Resets the component.
	 */
	@Override
	public void reset()
	{
		spatialComponent = null;
	}

	/**
	 * Updates the component.
	 * @param parent The parent object.
	 */
	@Override
	public void update(Base parent)
	{
		CollisionSystem system = (CollisionSystem) SystemRegistry.getSystem(CollisionSystem.class);

		if (system != null)
		{
			if (spatialComponent != null) // we do not require a reaction component
			{
				Vector position = spatialComponent.getPosition();
				Vector size = spatialComponent.getSize();

				int left = Math.round(position.x);
				int top = Math.round(position.y);
				int right = left + Math.round(size.x);
				int bottom = top + Math.round(size.y);

				CollisionVolume volume = new CollisionVolume(left, top, right, bottom);

				system.registerForCollision(volume, (Entity) parent, reactionComponent);
			}
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
	 * @param component The associated reaction component.
	 */
	public void setReactionComponent(ReactionComponent component)
	{
		reactionComponent = component;
	}
}
