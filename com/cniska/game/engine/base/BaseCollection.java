package com.cniska.game.engine.base;

import com.cniska.game.engine.util.SortedArrayList;

/**
 * Base collection class file.
 * This class provides basic functionality for object collections.
 * All collections should be extended from this class.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class BaseCollection extends Base
{
	// ----------
	// Properties
	// ----------

	private SortedArrayList<Base> objects;
	private SortedArrayList<Base> additions;
	private SortedArrayList<Base> removals;

	// -------
	// Methods
	// -------

	/**
	 * Creates the collection.
	 */
	public BaseCollection()
	{
		super();
		objects = new SortedArrayList<Base>();
		additions = new SortedArrayList<Base>();
		removals = new SortedArrayList<Base>();
	}

	/**
	 * Adds an object to this collection.
	 * @param object The object to add.
	 */
	public void add(Base object)
	{
		additions.add(object);
	}

	/**
	 * Removes an object from this collection.
	 * @param object The object to remove.
	 */
	public void remove(Base object)
	{
		removals.add(object);
	}

	/**
	 * Returns the size of this collection.
	 * @return The size.
	 */
	public final int getSize()
	{
		return objects.size();
	}

	/**
	 * Returns the size of this collection after the changes have been applied.
	 * @return The size.
	 */
	public final int getRealSize()
	{
		return objects.size() + additions.size() - removals.size();
	}

	/**
	 * Applies the pending additions and removals to this collections.
	 */
	public void applyChanges()
	{
		if (additions.size() > 0)
		{
			for (Base object : additions)
			{
				objects.add(object);
			}

			additions.clear();
		}

		if (removals.size() > 0)
		{
			for (Base object : removals)
			{
				objects.remove(object);
			}

			removals.clear();
		}
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Updates the entities managed by this manager.
	 */
	@Override
	public void update(Base parent)
	{
		applyChanges();

		if (objects.size() > 0)
		{
			for (Base object : objects)
			{
				object.update(this);
			}
		}
	}

	/**
	 * Resets the collection.
	 */
	@Override
	public void reset()
	{
		applyChanges();

		if (objects.size() > 0)
		{
			for (Base object : objects)
			{
				object.reset();
			}
		}
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @return The objects in this collection.
	 */
	public SortedArrayList<Base> getObjects()
	{
		return objects;
	}

	/**
	 * @return The objects pending to be added.
	 */
	public SortedArrayList<Base> getAdditions()
	{
		return additions;
	}

	/**
	 * Finds an object in this collection by its class.
	 * @param type The class of the object to find.
	 * @return The object or null if not found.
	 */
	public Base findByClass(Class<?> type)
	{
		for (Base object : objects)
		{
			if (object.getClass() == type)
			{
				return object;
			}
		}

		return null;
	}
}
