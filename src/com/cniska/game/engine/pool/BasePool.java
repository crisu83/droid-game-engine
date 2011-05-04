package com.cniska.game.engine.pool;

import com.cniska.game.engine.util.Stack;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public abstract class BasePool
{
	private static final int DEFAULT_POOL_SIZE = 32;
	private Stack pool;

	public BasePool()
	{
		this(DEFAULT_POOL_SIZE);
	}

	public BasePool(int size)
	{
		pool = new Stack(size);
		fill();
	}

	public Poolable allocate()
	{
		return (Poolable) pool.pop();
	}

	public void release(Poolable item)
	{
		pool.push(item);
	}

	public int getSize()
	{
		return pool.getSize();
	}

	public void setPool(Stack pool)
	{
		this.pool = pool;
	}

	public Stack getPool()
	{
		return pool;
	}

	public abstract void fill();
}
