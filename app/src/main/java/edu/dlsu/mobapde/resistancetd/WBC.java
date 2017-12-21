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
    private static final int baseDamage = 6;
    private static final int baseSpeed = 6;
    private static int baseRange;
    private static final int baseCooldown = 25;

    private static final int scaleCost = 4;
    private static final int scaleDamage = 4;
    private static final int scaleSpeed = 1;
    private static int scaleRange;
    private static final int scaleCooldown = 2;

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
        double speed = baseSpeed;
        int sc = scaleSpeed * (level - 1);
        speed += sc;
        speed *= scale;

        return (int)speed;
    }

    public static int getRange (int level) {
        double range = baseRange;
        int sc = scaleRange * (level - 1);
        range += sc;

        return (int)range;
    }

    public static int getCooldown (int level) {
        return baseCooldown - (scaleCooldown * (level - 1));
    }

    public static void setScale(double scale) {
        WBC.scale = scale;
    }

    public static void setBaseRange (int range) {
        WBC.scaleRange = range/16;
        WBC.baseRange = range;
    }

}
