package edu.dlsu.mobapde.resistancetd;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Jasper on 11/15/2017.
 */

public abstract class GameEntity {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Bitmap icon;
    protected Rect rect;
    protected boolean spawned;
    protected Spawner spawner;
    protected Paint paint;

    public GameEntity(int x, int y, int width, int height, Spawner spawner) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.spawner = spawner;
        spawned = false;
        rect = new Rect();
        rect.set(x, y, x + width, y + height);
        paint = new Paint();
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public void set (int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @SuppressWarnings("unchecked")
    public void despawn () {
        spawned = false;
        spawner.despawn(this);
    }

    public boolean intersects (float px, float py) {
        if (px >= x && px <= x + width &&
                py >= y && py <= y + height)
            return true;

        return false;
    }

    public abstract void set (Class c, int level);
    public abstract void update ();
    public abstract void draw (Canvas c);
}
