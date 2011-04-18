package com.cniska.game.engine;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

/**
 * Main game activity.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @copyright ChristofferNiska@gmail.com
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class Main extends Activity
{
	private static final int DEFAULT_FPS = 80;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	    // Force orientation to landscape, which is more suitable for games.
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

	    // Remove the title bar.
	    requestWindowFeature(Window.FEATURE_NO_TITLE);

	    // Calculate the period.
		long period = (long) 1000.0 / DEFAULT_FPS;

	    // Start the game.
        setContentView(new Template(this, period*1000000L)); // ms -> ns
    }
}
