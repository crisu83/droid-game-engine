package com.cniska.gameengine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.text.DecimalFormat;

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

	private static final long MAX_STATS_INTERVAL = 1000L; // record stats every second
	private static final int NUM_FPS = 10; // number of FPS values sorted to get an average

	private long statsInterval = 0L; // in nanoseconds
	private long prevStatsTime;
	private long totalElapsedTime = 0L;
	private long gameStartTime;
	protected int timeSpentInGame = 0; // in seconds

	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private double upsStore[];
	protected double averageUPS = 0.0;

	private long frameCount = 0;
	private double fpsStore[];
	private long statsCount = 0;
	protected double averageFPS = 0.0;

	//protected DecimalFormat df = new DecimalFormat("0.##"); // 2 decimal precision
	//protected DecimalFormat timedf = new DecimalFormat("0.####"); // 4 decimal precision

	/**
	 * Create the game engine.
	 * @param context the activity.
	 * @param period the period in nanoseconds.
	 */
	public CGameEngine(Context context, long period)
	{
		super(context);

		this.period = period;

		// Initialize the timing elements.
		fpsStore = new double[NUM_FPS];
		upsStore = new double[NUM_FPS];

		for (int i = 0; i < NUM_FPS; i++)
		{
			fpsStore[i] = 0.0;
			upsStore[i] = 0.0;
		}

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
		prevStatsTime = gameStartTime;
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

			storeStats();
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
			gameUpdate();
		}
	}

	/**
	 * Updates the view.
	 */
	// TODO: Improve.
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
	 * Stores the runtime statistics.
	 */
	private void storeStats()
	{
		frameCount++;
		statsInterval += period;

		// Make sure we should collect the stats.
		if (statsInterval >= MAX_STATS_INTERVAL)
		{
			long timeNow = System.nanoTime();
			timeSpentInGame = (int) ((timeNow - gameStartTime) / 1000000000L); // ns -> seconds

			long realElapsedTime = timeNow - prevStatsTime; // time since last stats collection
			totalElapsedTime += realElapsedTime;

			totalFramesSkipped += framesSkipped;

			// Calculate the latest FPS and UPS.
			double actualFPS = 0;
			double actualUPS = 0;

			if (totalElapsedTime > 0)
			{
				actualFPS = ((double) frameCount / totalElapsedTime) * 1000000000L; // ns -> seconds
				actualUPS = ((double) (frameCount + totalFramesSkipped) / totalElapsedTime) * 1000000000L;
			}

			// Store the latest FPS and UPS.
			fpsStore[ (int) statsCount % NUM_FPS ] = actualFPS;
			upsStore[ (int) statsCount % NUM_FPS ] = actualUPS;

			statsCount++;

			double totalFPS = 0.0;
			double totalUPS = 0.0;

			for( int i=0; i<NUM_FPS; i++ )
			{
				totalFPS += fpsStore[i];
				totalUPS += upsStore[i];
			}

			if( statsCount<NUM_FPS )
			{
				averageFPS = totalFPS / statsCount;
				averageUPS = totalUPS / statsCount;
			}
			else
			{
				averageFPS = totalFPS / NUM_FPS;
				averageUPS = totalUPS / NUM_FPS;
			}

			framesSkipped = 0;
			prevStatsTime = timeNow;
			statsInterval = 0L; // reset
		}
	}

	/**
	 * Renders the update statistics.
	 * @param canvas the canvas.
	 */
	protected void renderStats(Canvas canvas)
	{
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(12);

		DecimalFormat df = new DecimalFormat("0.##"); // 2 decimal precision
		canvas.drawText("FPS:" + df.format(averageFPS), 10, getHeight()-10, paint);
		canvas.drawText("UPS:" + df.format(averageUPS), 80, getHeight()-10, paint);
	}

	/**
	 * Initializes the game.
	 */
	public abstract void gameInit();

	/**
	 * Updates the game state.
	 */
	public abstract void gameUpdate();

	/**
	 * Renders the game.
	 * @param canvas the canvas.
	 */
	public abstract void gameRender(Canvas canvas);
}

