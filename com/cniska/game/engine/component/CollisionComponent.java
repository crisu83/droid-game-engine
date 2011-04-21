package com.cniska.game.engine.component;

import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.system.CollisionBox;
import com.cniska.game.engine.system.CollisionSystem;
import com.cniska.game.engine.system.SystemRegistry;
import com.cniska.game.engine.util.Vector;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class CollisionComponent extends BaseComponent
{
	private SpatialComponent spatialComponent;

	public CollisionComponent()
	{
		super();
		reset();
		setState(ComponentState.COLLISION.ordinal());
	}

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
			if (spatialComponent != null)
			{
				Vector position = spatialComponent.getPosition();
				Vector size = spatialComponent.getSize();

				system.registerForCollision(new CollisionBox(Math.round(position.x), Math.round(position.y),
						Math.round(size.x), Math.round(size.y)));
			}
		}
	}

	public void setSpatialComponent(SpatialComponent component)
	{
		spatialComponent = component;
	}
}
