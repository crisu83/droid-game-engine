package com.cniska.game.engine.system;

import android.graphics.Rect;
import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.util.SortedArrayList;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class CollisionSystem extends BaseSystem
{
	private SortedArrayList<CollisionBox> boxes;

	public CollisionSystem()
	{
		super();
		reset();
	}

	public void reset()
	{
		boxes = new SortedArrayList<CollisionBox>();
	}

	public void registerForCollision(CollisionBox box)
	{
		boxes.add(box);
	}

	public void update(Base parent)
	{
		// TODO: Run the logic to determine collision

		reset();
	}
}
