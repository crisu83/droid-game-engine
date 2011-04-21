package com.cniska.game.engine.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.util.Vector;

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

	private ArrayList<DrawableBitmap> drawQueue;

	// -------
	// Methods
	// -------

	/**
	 * Creates the system.
	 */
	public RenderSystem()
	{
		super();
		reset();
	}

	/**
	 * Resets this system.
	 */
	@Override
	public void reset()
	{
		drawQueue = new ArrayList<DrawableBitmap>();
	}

	/**
	 * Queues a bitmap to for drawing.
	 * @param bitmap The bitmap to draw.
	 * @param position The position where the bitmap should be drawn.
	 */
	public void queueForDraw(Bitmap bitmap, Vector position)
	{
		drawQueue.add(new DrawableBitmap(bitmap, position));
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
			if (drawQueue.size() > 0)
			{
				for (DrawableBitmap item : drawQueue)
				{
					item.render(canvas);
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
		public Vector position;

		/**
		 * Creates the drawable bitmap.
		 * @param bitmap The bitmap to draw.
	    * @param position The position where the bitmap should be drawn.
		 */
		public DrawableBitmap(Bitmap bitmap, Vector position)
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
