package com.cniska.gameengine;

import android.app.Activity;
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
	    requestWindowFeature(Window.FEATURE_NO_TITLE);

	    int fps = DEFAULT_FPS;
		long period = (long) 1000.0 / fps;
	    
        setContentView(new Template(this, period*1000000L)); // ms -> ns
    }
}
