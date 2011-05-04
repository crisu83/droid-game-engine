package com.cniska.game.engine.util;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class Stack
{
	private Object[] stack;
	private int lastIndex;

	public Stack(int size)
	{
		stack = new Object[size];
		lastIndex = -1;
	}

	public Object pop()
	{
		Object item = null;

		if (lastIndex > 0)
		{
			item = stack[lastIndex];
			stack[lastIndex] = null;
			lastIndex--;
		}

		return item;
	}

	public void push(Object item)
	{
		if (lastIndex < stack.length)
		{
			lastIndex++;
			stack[lastIndex] = item;
		}
	}

	public int getSize()
	{
		return stack.length;
	}
}
