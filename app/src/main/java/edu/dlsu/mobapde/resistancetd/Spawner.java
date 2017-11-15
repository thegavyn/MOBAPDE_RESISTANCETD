package edu.dlsu.mobapde.resistancetd;

import android.graphics.Bitmap;

/**
 * Created by Jasper on 11/15/2017.
 */

public class Spawner {

    private int x, y, width, height;
    private static final long COOLDOWN = 0;
    private long lastSpawn;

    public Spawner (int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public GameEntity spawn (Class c, Bitmap icon) {
        if (System.nanoTime () - lastSpawn < COOLDOWN)
            return null;

        lastSpawn = System.nanoTime();

        if (c == Bacteria.class) {
            return new Bacteria(x,y,width,height,8,icon);
        }

        return null;
    }


}
