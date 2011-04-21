package com.cniska.game.engine.entity;

import com.cniska.game.engine.base.StatefulCollection;
import com.cniska.game.engine.component.BaseComponent;

/**
 * Entity class file.
 * This class represents a single game object.
 * Its only functionality is to keep track of its components.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class Entity extends StatefulCollection
{
	// -------
	// Methods
	// -------

	/**
	 * Creates the entity.
	 */
	public Entity()
	{
		super();
	}

	/**
	 * Adds a component to this entity.
	 * @param component The component to add.
	 */
	public void addComponent(BaseComponent component)
	{
		super.add(component);
	}
}
