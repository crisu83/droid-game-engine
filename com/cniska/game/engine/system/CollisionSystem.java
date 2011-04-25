package com.cniska.game.engine.system;

import android.graphics.Rect;
import android.util.Log;
import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.collision.CollisionVolume;
import com.cniska.game.engine.component.ReactionComponent;
import com.cniska.game.engine.entity.Entity;

import java.util.ArrayList;

/**
 * Collision system class file.
 * This class provides the logic for determining whether entities have collided.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class CollisionSystem extends BaseSystem
{
	// ----------
	// Properties
	// ----------

	private static final CollisionSystem instance = new CollisionSystem();
	private ArrayList<CollisionRecord> records;

	// -------
	// Methods
	// -------

	/**
	 * Creates the system.
	 */
	private CollisionSystem()
	{
		super();
		records = new ArrayList<CollisionRecord>();
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
	 * Registers a new collision volume to the system.
	 * @param volume The collision volume.
	 * @param entity The associated entity.
	 * @param reactionComponent The associated reaction component.
	 */
	public void registerForCollision(CollisionVolume volume, Entity entity, ReactionComponent reactionComponent)
	{
		CollisionRecord record = new CollisionRecord();
		record.volume = volume;
		record.entity = entity;
		record.reactionComponent = reactionComponent;
		records.add(record);
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
		records.clear();
	}

	/**
	 * Updates this system.
	 * @param parent The parent object.
	 */
	// TODO: Support other shapes than rectangles and also turning of shapes.
	@Override
	public void update(Base parent)
	{
		final int recordsCount = records.size();

		if (recordsCount > 0)
		{
			for (int i = 0; i < recordsCount; i++)
			{
				CollisionRecord record = records.get(i);

				if (record.valid)
				{
					for (int j = 0; j < recordsCount; j++)
					{
						CollisionRecord other = records.get(j);

						if (record.entity != other.entity)
						{
							// Currently we only support rectangular shapes.
							if (Rect.intersects(record.volume.getVolume(), other.volume.getVolume()))
							{
								if (record.reactionComponent != null)
								{
									record.reactionComponent.onImpact(record.entity, record.volume,
											other.entity, other.volume);
								}

								if (other.reactionComponent != null)
								{
									other.reactionComponent.onImpact(other.entity, other.volume,
											record.entity, record.volume);
								}

								other.valid = false; // we don't want to process records twice
							}
						}
					}
				}
			}

			reset(); // Reset the system as all possible collisions have been processed.
		}
	}

	// -------------
	// Inner classes
	// -------------

	/**
	 * Collision record class.
	 * This class represents a single record in the collision system.
	 * Records are used for detemining whether a collision has occured.
	 */
	private class CollisionRecord
	{
		public Entity entity;
		public CollisionVolume volume;
		public ReactionComponent reactionComponent;
		public boolean valid = true;
	}
}
