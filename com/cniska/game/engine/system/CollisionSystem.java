package com.cniska.game.engine.system;

import com.cniska.game.engine.base.Base;

import java.util.ArrayList;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class CollisionSystem extends BaseSystem
{
	// ----------
	// Properties
	// ----------

	private static final CollisionSystem instance = new CollisionSystem();
	private ArrayList<CollisionBox> boxes;

	// -------
	// Methods
	// -------

	/**
	 * Creates the system.
	 */
	private CollisionSystem()
	{
		super();
		reset();
	}

	/**
	 * Returns the singleton instance of this class.
	 * @return The system.
	 */
	public static CollisionSystem getInstance()
	{
		return instance;
	}

	/**
	 * Registers an collision box in the system.
	 * @param box The collision box.
	 */
	public void registerForCollision(CollisionBox box)
	{
		boxes.add(box);
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Resets this system.
	 */
	@Override
	public void reset()
	{
		boxes = new ArrayList<CollisionBox>();
	}

	/**
	 * Updates this system.
	 * @param parent The parent object.
	 */
	@Override
	public void update(Base parent)
	{
		synchronized (this)
		{
			if (boxes.size() > 0)
			{
				for (CollisionBox box : boxes)
				{
					for (CollisionBox other : boxes)
					{
						if (box.getOwner() != other.getOwner())
						{
							if (box.intersects(other.getBox()))
							{
								if (true)
								{
									// Collision!
								}
							}
						}
					}
				}

				reset(); // Reset the system as all possible collisions have been processed.
			}
		}
	}
}
