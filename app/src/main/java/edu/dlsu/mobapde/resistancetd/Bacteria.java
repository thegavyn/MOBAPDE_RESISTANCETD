package edu.dlsu.mobapde.resistancetd;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Mark Gavin on 11/15/2017.
 *
 * Edited by Jasper 12/15/2017.
 */

public class Bacteria {

    private static int height;
    private static double scale;
    private static ArrayList<Bitmap> icon = new ArrayList<>();

    private static final int baseMoveSpeed = 3;
    private static final int baseAttackDamage = 1;
    private static final int baseHealth = 70;
    private static final int baseCells = 5;
    private static final int baseScore = 3;

    private static final double scaleMoveSpeed = 1;
    private static final double scaleAttackDamage = 1;
    private static final double scaleHealth = 10;
    private static final int scaleCells = 1;
    private static final int scaleScore = 2;

    private static final int maxMoveSpeed = 10;
    private static final int maxAttackDamage = 100;
    private static final int maxHealth = 50000;

    public static void setIcon (Bitmap icon) {
        Bacteria.icon.add(icon);
    }

    public static Bitmap getIcon (int level) {
        return Bacteria.icon.get((level - 1) % icon.size());
    }

    public static int getAttackDamage (int level) {
        int damage = (int)((level - 1) * scaleAttackDamage + baseAttackDamage);

        if (damage > maxAttackDamage)
            return maxAttackDamage;

        return damage;
    }

    public static int getMoveSpeed (int level) {
        int speed = (int) ((level - 1) * scaleMoveSpeed + baseMoveSpeed);

        if (speed > maxMoveSpeed)
            speed = maxMoveSpeed;

        if (speed != 0)
            scale = height / (1920.0/speed);

        speed = (int) (scale);

        return speed;
    }

    public static int getHealth (int level) {
        int health = (int)((level-1) * scaleHealth + baseHealth);

        if (health > maxHealth)
            return maxHealth;

        return health;
    }

    public static int getCells (int level) {
        return baseCells + scaleCells * (level - 1);
    }

    public static int getScore (int level) {
        return baseScore + scaleScore * (level - 1);
    }

    public static void setScale (double scale) {
        Bacteria.scale = scale;
    }

    public static void setHeight (int height) {
        Bacteria.height = height;
    }

}
