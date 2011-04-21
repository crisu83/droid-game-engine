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

	private SpatialComponent spartialComponent;
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
		spartialComponent = null;
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
			if (spartialComponent != null && spriteComponent != null)
			{
				system.queueForDraw(spriteComponent.getBitmap(), spartialComponent.getPosition());
			}
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @param value The associated spatial component.
	 */
	public void setSpartialComponent(SpatialComponent value)
	{
		spartialComponent = value;
	}

	/**
	 * @param value The associated sprite component.
	 */
	public void setSpriteComponent(SpriteComponent value)
	{
		spriteComponent = value;
	}
}
