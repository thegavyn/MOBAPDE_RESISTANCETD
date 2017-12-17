package edu.dlsu.mobapde.resistancetd;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * ResistanceTD
 * Created by Jasper Glen A. Pillejera on 2017-12-16.
 */

public class Projectile extends GameEntity {

    private Enemy e;
    private int dx;
    private int dy;
    private double dist;
    private int vx;
    private int vy;
    private int r;
    private int damage;
    private int speed;

    public Projectile(int x, int y, int width, int height, Spawner spawner) {
        super(x, y, width, height, spawner);
        r = (width + height)/16;
    }

    public boolean hit () {
        return e.intersects(x, y);
    }

    public void attack () {
        e.attack (damage);
        spawned = false;
    }

    public void set (Enemy e) {
        this.e = e;
    }

    @Override
    public void set(Class c, int level) {
        if (c == WBC.class) {
            spawned = true;
            speed = WBC.getSpeed(level);
            damage = WBC.getDamage(level);
            if (level == 1)
                paint.setColor(Color.parseColor("#AAAAAA"));
            else if (level == 2)
                paint.setColor(Color.parseColor("#e8d458"));
            else if (level == 3)
                paint.setColor(Color.parseColor("#F7525C"));
        }
    }

    @Override
    public void update() {
        if (!e.isAlive() || !e.spawned)
            despawn();
        if(spawned) {
            dx = e.getX() + e.width/2 - this.x;
            dy = e.getY() + e.height/2 - this.y;
            dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            vx = (int)(dx / dist * speed);
            vy = (int)(dy / dist * speed);
            this.x += this.vx;
            this.y += this.vy;
        }
    }

    @Override
    public void draw(Canvas c) {
        if(spawned) {
            c.drawCircle(x, y, r, paint);
        }
    }

}
