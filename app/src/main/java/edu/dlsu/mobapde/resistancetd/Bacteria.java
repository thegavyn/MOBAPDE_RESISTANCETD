package edu.dlsu.mobapde.resistancetd;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Mark Gavin on 11/15/2017.
 *
 * Edited by Jasper 11/15/2017.
 */

public class Bacteria {

    private static ArrayList<Bitmap> icon = new ArrayList<>();
    private static final int baseMoveSpeed = 30;
    private static final int baseAttackDamage = 20;
    private static final int baseHealth = 50;
    private static final double scaleMoveSpeed = 1;
    private static final double scaleAttackDamage = 1;
    private static final double scaleHealth = 100;
    private static final int maxMoveSpeed = 100;
    private static final int maxAttackDamage = 50000;
    private static final int maxHealth = Integer.MAX_VALUE;
    private static final int baseCells = 5;
    private static final int scaleCells = 1;
    private static final int baseScore = 5;
    private static final int scaleScore = 2;


    public static void setIcon (Bitmap icon) {
        Bacteria.icon.add(icon);
    }

    public static Bitmap getIcon (int level) {
        return Bacteria.icon.get((level - 1) % icon.size());
    }

    public static int getAttackDamage (int level) {
        int damage = (int)(level * scaleAttackDamage + baseAttackDamage);

        if (damage > maxAttackDamage)
            return maxAttackDamage;

        return damage;
    }

    public static int getMoveSpeed (int level) {
        int speed = (int)(level * scaleMoveSpeed + baseMoveSpeed);

        if (speed > maxMoveSpeed)
            return maxMoveSpeed;

        return speed;
    }

    public static int getHealth (int level) {
        long health = (long)((long)level * scaleHealth + baseHealth);

        if (health > maxHealth)
            return maxHealth;

        return (int)health;
    }

    public static int getCells (int level) {
        return baseCells + scaleCells * level;
    }

    public static int getScore (int level) {
        return baseScore + scaleScore * level;
    }

}
