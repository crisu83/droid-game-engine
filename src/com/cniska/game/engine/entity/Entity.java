package com.cniska.game.engine.entity;

import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.base.StatefulCollection;
import com.cniska.game.engine.component.BaseComponent;
import com.cniska.game.engine.util.SortedArrayList;

/**
 * Entity class file.
 * This class represents a single game object and provides functionality for keeping track of its components.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public final class Entity extends Base
{
	// ----------
	// Properties
	// ----------

	private String name;
	private StatefulCollection components;
	private boolean removed = false;

	// -------
	// Methods
	// -------

	/**
	 * Creates the entity.
	 */
	public Entity(String name)
	{
		super();
		this.name = name;
		components = new StatefulCollection();
	}

	/**
	 * Resets this entity.
	 */
	@Override
	public void reset()
	{
		components.reset();
		removed = false;
	}

	/**
	 * Updates this entity and its components.
	 * @param parent The parent object.
	 */
	@Override
	public void update(Base parent)
	{
		components.applyChanges();

		final int componentsCount = components.getSize();

		if (componentsCount > 0)
		{
			SortedArrayList<Base> objects = components.getObjects();
			
			for (int i = 0; i < componentsCount; i++)
			{
				BaseComponent component = (BaseComponent) objects.get(i);

				if (component.getActive())
				{
					component.update(this);
				}
			}
		}
	}

	/**
	 * Adds a component to this entity.
	 * @param component The component to add.
	 */
	public void addComponent(BaseComponent component)
	{
		component.setOwner(this);
		components.add(component);
	}

	/**
	 * Removes a component from this entity.
	 * @param component The component to remove.
	 */
	public void removeComponent(BaseComponent component)
	{
		components.remove(component);
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @return The name of this entity.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return The components that belong to this entity.
	 */
	public StatefulCollection getComponents()
	{
		return components;
	}

	public void setRemoved(boolean removed)
	{
		this.removed = removed;
	}

	public boolean getRemoved()
	{
		return removed;
	}
}
