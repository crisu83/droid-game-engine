package com.cniska.game.engine;

import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.cniska.game.engine.base.BaseCollection;
import com.cniska.game.engine.debug.Logger;
import com.cniska.game.engine.system.CollisionSystem;
import com.cniska.game.engine.system.InputSystem;
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
	private BaseCollection gameRoot;

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

		Logger.info(Logger.TAG_CORE, "Creating game.");

		GameParams params = new GameParams();
		params.context = context;
		params.gameWidth = gameWidth;
		params.gameHeight = gameHeight;
		SystemRegistry.params = params;

		getHolder().addCallback(this);

		gameRoot = new BaseCollection();
		gameThread = new GameThread(this, period);
		gameThread.setGameRoot(gameRoot);

		// Create the rendering system.

		RenderSystem renderSystem = RenderSystem.getInstance();
		SystemRegistry.addSystem(renderSystem);
		Logger.info(Logger.TAG_CORE, "Added rendering system to the system regristy.");

		// Create the collision system.

		CollisionSystem collisionSystem = CollisionSystem.getInstance();
		SystemRegistry.addSystem(collisionSystem);
		Logger.info(Logger.TAG_CORE, "Added collision system to the system regristy.");
		gameRoot.add(collisionSystem);

		// Create the input system.

		InputSystem inputSystem = InputSystem.getInstance();
		SystemRegistry.addSystem(inputSystem);
		Logger.info(Logger.TAG_CORE, "Added input system to the system registry.");

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
			
			Logger.info(Logger.TAG_CORE, "Starting the game.");
			game = new Thread(gameThread);
			game.setName("Game");
			game.start();
			running = true;
		}
		else
		{
			Logger.warn(Logger.TAG_CORE, "Trying to start the game while it is already running!");
		}
	}

	/**
	 * Stops this game.
	 */
	public void stop()
	{
		if (running)
		{
			Logger.info(Logger.TAG_CORE, "Stopping the game.");
			gameThread.stopGame();
			game = null;
			running = false;
		}
		else
		{
			Logger.warn(Logger.TAG_CORE, "Trying to stop the game while it is not running!");
		}
	}

	/**
	 * Actions to be taken when this game is paused.
	 */
	public void onPause()
	{
		if (running)
		{
			Logger.info(Logger.TAG_CORE, "Pausing the game.");
			gameThread.pauseGame();
		}
		else
		{
			Logger.warn(Logger.TAG_CORE, "Trying to pause the game while it is not running!");
		}
	}

	/**
	 * Actions to be taken when this game is resumed.
	 */
	public void onResume()
	{
		if (running)
		{
			Logger.info(Logger.TAG_CORE, "Resuming the game.");
			gameThread.resumeGame();
		}
		else
		{
			Logger.warn(Logger.TAG_CORE, "Trying to resume the game while it is not running!");
		}
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		if (running)
		{
			InputSystem system = (InputSystem) SystemRegistry.getSystem(InputSystem.class);

			if (system != null)
			{
				system.onTouchEvent(event);
				return true;
			}
		}

		return false;
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
		Logger.info(Logger.TAG_CORE, "Surface created.");
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
		Logger.info(Logger.TAG_CORE, "Surface changed.");
	}

	/**
	 * Actions to the be taken when the surface is destroyed.
	 * @param holder the surface holder.
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		Logger.info(Logger.TAG_CORE, "Surface destroyed.");
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
	 * @return the root node of this game.
	 */
	public BaseCollection getGameRoot()
	{
		return gameRoot;
	}
}

