package edu.dlsu.mobapde.resistancetd;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Mark Gavin on 11/15/2017.
 *
 * Edited by Jasper 11/15/2017.
 */

public class Bacteria implements GameEntity {

    private int x, y, width, height, moveSpeed;
    private Bitmap icon;
    private Rect rect;

    public Bacteria(int x, int y, int width, int height, int moveSpeed, Bitmap icon) {
        this.x = x;
        this.y = y;
        this.moveSpeed = moveSpeed;
        this.width = width;
        this.height = height;
        this.icon = icon;
        rect = new Rect();
        rect.set(x, y, x + width, y + height);
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    @Override
    public void update() {
        y += moveSpeed;
        rect.set(x, y, x + width, y + height);
    }

    @Override
    public void draw(Canvas c) {
        c.drawBitmap(icon, null, rect, new Paint());
    }
}
