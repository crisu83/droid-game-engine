package com.cniska.game.engine.util;

/**
 * Vector class file.
 * This class provides basic vector mathematics.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class Vector
{
	// ----------
	// Properties
	// ----------

	public float x;
	public float y;

	// -------
	// Methods
	// -------

	/**
	 * Creates the vector.
	 */
	public Vector()
	{
		reset();
	}

	/**
	 * Creates the vector.
	 * @param vector The vector.
	 */
	public Vector(Vector vector)
	{
		this();
		set(vector);
	}

	/**
	 * Creates the vector
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public Vector(float x, float y)
	{
		set(x, y);
	}

	/**
	 * Adds another vector to this vector.
	 * @param other The other vector.
	 */
	public void add(Vector other)
	{
		this.x += other.x;
		this.y += other.y;
	}

	/**
	 * Substracts another vector from this vector.
	 * @param other The other vector.
	 */
	public void subtract(Vector other)
	{
		this.x -= other.x;
		this.y -= other.y;
	}

	/**
	 * Multiplies this vector.
	 * @param multiplier The multiplier.
	 */
	public void multiply(float multiplier)
	{
		this.x *= multiplier;
		this.y *= multiplier;
	}

	/**
	 * Divides this vector.
	 * @param divider The divider.
	 */
	public void divide(float divider)
	{
		this.x /= divider;
		this.y /= divider;
	}

	/**
	 * Resets this vector to zero.
	 */
	public void reset()
	{
		x = 0.0f;
		y = 0.0f;
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * Sets the coordinate for this vector.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the coordinate for this vector.
	 * @param vector The new vector.
	 */
	public void set(Vector vector)
	{
		this.x = vector.x;
		this.y = vector.y;
	}
}
