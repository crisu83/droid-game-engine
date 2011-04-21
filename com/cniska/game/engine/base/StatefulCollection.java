package com.cniska.game.engine.base;

import java.util.Comparator;

/**
 * 
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class StatefulCollection extends BaseCollection
{
	// ----------
	// Properties
	// ----------

	private boolean sorted = false;
	private final static StateComparator stateComparator = new StateComparator();

	// -------
	// Methods
	// -------

	public StatefulCollection()
	{
		super();
		getObjects().setComparator(stateComparator);
		getAdditions().setComparator(stateComparator);
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Adds an object to this collection.
	 * @param object The object to add.
	 */
	@Override
	public void add(Base object)
	{
		if (object instanceof Stateful)
		{
			super.add(object);
			sorted = false;
		}
	}

	/**
	 * Applies the pending additions and removals to this collections.
	 */
	@Override
	public void applyChanges()
	{
		super.applyChanges();

		if (!sorted)
		{
			getObjects().sort();
			sorted = true;
		}
	}

	// -------------
	// Inner classes
	// -------------

	/**
	 * State comparator class.
	 * This class provides functionality for comparing two objects based on their state.
	 */
	private static class StateComparator implements Comparator<Base>
	{
		/**
		 * Compares two stateful objects based on their state.
		 * @param object1 The object to compare.
		 * @param object2 The object to compare with.
		 * @return The result.
		 */
		@Override
		public int compare(Base object1, Base object2)
		{
			int result = 0;

			if (object1 != null && object2 != null)
			{
				result = ((Stateful) object1).getState() - ((Stateful) object2).getState();
			}
			else if (object1 == null && object2 != null)
			{
				return 1;
			}
			else if (object2 == null && object1 != null)
			{
				return -1;
			}

			return result;
		}
	}
}
