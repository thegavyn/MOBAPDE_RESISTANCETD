package edu.dlsu.mobapde.resistancetd;

import android.graphics.Canvas;

/**
 * Created by Jasper on 11/15/2017.
 */

public interface GameEntity {
    public int getX ();
    public int getY ();
    public void update ();
    public void draw (Canvas c);
}
