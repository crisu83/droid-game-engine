package com.cniska.game.engine;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.cniska.game.engine.entity.EntityManager;
import com.cniska.game.engine.system.CollisionSystem;
import com.cniska.game.engine.system.RenderSystem;
import com.cniska.game.engine.system.SystemRegistry;

/**
 * Game class file.
 * The heart of the game, keeps track of the game thread and the game root entity manager.
 * @see GameThread
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @copyright ChristofferNiska@gmail.com
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public abstract class Game extends SurfaceView implements SurfaceHolder.Callback
{
	// ----------
	// Properties
	// ----------

	private volatile boolean running = false;
	private GameThread gameThread;
	private Thread game;
	private EntityManager gameRoot;

	// -------
	// Methods
	// -------

	/**
	 * Creates the game.
	 * @param context the context.
	 * @param gameWidth the width of the game.
	 * @param gameHeight the height of the game.
	 * @param period the update period in nano seconds.
	 */
	public Game(Context context, int gameWidth, int gameHeight, long period)
	{
		super(context);

		GameParams params = new GameParams();
		params.context = context;
		params.gameWidth = gameWidth;
		params.gameHeight = gameHeight;
		SystemRegistry.params = params;

		getHolder().addCallback(this);

		gameRoot = new EntityManager();
		gameThread = new GameThread(this, period);
		gameThread.setGameRoot(gameRoot);

		// Create the rendering system.

		RenderSystem renderSystem = new RenderSystem();
		SystemRegistry.addSystem(renderSystem);

		// Create the collision system.

		CollisionSystem collisionSystem = new CollisionSystem();
		SystemRegistry.addSystem(collisionSystem);
		gameRoot.add(collisionSystem);

		init();
	}

	/**
	 * Starts this game.
	 */
	public void start()
	{
		if (!running)
		{
			Runtime r = Runtime.getRuntime();
			r.gc(); // Run garbage collection
			game = new Thread(gameThread);
			game.setName("Game");
			game.start();
			running = true;
		}
	}

	/**
	 * Stops this game.
	 */
	public void stop()
	{
		if (running)
		{
			gameThread.stopGame();
			game = null;
			running = false;
		}
	}

	/**
	 * Actions to be taken when this game is paused.
	 */
	public void onPause()
	{
		if (running)
		{
			gameThread.pauseGame();
		}
	}

	/**
	 * Actions to be taken when this game is resumed.
	 */
	public void onResume()
	{
		if (running)
		{
			gameThread.resumeGame();
		}
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Actions to the be taken when the surface is created.
	 * @param holder the surface holder.
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		start();
	}

	/**
	 * Actions to take when the surface is changed.
	 * @param holder the surface holder.
	 * @param format the new pixel format of the surface.
	 * @param width the new width of the surface.
	 * @param height the new height of the surface.
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
	}

	/**
	 * Actions to the be taken when the surface is destroyed.
	 * @param holder the surface holder.
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		stop();
	}

	// ----------------
	// Abstract methods
	// ----------------

	/**
	 * Initializes this game.
	 * Override to implement your own game.
	 */
	public abstract void init();

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @return the root entity manager.
	 */
	public EntityManager getGameRoot()
	{
		return gameRoot;
	}
}

