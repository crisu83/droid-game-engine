package com.cniska.game.engine.component;

import com.cniska.game.engine.collision.CollisionVolume;
import com.cniska.game.engine.util.Util;
import com.cniska.game.engine.util.Vector;

import java.util.ArrayList;

/**
 * Bouce component class file.
 * This class is a reaction component that provides functionality for responding to a collision by bouncing back.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class BounceComponent extends ReactionComponent
{
	// ----------
	// Properties
	// ----------

	private static ArrayList<Overlap> overlaps = new ArrayList<Overlap>();
	private SpatialComponent spatialComponent;
	private VelocityComponent velocityComponent;

	/**
	 * Creates the component.
	 */
	public BounceComponent()
	{
		super();
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Actions to be taken when a collision occurs.
	 * @param volume The collision volume.
	 * @param other The counterpart collision volume.
	 */
	@Override
	public void onImpact(CollisionVolume volume, CollisionVolume other)
	{
		if (spatialComponent != null && velocityComponent != null)
		{
			Vector position = spatialComponent.getPosition();
			Vector size = spatialComponent.getSize();
			Vector velocity = velocityComponent.getVelocity();

			float left = volume.getMaxX() - other.getMinX();
			float top = volume.getMaxY() - other.getMinY();
			float right = volume.getMinX() - other.getMaxX();
			float bottom = volume.getMinY() - other.getMaxY();

			OverlapType type = resolveOverlapType(left, top, right, bottom);

			switch (type)
			{
				case LEFT:
					position.x = other.getMinX() - size.x -  1;
					velocity.x = Util.invert(velocity.x);
					break;

				case TOP:
					position.y = other.getMinY() - size.y - 1;
					velocity.y = Util.invert(velocity.y);
					break;

				case RIGHT:
					position.x = other.getMaxX() + 1;
					velocity.x = Util.invert(velocity.x);
					break;

				case BOTTOM:
					position.y = other.getMaxY() + 1;
					velocity.y = Util.invert(velocity.y);
					break;

				default:
					// This should never happen.
			}

			spatialComponent.setPosition(position);
			velocityComponent.setVelocity(velocity);
		}
	}

	private OverlapType resolveOverlapType(float left, float top, float right, float bottom)
	{
		overlaps.clear();
		overlaps.add(new Overlap(left, OverlapType.LEFT));
		overlaps.add(new Overlap(top, OverlapType.TOP));
		overlaps.add(new Overlap(right, OverlapType.RIGHT));
		overlaps.add(new Overlap(bottom, OverlapType.BOTTOM));

		Overlap overlap = null;
		for (int i = 0, length = overlaps.size(); i < length; i++)
		{
			Overlap current = overlaps.get(i);

			if (overlap != null)
			{
				if (Math.abs(current.value) < Math.abs(overlap.value))
				{
					overlap = current;
				}
			}
			else
			{
				overlap = current;
			}
		}

		return overlap.type;
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @param component The associated spatial component.
	 */
	public void setSpatialComponent(SpatialComponent component)
	{
		spatialComponent = component;
	}

	/**
	 * @param component The associated velocity component.
	 */
	public void setVelocityComponent(VelocityComponent component)
	{
		velocityComponent = component;
	}

	private class Overlap
	{
		public OverlapType type;
		public float value;

		public Overlap(float value, OverlapType type)
		{
			this.value = value;
			this.type = type;
		}
	}
}
