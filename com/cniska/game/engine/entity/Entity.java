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
public class Entity extends Base
{
	// ----------
	// Properties
	// ----------

	private StatefulCollection components;

	// -------
	// Methods
	// -------

	/**
	 * Creates the entity.
	 */
	public Entity()
	{
		super();
		components = new StatefulCollection();
	}

	/**
	 * Resets this entity.
	 */
	@Override
	public void reset()
	{
		components.reset();
	}

	/**
	 * Updates this entity and its components.
	 * @param parent The parent object.
	 */
	@Override
	public void update(Base parent)
	{
		components.applyChanges();

		SortedArrayList<Base> objects = components.getObjects();

		for (Base object : objects)
		{
			if (((BaseComponent) object).getActive())
			{
				object.update(this);
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
}
