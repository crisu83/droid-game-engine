package com.cniska.game.demos.blocks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	public enum EntityType
	{
		TEAL_BLOCK,
		VIOLET_BLOCK,
		YELLOW_BLOCK,
	}

	public static Entity factory(EntityType type, float x, float y)
	{
		Entity entity = null;

		switch (type)
		{
			case TEAL_BLOCK:
				entity = createTealBlock();
				break;

			case VIOLET_BLOCK:
				entity = createVioletBlock();
				break;

			case YELLOW_BLOCK:
				entity = createYellowBlock();
				break;
		}

		return entity;
	}

	private static Entity createTealBlock()
	{
		Bitmap bitmap = BitmapFactory.decodeResource(
				SystemRegistry.params.context.getResources(), R.drawable.teal_block);
		return createBlock("TEAL", bitmap);
	}

	private static Entity createVioletBlock()
	{
		Bitmap bitmap = BitmapFactory.decodeResource( 
				SystemRegistry.params.context.getResources(), R.drawable.violet_block);
		return createBlock("VIOLET", bitmap);
	}

	private static Entity createYellowBlock()
	{
		Bitmap bitmap = BitmapFactory.decodeResource(
				SystemRegistry.params.context.getResources(), R.drawable.yellow_block);
		return createBlock("YELLOW", bitmap);
	}

	private static Entity createBlock(String name, Bitmap bitmap)
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
