package edu.dlsu.mobapde.resistancetd;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * ResistanceTD
 * Created by Jasper Glen A. Pillejera on 2017-12-16.
 */

public class WBC {

    private static double scale;

    private static ArrayList<Bitmap> icon = new ArrayList<>();
    private static ArrayList<Bitmap> rightIcon = new ArrayList<>();

    private static final int baseCost = 100;
    private static final int baseDamage = 5;
    private static final int baseSpeed = 15;
    private static final int baseRange = 200;
    private static final int baseCooldown = 40;

    private static final int scaleCost = 4;
    private static final int scaleDamage = 4;
    private static final int scaleSpeed = 10;
    private static final int scaleRange = 25;
    private static final int scaleCooldown = 4;

    public static Bitmap getIcon(int level, boolean left) {
        if (left)
            return icon.get(level - 1);
        else
            return rightIcon.get(level - 1);
    }

    public static void setIcon(Bitmap icon, boolean left) {
        if (left)
            WBC.icon.add(icon);
        else
            WBC.rightIcon.add(icon);
    }

    public static int getCost (int level) {
        return baseCost * (int)Math.pow(scaleCost, level - 1);
    }

    public static int getDamage (int level) {
        return baseDamage * (int) Math.pow(scaleDamage, level - 1);
    }

    public static int getSpeed (int level) {
        return (int)((baseSpeed + scaleSpeed * (level - 1)) * scale);
    }

    public static int getRange (int level) {
        return (int) ((baseRange + scaleRange * (level - 1)) * scale);
    }

    public static int getCooldown (int level) {
        return baseCooldown - (scaleCooldown * (level - 1));
    }

    public static void setScale(double scale) {
        WBC.scale = scale;
    }

}
