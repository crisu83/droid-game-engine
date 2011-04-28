package com.cniska.game.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Debug;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.base.BaseCollection;
import com.cniska.game.engine.debug.Logger;
import com.cniska.game.engine.system.RenderSystem;
import com.cniska.game.engine.system.SystemRegistry;

import java.text.DecimalFormat;

/**
 * Game thread class file.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class GameThread implements Runnable
{
	// ----------
	// Properties
	// ----------

	private long period; // period in between drawing in nanoseconds
	private volatile boolean finished = false;
	private volatile boolean paused = false;
	private SurfaceView view;
	private BaseCollection gameRoot;
	private Object pauseLock;

	private static final int NO_DELAYS_PER_YIELD = 16;
	private static final int MAX_FRAME_SKIPS = 5;
	private static final long MAX_STATS_INTERVAL = 1000L; // record stats every second
	private static final int NUM_FPS = 10; // number of FPS values sorted to get an average

	private long statsInterval = 0L; // in nanoseconds
	private long prevStatsTime;
	private long totalElapsedTime = 0L;
	private long gameStartTime;
	private int timeSpentInGame = 0; // in seconds

	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private double upsStore[];
	private double averageUPS = 0.0;

	private long frameCount = 0;
	private double fpsStore[];
	private long statsCount = 0;
	private double averageFPS = 0.0;

	// -------
	// Methods
	// -------

	/**
	 * Creates the game thread.
	 * @param view The surface view.
	 * @param period The update period in nano seconds.
	 */
	public GameThread(SurfaceView view, long period)
	{
		this.view = view;
		this.period = period;

		pauseLock = new Object();

		gameStartTime = System.nanoTime();

		fpsStore = new double[NUM_FPS];
		upsStore = new double[NUM_FPS];

		for (int i = 0; i < NUM_FPS; i++)
		{
			fpsStore[i] = 0.0;
			upsStore[i] = 0.0;
		}

		Logger.info(Logger.TAG_CORE, "Game thread created.");
	}

	/**
	 * Runs the game.
	 */
	public void run()
	{
		Base parent = null; // We have no parent because we're at the root level.
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		long excess = 0L;
		int noDelays = 0;

		prevStatsTime = gameStartTime;
		beforeTime = gameStartTime;

		Logger.info(Logger.TAG_CORE, "Starting the game loop.");

		while (!finished)
		{
			if (gameRoot != null)
			{
				//Debug.startMethodTracing("gameLoop");

				gameRoot.update(parent);

				synchronized (this)
				{
					SurfaceHolder holder = view.getHolder();
					Canvas canvas = holder.lockCanvas();

					if (canvas != null)
					{
						RenderSystem system = (RenderSystem) SystemRegistry.getSystem(RenderSystem.class);
						system.drawFrame(canvas);
						//drawStats(canvas); // Decimal formatting kills to performance.
						holder.unlockCanvasAndPost(canvas);
					}
				}

				afterTime = System.nanoTime();
				timeDiff = afterTime - beforeTime;
				sleepTime = (period - timeDiff) - overSleepTime;

				// We have time to sleep.
				if (sleepTime >= 0)
				{
					try
					{
						Thread.sleep(sleepTime/1000000L); // ns -> ms
					}
					catch (InterruptedException e)
					{
						Logger.info(Logger.TAG_CORE, "Game loop sleep interrupted.");						
					}

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
					gameRoot.update(parent); // update the state but do not render
					skips++;
				}

				framesSkipped += skips;

				saveStats();

				//logStats();

				//Debug.stopMethodTracing();
			}

			synchronized (pauseLock)
			{
				if (paused)
				{
					while (paused)
					{
						try
						{
							pauseLock.wait();
						}
						catch (InterruptedException e)
						{
							Logger.info(Logger.TAG_CORE, "Paused lock interrupted.");
						}
					}
				}
			}
		}
	}

	/**
	 * Stops the game.
	 */
	public void stopGame()
	{
		synchronized (pauseLock)
		{
			if (paused)
			{
				resumeGame();
			}

			finished = true;
		}
	}

	/**
	 * Pauses the game.
	 */
	public void pauseGame()
	{
		synchronized (pauseLock)
		{
			paused = true;
		}
	}

	/**
	 * Resumes the game.
	 */
	public void resumeGame()
	{
		synchronized (pauseLock)
		{
			paused = false;
			pauseLock.notifyAll(); // tell the thread that the game is no longer paused
		}
	}

	/**
	 * Saves update statistics for the game.
	 * This method is called once during each game loop.
	 */
	private void saveStats()
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
			statsInterval = 0L;
		}
	}

	protected void logStats()
	{
		Logger.info(Logger.TAG_CORE, "FPS: " + averageFPS + ", UPS: " + averageUPS + ", Skipped: " + framesSkipped);
	}

	/**
	 * Draws the average statistics to the canvas.
	 * @param canvas The canvas.
	 */
	protected void drawStats(Canvas canvas)
	{
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(12);

		DecimalFormat df = new DecimalFormat("0.##"); // 2 decimal precision
		canvas.drawText("FPS:" + df.format(averageFPS), 10, view.getHeight()-10, paint);
		canvas.drawText("UPS:" + df.format(averageUPS), 80, view.getHeight()-10, paint);
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @param gameRoot The game root entity manager.
	 */
	public void setGameRoot(BaseCollection gameRoot)
	{
		this.gameRoot = gameRoot;
	}

	/**
	 * @return Whether the game is paused.
	 */
	public boolean getPaused()
	{
		return paused;
	}
}
