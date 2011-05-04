package com.cniska.game.demos.blocks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.cniska.game.demos.blocks.component.VanishComponent;
import com.cniska.game.engine.component.BoundaryComponent;
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
		GREY_BLOCK,
		TEAL_BLOCK,
		VIOLET_BLOCK,
		YELLOW_BLOCK,
	}

	public static Entity factory(EntityType type, float x, float y)
	{
		Entity entity = null;

		switch (type)
		{
			case GREY_BLOCK:
				entity = createGreyBlock(x, y);
				break;

			case TEAL_BLOCK:
				entity = createTealBlock(x, y);
				break;

			case VIOLET_BLOCK:
				entity = createVioletBlock(x, y);
				break;

			case YELLOW_BLOCK:
				entity = createYellowBlock(x, y);
				break;
		}

		return entity;
	}

	private static Entity createGreyBlock(float x, float y)
	{
		Bitmap bitmap = BitmapFactory.decodeResource(
				SystemRegistry.params.context.getResources(), R.drawable.grey_block);
		return createGravityBlock(bitmap, x, y);
	}

	private static Entity createTealBlock(float x, float y)
	{
		Bitmap bitmap = BitmapFactory.decodeResource(
				SystemRegistry.params.context.getResources(), R.drawable.teal_block);
		return createMovingBlock("TEAL", bitmap, x, y);
	}

	private static Entity createVioletBlock(float x, float y)
	{
		Bitmap bitmap = BitmapFactory.decodeResource( 
				SystemRegistry.params.context.getResources(), R.drawable.violet_block);
		return createMovingBlock("VIOLET", bitmap, x, y);
	}

	private static Entity createYellowBlock(float x, float y)
	{
		Bitmap bitmap = BitmapFactory.decodeResource(
				SystemRegistry.params.context.getResources(), R.drawable.yellow_block);
		return createMovingBlock("YELLOW", bitmap, x, y);
	}

	private static Entity createMovingBlock(String name, Bitmap bitmap, float x, float y)
	{
		Random rand = new Random();

		Entity block = new Entity(name);

		SpriteComponent spriteComponent = new SpriteComponent();
		spriteComponent.setBitmap(bitmap);
		block.addComponent(spriteComponent);

		x = x > 0.0f ? x : (float) rand.nextInt(449);
		y = y > 0.0f ? y : (float) rand.nextInt(289);

		SpatialComponent spatialComponent = new SpatialComponent();
		spatialComponent.setPosition(x, y);
		spatialComponent.setSize(spriteComponent.getSize());
		block.addComponent(spatialComponent);

		float vx = (float) rand.nextInt(5) + 1;
		float vy = (float) rand.nextInt(5) + 1;

		vx = rand.nextBoolean() ? vx : -vx;
		vy = rand.nextBoolean() ? vy : -vy;

		VelocityComponent velocityComponent = new VelocityComponent();
		velocityComponent.setPositionComponent(spatialComponent);
		velocityComponent.setVelocity(vx, vy);
		block.addComponent(velocityComponent);

		BoundaryComponent boundaryComponent = new BoundaryComponent();
		boundaryComponent.setSpatialComponent(spatialComponent);
		boundaryComponent.setSpriteComponent(spriteComponent);
		boundaryComponent.setVelocityComponent(velocityComponent);
		block.addComponent(boundaryComponent);

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

		return block;
	}

	private static Entity createGravityBlock(Bitmap bitmap, float x, float y)
	{
		Entity block = new Entity("GREY");

		SpriteComponent spriteComponent = new SpriteComponent();
		spriteComponent.setBitmap(bitmap);
		block.addComponent(spriteComponent);

		SpatialComponent spatialComponent = new SpatialComponent();
		spatialComponent.setPosition(x, y);
		spatialComponent.setSize(spriteComponent.getSize());
		block.addComponent(spatialComponent);

		VelocityComponent velocityComponent = new VelocityComponent();
		velocityComponent.setPositionComponent(spatialComponent);
		block.addComponent(velocityComponent);

		GravityComponent gravityComponent = new GravityComponent();
		gravityComponent.setSpatialComponent(spatialComponent);
		gravityComponent.setVelocityComponent(velocityComponent);
		block.addComponent(gravityComponent);

		BoundaryComponent boundaryComponent = new BoundaryComponent();
		boundaryComponent.setSpatialComponent(spatialComponent);
		boundaryComponent.setSpriteComponent(spriteComponent);
		block.addComponent(boundaryComponent);

		CollisionComponent collisionComponent = new CollisionComponent();
		collisionComponent.setSpatialComponent(spatialComponent);
		block.addComponent(collisionComponent);

		RenderComponent renderComponent = new RenderComponent();
		renderComponent.setSpartialComponent(spatialComponent);
		renderComponent.setSpriteComponent(spriteComponent);
		block.addComponent(renderComponent);

		VanishComponent vanishComponent = new VanishComponent();
		block.addComponent(vanishComponent);

		return block;
	}
}
