package com.cniska.game.engine.entity;

import android.graphics.BitmapFactory;
import com.cniska.game.blocks.R;
import com.cniska.game.blocks.component.BlockComponent;
import com.cniska.game.engine.GameParams;
import com.cniska.game.engine.component.*;
import com.cniska.game.engine.system.SystemRegistry;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class EntityFactory
{
	public static Entity createBlock(float x, float y)
	{
		GameParams params = SystemRegistry.params;

		Entity block = new Entity();

		SpatialComponent pc = new SpatialComponent();
		pc.setPosition(x, y);
		block.addComponent(pc);

		VelocityComponent vc = new VelocityComponent();
		vc.setPositionComponent(pc);
		vc.setVelocity(3, 3);
		block.addComponent(vc);
		
		SpriteComponent sc = new SpriteComponent();
		sc.setBitmap(BitmapFactory.decodeResource(params.context.getResources(), R.drawable.block));
		block.addComponent(sc);

		RenderComponent rc = new RenderComponent();
		rc.setPositionComponent(pc);
		rc.setSpriteComponent(sc);
		block.addComponent(rc);

		BlockComponent bc = new BlockComponent();
		bc.setPositionComponent(pc);
		bc.setSpriteComponent(sc);
		bc.setVelocityComponent(vc);
		block.addComponent(bc);

		return block;
	}
}
