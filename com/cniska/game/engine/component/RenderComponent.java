package com.cniska.game.engine.component;

import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.system.RenderSystem;
import com.cniska.game.engine.system.SystemRegistry;

/**
 * Render component class file.
 * This class providers the functionality for rendering an entity.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class RenderComponent extends BaseComponent
{
	// ----------
	// Properties
	// ----------

	private SpatialComponent positionComponent;
	private SpriteComponent spriteComponent;

	// -------
	// Methods
	// -------

	/**
	 * Creates the component.
	 */
	public RenderComponent()
	{
		super();
		reset();
		setState(ComponentState.RENDER.ordinal());
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
		positionComponent = null;
		spriteComponent = null;
	}

	/**
	 * Updates the component.
	 * @param parent The parent object.
	 */
	@Override
	public void update(Base parent)
	{
		RenderSystem system = (RenderSystem) SystemRegistry.getSystem(RenderSystem.class);

		if (system != null)
		{
			if (positionComponent != null && spriteComponent != null)
			{
				system.queueForDraw(spriteComponent.getBitmap(), positionComponent.getPosition());
			}
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @param value The associated position component.
	 */
	public void setPositionComponent(SpatialComponent value)
	{
		positionComponent = value;
	}

	/**
	 * @param value The associated sprite component.
	 */
	public void setSpriteComponent(SpriteComponent value)
	{
		spriteComponent = value;
	}
}
