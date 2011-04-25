package com.cniska.game.demos.blocks;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import com.cniska.game.engine.Game;

/**
 * Main game activity.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 * @copyright ChristofferNiska@gmail.com
 * @license New BSD License http://www.opensource.org/licenses/bsd-license.php
 */
public class Main extends Activity
{
	private static final int DEFAULT_FPS = 80;
	private Game game;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	    // Force orientation to landscape, which is more suitable for games.
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

	    // Set full-screen.
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	    // -------------------------
	    // Calculate display metrics
	    // -------------------------

	    DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);

	    // ----------------------
	    // Create the game engine
	    // ----------------------

	    long period = (long) 1000.0 / DEFAULT_FPS;
        game = new Blocks(this, dm.widthPixels, dm.heightPixels, period * 1000000L);
	    
	    setContentView(game);
    }

	protected void onDestroy()
	{
		game.stop();
		super.onDestroy();
	}

	protected void onPause()
	{
		super.onPause();
		game.onPause();
	}

	protected void onResume()
	{
		super.onResume();
		game.onResume();
	}
}
