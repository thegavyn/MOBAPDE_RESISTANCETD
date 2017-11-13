package com.example.markgavin.resistancetd;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Mark Gavin on 11/13/2017.
 */

public class RectBacteria implements GameObject {

    private Rect rectangle;
    private int color;
    private int damage;
    private int health;
    public Point enemyPoint;


    public RectBacteria() {
        this.rectangle = new Rect(100,0,200,100);
        this.color = Color.rgb(0, 128, 0);
        this.damage = Constants.blevelOne_Hit;
        this.health = Constants.blevelOne_Health;
        enemyPoint = new Point(Constants.SCREEN_WIDTH/2 - rectangle.width()/2
                ,rectangle.height()/2);
    }

    public Rect getRectangle() {
        return rectangle;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point) {
        rectangle.set(point.x - rectangle.width() / 2,
                point.y - rectangle.height() / 2 , point.x + rectangle.width() / 2,
                point.y + rectangle.height() / 2 );//left, top, right, bottom
    }
}
