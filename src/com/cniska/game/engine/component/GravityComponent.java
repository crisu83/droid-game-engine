package com.cniska.game.engine.component;

import com.cniska.game.engine.GameParams;
import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.system.SystemRegistry;
import com.cniska.game.engine.util.Vector2;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class GravityComponent extends BaseComponent
{
	private static final float GRAVITY_CONSTANT = 0.1f;

	private GameParams params;
	private float gravity;
	private SpatialComponent spatialComponent;
	private VelocityComponent velocityComponent;

	public GravityComponent()
	{
		super();
		reset();
		params = SystemRegistry.params;
		setState(ComponentState.PHYSICS.ordinal());
	}

	/**
	 * Resets this object.
	 */
	@Override
	public void reset()
	{
		gravity = GRAVITY_CONSTANT;
		spatialComponent = null;
		velocityComponent = null;
	}

	/**
	 * Updates this component.
	 * @param parent The parent object.
	 */
	public void update(Base parent)
	{
		if (spatialComponent != null && velocityComponent != null)
		{
			Vector2 position = spatialComponent.getPosition();
			Vector2 size = spatialComponent.getSize();
			Vector2 velocity = velocityComponent.getVelocity();

			if ((position.y + size.y) >= params.gameHeight)
			{
				position.y = params.gameHeight - size.y;
				velocity.zero();
			}
			else
			{
				velocity.y += gravity;
			}

			velocityComponent.setVelocity(velocity);
		}
	}

	public void setSpatialComponent(SpatialComponent component)
	{
		spatialComponent = component;
	}

	public void setVelocityComponent(VelocityComponent component)
	{
		velocityComponent = component;
	}
}
