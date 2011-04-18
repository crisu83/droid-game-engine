package com.cniska.game.engine.entity;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Entity colleciton class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class EntityCollection
{
	private ArrayList<Entity> entities;

	/**
	 * Creates the entity collection.
	 */
	public EntityCollection()
	{
		entities = new ArrayList<Entity>();
	}

	/**
	 * Adds an entity to this collection.
	 * @param entity the entity to add.
	 */
	public synchronized void addEntity(Entity entity)
	{
		entities.add(entity);
	}

	/**
	 * Removes an entity from this collection
	 * @param entity the entity to remove.
	 */
	public synchronized void removeEntity(Entity entity)
	{
		entities.remove(entity);
	}

	/**
	 * Updates the entities.
	 */
	public void update()
	{
		for (Entity entity : entities)
		{
			entity.update();
		}
	}

	/**
	 * Renders the entities.
	 * @param c the canvas.
	 */
	public void render(Canvas c)
	{
		for (Entity entity : entities)
		{
			entity.render(c);
		}
	}
}
