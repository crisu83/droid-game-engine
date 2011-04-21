package com.cniska.game.engine.component;

import android.graphics.Bitmap;
import com.cniska.game.engine.base.Base;

/**
 * Sprite component class file.
 * This class provides the functionality for settings sprites for entities.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class SpriteComponent extends BaseComponent
{
	// ----------
	// Properties
	// ----------

	private Bitmap bitmap;

	// -------
	// Methods
	// -------

	/**
	 * Creates the component.
	 */
	public SpriteComponent()
	{
		super();
		reset();
		setState(ComponentState.BEFORE_RENDER.ordinal());
	}

	// ------------------
	// Overridden methods
	// ------------------

	/**
	 * Resets the component.
	 */
	@Override
	public void reset()
	{
		bitmap = null;
	}

	/**
	 * Updates the component.
	 * @param parent The parent object.
	 */
	@Override
	public void update(Base parent)
	{
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @param bitmap The bitmap the respresents this sprite.
	 */
	public void setBitmap(Bitmap bitmap)
	{
		this.bitmap = bitmap;
	}

	/**
	 * @return The bitmap the respresents this sprite.
	 */
	public Bitmap getBitmap()
	{
		return bitmap;
	}

	/**
	 * @return The width of the sprite.
	 */
	public int getWidth()
	{
		return bitmap.getWidth();
	}

	/**
	 * @return The height of the sprite.
	 */
	public int getHeight()
	{
		return bitmap.getHeight();
	}
}
