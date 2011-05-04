package com.cniska.game.engine.pool;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class PoolArray
{
	private int size;
	private int lastIndex;
	private Poolable[] pool;

	public PoolArray(int size)
	{
		this.size = size;
		lastIndex = 0;
		pool = new Poolable[size];
	}

	public Poolable pop()
	{
		Poolable item = null;

		if (lastIndex > 0)
		{
			item = pool[lastIndex];
			pool[lastIndex] = null;
			lastIndex--;
		}
		else
		{
			// Do something...
		}

		return item;
	}

	public boolean push(Poolable item)
	{
		if (lastIndex < (size - 1))
		{
			pool[lastIndex] = item;
			lastIndex++;
			return true;
		}
		else
		{
			// Do something...
		}

		return false;
	}

	public int getSize()
	{
		return size;
	}
}
