package com.example.markgavin.resistancetd;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Mark Gavin on 11/7/2017.
 */

public class Obstacle extends GameObject{

    private Rect rectangle;
    private int color;

    public Obstacle(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    public boolean playerCollide(RectPlayer player)
    {
        if(rectangle.contains(player.getRectangle().left, player.getRectangle().top) ||
                rectangle.contains(player.getRectangle().right, player.getRectangle().top) ||
                rectangle.contains(player.getRectangle().left, player.getRectangle().bottom) ||
                rectangle.contains(player.getRectangle().right, player.getRectangle().bottom))
            return true;
        else
            return false;
    }
}
