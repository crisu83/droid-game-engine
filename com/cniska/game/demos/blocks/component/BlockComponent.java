package com.cniska.game.demos.blocks.component;

import com.cniska.game.engine.GameParams;
import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.component.BaseComponent;
import com.cniska.game.engine.component.SpatialComponent;
import com.cniska.game.engine.component.SpriteComponent;
import com.cniska.game.engine.component.VelocityComponent;
import com.cniska.game.engine.system.SystemRegistry;
import com.cniska.game.engine.util.Vector;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class BlockComponent extends BaseComponent
{
	// ----------
	// Properties
	// ----------

	private GameParams params;
	private SpatialComponent spatialComponent;
	private SpriteComponent spriteComponent;
	private VelocityComponent velocityComponent;

	// -------
	// Methods
	// -------

	/**
	 * Creates the component.
	 */
	public BlockComponent()
	{
		params = SystemRegistry.params;
		setState(ComponentState.LOGIC.ordinal());
	}

	// ----------------
	// Abstract methods
	// ----------------

	/**
	 * Resets the component.
	 */
	@Override
	public void reset()
	{
		spatialComponent = null;
		spriteComponent = null;
		velocityComponent = null;
	}

	/**
	 * Updates the component.
	 * @param parent The parent object.
	 */
	@Override
	public void update(Base parent)
	{
		if (spatialComponent != null && spriteComponent != null && velocityComponent != null)
		{
			Vector position = spatialComponent.getPosition();
			Vector size = spatialComponent.getSize();
			Vector velocity = velocityComponent.getVelocity();

			if ((position.x + size.x) >= params.gameWidth)
			{
				position.x = params.gameWidth - size.x;
				velocity.x = -Math.abs(velocity.x);
			}

			if (position.x <= 0)
			{
				position.x = 0;
				velocity.x = Math.abs(velocity.x);
			}

			if ((position.y + size.y) >= params.gameHeight)
			{
				position.y = params.gameHeight - size.y;
				velocity.y = -Math.abs(velocity.y);
			}

			if (position.y <= 0)
			{
				position.y = 0;
				velocity.y = Math.abs(velocity.y);
			}

			spatialComponent.setPosition(position);
			velocityComponent.setVelocity(velocity);
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

	public void setSpatialComponent(SpatialComponent component)
	{
		spatialComponent = component;
	}

	public void setSpriteComponent(SpriteComponent component)
	{
		spriteComponent = component;
	}

	public void setVelocityComponent(VelocityComponent component)
	{
		velocityComponent = component;
	}
}
