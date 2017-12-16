package edu.dlsu.mobapde.resistancetd;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * ResistanceTD
 * Created by Jasper Glen A. Pillejera on 2017-12-16.
 */

public class WBC {

    private static ArrayList<Bitmap> icon = new ArrayList<>();
    private static final int baseCost = 46;
    private static final int scaleCost = 4;
    private static final int baseDamage = 50;
    private static final int scaleDamage = 70;
    private static final int baseSpeed = 25;
    private static final int scaleSpeed = 15;
    private static final int baseRange = 700;
    private static final int scaleRange = 100;
    private static final int baseCooldown = 20;
    private static final int scaleCooldown = 1;

    public static Bitmap getIcon(int level) {
        return icon.get(level - 1);
    }

    public static void setIcon(Bitmap icon) {
        WBC.icon.add(icon);
    }

    public static int getCost (int level) {
        return baseCost + scaleCost * level;
    }

    public static int getDamage (int level) {
        return baseDamage + scaleDamage * level;
    }

    public static int getSpeed (int level) {
        return baseSpeed + scaleSpeed * level;
    }

    public static int getRange (int level) {
        return baseRange + scaleRange * level;
    }

    public static int getCooldown (int level) {
        return baseCooldown - (scaleCooldown * level);
    }

}
