package com.cniska.game.engine.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import com.cniska.game.engine.util.Vector2;

import java.util.ArrayList;

/**
 * Render system class file.
 * This class provides the logic for rendering entities.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class RenderSystem extends BaseSystem
{
	// ----------
	// Properties
	// ----------

	private static final RenderSystem instance = new RenderSystem();
	private ArrayList<DrawableBitmap> drawQueue;

	// -------
	// Methods
	// -------

	/**
	 * Creates the system.
	 */
	private RenderSystem()
	{
		super();
		drawQueue = new ArrayList<DrawableBitmap>();
		reset();
	}

	/**
	 * Returns the singleton instance of this class.
	 * @return The system.
	 */
	public static RenderSystem getInstance()
	{
		return instance;
	}

	/**
	 * Queues a bitmap to for drawing.
	 * @param bitmap The bitmap to draw.
	 * @param position The position where the bitmap should be drawn.
	 */
	public void queueForDraw(Bitmap bitmap, Vector2 position)
	{
		drawQueue.add(new DrawableBitmap(bitmap, position));
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Resets this system.
	 */
	@Override
	public void reset()
	{
		drawQueue.clear();
	}

	/**
	 * Draws a single frame.
	 * This method is called by the game thread.
	 * @param canvas The canvas to draw to.
	 */
	public void drawFrame(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);
		
		synchronized (this)
		{
			final int drawCount = drawQueue.size();

			if (drawCount > 0)
			{
				for (int i = 0; i < drawCount; i++)
				{
					drawQueue.get(i).render(canvas);
				}
			}
		}

		reset();
	}

	// -------
	// Classes
	// -------

	/**
	 * Drawable bitmap class.
	 * This class represents a single item in the draw queue.
	 */
	private class DrawableBitmap
	{
		public Bitmap bitmap;
		public Vector2 position;

		/**
		 * Creates the drawable bitmap.
		 * @param bitmap The bitmap to draw.
	    * @param position The position where the bitmap should be drawn.
		 */
		public DrawableBitmap(Bitmap bitmap, Vector2 position)
		{
			this.bitmap = bitmap;
			this.position = position;
		}

		/**
		 * Renders this drawable bitmap.
		 * @param canvas The canvas to draw to.
		 */
		public void render(Canvas canvas)
		{
			canvas.drawBitmap(bitmap, position.x, position.y, null);
		}
	}
}
