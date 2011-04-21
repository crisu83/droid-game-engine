package com.cniska.game.engine.entity;

import android.graphics.BitmapFactory;
import com.cniska.game.blocks.R;
import com.cniska.game.blocks.component.BlockComponent;
import com.cniska.game.engine.GameParams;
import com.cniska.game.engine.component.*;
import com.cniska.game.engine.system.CollisionBox;
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

		SpatialComponent spatialComponent = new SpatialComponent();
		spatialComponent.setPosition(x, y);
		block.addComponent(spatialComponent);

		VelocityComponent velocityComponent = new VelocityComponent();
		velocityComponent.setPositionComponent(spatialComponent);
		velocityComponent.setVelocity(5, 5);
		block.addComponent(velocityComponent);

		CollisionComponent collisionComponent = new CollisionComponent();
		collisionComponent.setSpatialComponent(spatialComponent);
		block.addComponent(collisionComponent);
		
		SpriteComponent spriteComponent = new SpriteComponent();
		spriteComponent.setBitmap(BitmapFactory.decodeResource(params.context.getResources(), R.drawable.block));
		block.addComponent(spriteComponent);

		RenderComponent renderComponent = new RenderComponent();
		renderComponent.setSpartialComponent(spatialComponent);
		renderComponent.setSpriteComponent(spriteComponent);
		block.addComponent(renderComponent);

		BlockComponent blockComponent = new BlockComponent();
		blockComponent.setPositionComponent(spatialComponent);
		blockComponent.setSpriteComponent(spriteComponent);
		blockComponent.setVelocityComponent(velocityComponent);
		block.addComponent(blockComponent);

		return block;
	}
}
