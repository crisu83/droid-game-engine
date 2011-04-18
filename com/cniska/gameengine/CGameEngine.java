package com.cniska.gameengine;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Core game engine class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @copyright ChristofferNiska@gmail.com
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public abstract class CGameEngine extends SurfaceView implements Runnable
{
	private Thread animator;

	private volatile boolean running = false;
	private volatile boolean gameOver = false;

	private long period; // period in between drawing in nanoseconds

	private static final int NO_DELAYS_PER_YIELD = 16;
	private static final int MAX_FRAME_SKIPS = 5;

	private long framesSkipped = 0L;

	private long gameStartTime; // when the game was started
	private long lastUpdateTime; // when the game was last updated

	/**
	 * Create the game engine.
	 * @param context the activity.
	 * @param period the period in nanoseconds.
	 */
	public CGameEngine(Context context, long period)
	{
		super(context);

		this.period = period;

		getHolder().addCallback(
			new SurfaceHolder.Callback()
			{
				/**
				 * Actions to the be taken when the surface is created.
				 * @param holder the surface holder.
				 */
				@Override
				public void surfaceCreated(SurfaceHolder holder)
				{
					gameInit();
					gameStart();
				}

				/**
				 * Actions to take when the surface is changed.
				 * @param holder the surface holder.
				 * @param format the new pixel format of the surface.
				 * @param width the new width of the surface.
				 * @param height the new height of the surface.
				 */
				@Override
				public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

				/**
				 * Actions to the be taken when the surface is destroyed.
				 * @param holder the surface holder.
				 */
				@Override
				public void surfaceDestroyed(SurfaceHolder holder)
				{
					gameStop();
				}
			}
		);
	}

	/**
	 * Starts the game.
	 */
	public void gameStart()
	{
		// Make sure that the game isn't already running.
		if (animator == null || !running)
		{
			animator = new Thread(this);
			animator.start();
		}
	}

	/**
	 * Stops the game.
	 */
	public void gameStop()
	{
		running = false; // exit the game loop
	}

	/**
	 * Runs the game.
	 */
	public void run()
	{
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;

		gameStartTime = System.nanoTime();
		beforeTime = gameStartTime;

		running = true;

		while (running)
		{
			stateUpdate();
			viewUpdate();

			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;

			// We have time to sleep.
			if (sleepTime > 0)
			{
				try
				{
					Thread.sleep(sleepTime/1000000L); // ns -> ms
				}
				catch (java.lang.InterruptedException e) {}

				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			}
			// We don't have time to sleep because the update took longer than the period.
			else
			{
				excess -= sleepTime; // store excess time value
				overSleepTime = 0L;

				// Give other threads a chance run if necessary.
				if (++noDelays >= NO_DELAYS_PER_YIELD)
				{
					Thread.yield();
					noDelays = 0;
				}
			}

			beforeTime = System.nanoTime();

			int skips = 0;
			while (excess > period && skips < MAX_FRAME_SKIPS)
			{
				excess -= period;
				stateUpdate(); // update the state but do not render
				skips++;
			}

			framesSkipped += skips;
		}
	}

	/**
	 * Updates the game state.
	 */
	private void stateUpdate()
	{
		// Make sure that the game is not over.
		if (!gameOver)
		{
			// Get the amount of time passed since the last update
			// and update the game state.
			long timePassed = getTimeSinceLastUpdate();
			gameUpdate(timePassed);
		}
	}

	/**
	 * Calculates the time that has  passed since the last update.
	 * @return the time passed in nanoseconds.
	 */
	private long getTimeSinceLastUpdate()
	{
		long timePassed, timeNow;

		timeNow = System.nanoTime();

		// Calculate the time passed based on when the last previous update time.
		lastUpdateTime = lastUpdateTime > 0 ? lastUpdateTime : timeNow;
		timePassed = timeNow - lastUpdateTime;
		lastUpdateTime = timeNow;

		return timePassed;
	}

	/**
	 * Updates the view.
	 */
	public void viewUpdate()
	{
		Canvas c = null;
		SurfaceHolder holder = getHolder();

		try
		{
			c = holder.lockCanvas();

			synchronized (holder)
			{
				gameRender(c);
			}
		}
		finally
		{
			if (c != null)
			{
				holder.unlockCanvasAndPost(c);
			}
		}
	}

	/**
	 * Initializes the game.
	 */
	public abstract void gameInit();

	/**
	 * Updates the game state.
	 * @param timePassed the time passed since the last update.
	 */
	public abstract void gameUpdate(long timePassed);

	/**
	 * Renders the game.
	 * @param c the canvas.
	 */
	public abstract void gameRender(Canvas c);
}

