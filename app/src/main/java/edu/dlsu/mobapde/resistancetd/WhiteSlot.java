package edu.dlsu.mobapde.resistancetd;

import android.graphics.Color;

/**
 * Created by Mark Gavin on 11/15/2017.
 */

public class WhiteSlot {
    private int x;
    private int y;
    private int color;
    private boolean isTaken;
    private int attackPower;
    private int level;

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public WhiteSlot(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = Color.rgb(255, 244, 124);
        isTaken = false;
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

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}