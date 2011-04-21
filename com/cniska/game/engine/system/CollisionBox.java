package com.cniska.game.engine.system;

import android.graphics.Rect;
import com.cniska.game.engine.entity.Entity;
import org.apache.http.entity.EntityTemplate;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class CollisionBox
{
	private Rect box;
	private Entity owner;

	public CollisionBox(int x, int y, int width, int height)
	{
		box = new Rect(x, y, width, height);
	}

	public boolean intersects(Rect other)
	{
		return box.intersect(other);
	}

	public Rect getBox()
	{
		return box;
	}

	public Entity getOwner()
	{
		return owner;
	}

	public void setOwner(Entity owner)
	{
		this.owner = owner;
	}
}
