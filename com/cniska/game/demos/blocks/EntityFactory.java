package com.cniska.game.demos.blocks;

import android.graphics.Bitmap;
import com.cniska.game.demos.blocks.component.BlockComponent;
import com.cniska.game.engine.GameParams;
import com.cniska.game.engine.component.*;
import com.cniska.game.engine.entity.Entity;
import com.cniska.game.engine.system.SystemRegistry;

import java.util.Random;

/**
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class EntityFactory
{
	public static Entity createBlock(String name, Bitmap bitmap)
	{
		Random rand = new Random();

		Entity block = new Entity(name);

		SpriteComponent spriteComponent = new SpriteComponent();
		spriteComponent.setBitmap(bitmap);
		block.addComponent(spriteComponent);

		SpatialComponent spatialComponent = new SpatialComponent();
		spatialComponent.setPosition((float) rand.nextInt(449), (float) rand.nextInt(289));
		spatialComponent.setSize(spriteComponent.getSize());
		block.addComponent(spatialComponent);

		VelocityComponent velocityComponent = new VelocityComponent();
		velocityComponent.setPositionComponent(spatialComponent);
		velocityComponent.setVelocity((float) rand.nextInt(3) + 1, (float) rand.nextInt(3) + 1);
		block.addComponent(velocityComponent);

		BounceComponent reactionComponent = new BounceComponent();
		reactionComponent.setSpatialComponent(spatialComponent);
		reactionComponent.setVelocityComponent(velocityComponent);
		block.addComponent(reactionComponent);

		CollisionComponent collisionComponent = new CollisionComponent();
		collisionComponent.setSpatialComponent(spatialComponent);
		collisionComponent.setReactionComponent(reactionComponent);
		block.addComponent(collisionComponent);

		RenderComponent renderComponent = new RenderComponent();
		renderComponent.setSpartialComponent(spatialComponent);
		renderComponent.setSpriteComponent(spriteComponent);
		block.addComponent(renderComponent);

		BlockComponent blockComponent = new BlockComponent();
		blockComponent.setSpatialComponent(spatialComponent);
		blockComponent.setSpriteComponent(spriteComponent);
		blockComponent.setVelocityComponent(velocityComponent);
		block.addComponent(blockComponent);

		return block;
	}
}
