package com.cniska.game.demos.blocks.component;

import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.component.BaseComponent;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class VanishComponent extends BaseComponent
{
	private final static int DEFAULT_LIFETIME = 2000; // 2 seconds

	private double vanishTime;

	public VanishComponent()
	{
		super();
		reset();
		setState(ComponentState.BEFORE_RENDER.ordinal());
	}

	/**
	 * Resets this object.
	 */
	@Override
	public void reset()
	{
		double lifeTime = DEFAULT_LIFETIME * 1000000L; // ms -> ns;
		vanishTime = System.nanoTime() + lifeTime;
	}

	/**
	 * Updates the component.
	 * @param parent The parent object.
	 */
	public void update(Base parent)
	{
		double timeNow = System.nanoTime();

		if (timeNow >= vanishTime)
		{
			getOwner().setRemoved(true);
		}
	}
}
