package com.cniska.game.demos.blocks;

import com.cniska.game.demos.blocks.EntityFactory.EntityType;
import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.entity.Entity;
import com.cniska.game.engine.entity.EntityManager;
import com.cniska.game.engine.input.InputTouch;
import com.cniska.game.engine.input.InputTouchPointer;
import com.cniska.game.engine.system.InputSystem;
import com.cniska.game.engine.system.SystemRegistry;
import com.cniska.game.engine.util.Vector;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class BlockSpawner extends Base
{
	private EntityManager entityManager;

	/**
	 * Creates the block spawner.
	 */
	public BlockSpawner()
	{
		super();
		reset();
	}

	/**
	 * Updates this object.
	 * @param parent The parent object.
	 */
	public void update(Base parent)
	{
		entityManager.update(this);

		InputSystem inputSystem = (InputSystem) SystemRegistry.getSystem(InputSystem.class);
		InputTouch touch = inputSystem.getTouch();
		InputTouchPointer[] pointers = touch.getActivePointers();

		InputTouchPointer pointer;

		for (int i = 0; i < pointers.length; i++)
		{
			pointer = pointers[i];

			if (pointer != null)
			{
				Vector position = pointer.getPosition();
				Entity touchBlock = EntityFactory.factory(EntityType.GREY_BLOCK, position.x, position.y);
				entityManager.addEntity(touchBlock);
			}
		}

		touch.resetAllPointers();
	}

	/**
	 * Resets this object.
	 */
	@Override
	public void reset()
	{
		entityManager = new EntityManager();
	}
}
