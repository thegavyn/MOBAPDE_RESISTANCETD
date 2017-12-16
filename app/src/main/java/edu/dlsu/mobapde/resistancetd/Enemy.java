package edu.dlsu.mobapde.resistancetd;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * ResistanceTD
 * Created by Jasper Glen A. Pillejera on 2017-12-15.
 */

public class Enemy extends GameEntity {

    protected int health;
    protected int moveSpeed;
    protected int attackDamage;
    protected boolean alive;
    protected int cells;
    protected int score;

    public Enemy(int x, int y, int width, int height, Spawner spawner) {
        super(x, y, width, height, spawner);
        moveSpeed = 0;
        attackDamage = 0;
        health = 0;
        alive = false;
    }

    public boolean isAlive () {
        return alive;
    }

    public void attack (int damage) {
        this.health -= damage;
    }

    @Override
    public void set(Class c, int level) {
        if (c == Bacteria.class) {
            moveSpeed = Bacteria.getMoveSpeed(level);
            attackDamage = Bacteria.getAttackDamage(level);
            health = Bacteria.getHealth(level);
            icon = Bacteria.getIcon(level);
            cells = Bacteria.getCells(level);
            score = Bacteria.getScore(level);
            spawned = true;
        }

        alive = true;
    }

    @Override
    public void update() {
        if (alive && spawned) {
            if (health <= 0)
                alive = false;
            y += moveSpeed;
            rect.set(x, y, x + width, y + height);
        }
    }

    @Override
    public void draw(Canvas c) {
        if (alive && spawned)
            c.drawBitmap(icon, null, rect, paint);
    }
}
