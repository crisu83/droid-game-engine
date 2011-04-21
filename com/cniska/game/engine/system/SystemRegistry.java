package com.cniska.game.engine.system;

import com.cniska.game.engine.base.Base;
import com.cniska.game.engine.GameParams;
import com.cniska.game.engine.base.BaseCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * System registry class file.
 * This class provides static access to all the systems and the game parameters.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class SystemRegistry
{
	// ----------
	// Properties
	// ----------

	private static ArrayList<BaseSystem> systems = new ArrayList<BaseSystem>();
	public static GameParams params;

	// -------
	// Methods
	// -------

	/**
	 * Adds a system to the registry.
	 * @param system The system to add.
	 */
	public synchronized static void addSystem(BaseSystem system)
	{
		systems.add(system);
	}

	/**
	 * Removes a system from the registry.
	 * @param system The system to remove.
	 */
	public synchronized static void removeSystem(BaseSystem system)
	{
		systems.remove(system);
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * Returns a specific system from the registry.
	 * @param type The class of the system to return.
	 * @return The system, or null if the system is not registered.
	 */
	public static Base getSystem(Class<? extends BaseSystem> type)
	{
		if (systems.size() > 0)
		{
			for (Base system : systems)
			{
				if (system.getClass() == type)
				{
					return system;
				}
			}
		}

		return null;
	}
}
