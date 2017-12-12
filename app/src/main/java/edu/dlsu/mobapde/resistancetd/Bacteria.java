package edu.dlsu.mobapde.resistancetd;

import android.graphics.Color;
import android.graphics.Rect;

        import android.graphics.Rect;

/**
 * Created by Mark Gavin on 11/15/2017.
 */

public class Bacteria {
    private int x;
    private int y;
    private int color;
    private Rect rect;
    private int pace;
    private int attackPower;
    private int range;

    public Bacteria(int x, int y, int pace) {
        this.x = x;
        this.y = y;
        this.color = Color.rgb(0, 128, 0);;
        this.pace = pace;
    }

    public Bacteria(int x, int y, int color, Rect rect) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.rect = rect;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getRange() {
        return range;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void moveDown(){
        this.y+=this.pace;
    }

    public Bacteria(Rect rect) {
        this.rect = rect;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}

