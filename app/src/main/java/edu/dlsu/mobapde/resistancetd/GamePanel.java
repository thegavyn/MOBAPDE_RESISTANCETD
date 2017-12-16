package edu.dlsu.mobapde.resistancetd;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * ResistanceTD
 * Created by Jasper Glen A. Pillejera on 2017-12-15.
 */

public abstract class GamePanel extends SurfaceView implements SurfaceHolder.Callback, GamePanelInitializer {

    public GamePanel (Context context) {
        super (context);
    }

    public abstract void update ();

}
