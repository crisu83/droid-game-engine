package com.cniska.game.engine.debug;

import android.util.Log;

/**
 * Debug class file.
 * This class wraps the Android Log to provide functionality for enabling and disabling logging in the application.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public final class Logger
{
	// ---------
	// Constants
	// ---------

	public static final String TAG_CORE = "Core";

	// ----------
	// Properties
	// ----------

	private static boolean logging = true;

	// -------
	// Methods
	// -------

	/**
	 * Constructor.
	 */
	private Logger()
	{
		// We do not allow instancing of this class.
	}

	/**
	 * Sends a DEBUG log message.
	 * @param tag The message tag.
	 * @param msg The message string.
	 */
	public static int debug(String tag, String msg)
	{
		return logging ? Log.d(tag, msg) : 0;
	}

	/**
	 * Sends a DEBUG log message and logs an exception.
	 * @param tag The message tag.
	 * @param msg The message string.
	 * @param tr The throwable exception.
	 */
	public static int debug(String tag, String msg, Throwable tr)
	{
		return logging ? Log.d(tag, msg, tr) : 0;
	}

	/**
	 * Sends an ERROR log message.
	 * @param tag The message tag.
	 * @param msg The message string.
	 */
	public static int error(String tag, String msg)
	{
		return logging ? Log.e(tag, msg) : 0;
	}

	/**
	 * Sends an ERROR log message and logs an exception.
	 * @param tag The message tag.
	 * @param msg The message string.
	 * @param tr The throwable exception.
	 */
	public static int error(String tag, String msg, Throwable tr)
	{
		return logging ? Log.e(tag, msg, tr) : 0;
	}

	/**
	 * Sends a INFO log message.
	 * @param tag The message tag.
	 * @param msg The message string.
	 */
	public static int info(String tag, String msg)
	{
		return logging ? Log.d(tag, msg) : 0;
	}

	/**
	 * Sends a INFO log message and logs an exception.
	 * @param tag The message tag.
	 * @param msg The message string.
	 * @param tr The throwable exception.
	 */
	public static int info(String tag, String msg, Throwable tr)
	{
		return logging ? Log.d(tag, msg, tr) : 0;
	}

	/**
	 * Sends a VERBOSE log message.
	 * @param tag The message tag.
	 * @param msg The message string.
	 */
	public static int verbose(String tag, String msg)
	{
		return logging ? Log.v(tag, msg) : 0;
	}

	/**
	 * Sends a VERBOSE log message and logs an exception.
	 * @param tag The message tag.
	 * @param msg The message string.
	 * @param tr The throwable exception.
	 */
	public static int verbose(String tag, String msg, Throwable tr)
	{
		return logging ? Log.v(tag, msg, tr) : 0;
	}

	/**
	 * Sends a WARN log message.
	 * @param tag The message tag.
	 * @param msg The message string.
	 */
	public static int warn(String tag, String msg)
	{
		return logging ? Log.w(tag, msg) : 0;
	}

	/**
	 * Sends a WARN log message and logs an exception.
	 * @param tag The message tag.
	 * @param msg The message string.
	 * @param tr The throwable exception.
	 */
	public static int warn(String tag, String msg, Throwable tr)
	{
		return logging ? Log.w(tag, msg, tr) : 0;
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * @param value Whether logging is enabled.
	 */
	public static void setLogging(boolean value)
	{
		logging = value;
	}
}
