package com.example.markgavin.resistancetd;


import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Mark Gavin on 11/12/2017.
 */

public class ObstacleManager {

    public ObstacleManager() {

    }

    public RectBacteria spawnBacterium()
    {
        RectBacteria spawnB = new RectBacteria();
        return spawnB;
    }
    public void update(RectBacteria rb, Point p)
    {
        rb.update(p);
    }
}
