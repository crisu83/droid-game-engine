package com.cniska.game.demos.blocks;

import android.content.Context;
import com.cniska.game.engine.Game;
import com.cniska.game.engine.base.BaseCollection;
import com.cniska.game.engine.entity.Entity;
import com.cniska.game.engine.entity.EntityManager;
import com.cniska.game.demos.blocks.EntityFactory.EntityType;

/**
 * Blocks class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @copyright ChristofferNiska@gmail.com
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class Blocks extends Game
{
	private Entity block;
	private Entity block2;
	private Entity block3;

	/**
	 * Creates the game.
	 * @param context the context.
	 * @param gameWidth the width of the game.
	 * @param gameHeight the height of the game.
	 * @param period the update period in nano seconds.
	 */
	public Blocks(Context context, int gameWidth, int gameHeight, long period)
	{
		super(context, gameWidth, gameHeight, period);
	}

	/**
	 * Initializes this game.
	 */
	@Override
	public void init()
	{
		BaseCollection gameRoot = getGameRoot();
		EntityManager em = new EntityManager();
		gameRoot.add(em);

		block = EntityFactory.factory(EntityType.TEAL_BLOCK, 0, 0);
		block2 = EntityFactory.factory(EntityType.VIOLET_BLOCK, 0, 0);
		block3 = EntityFactory.factory(EntityType.YELLOW_BLOCK, 0, 0);

		em.addEntity(block);
		em.addEntity(block2);
		em.addEntity(block3);
	}
}
