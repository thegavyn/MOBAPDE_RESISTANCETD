package edu.dlsu.mobapde.resistancetd;

import android.graphics.Canvas;

/**
 * ResistanceTD
 * Created by Jasper Glen A. Pillejera on 2017-12-15.
 */

public class Tower extends GameEntity {

    private ProjectileSpawner projectileSpawner;
    private Class type;
    private int cooldown;
    private int countdown;
    private static final int maxLevel = 3;
    private int level;
    private int cost;
    private int range;
    private boolean left;

    public Tower(int x, int y, int width, int height, Spawner spawner, ProjectileSpawner projectileSpawner, boolean left) {
        super(x, y, width, height, spawner);
        this.projectileSpawner = projectileSpawner;
        level = 0;
        cost = 0;
        countdown = 0;
        cooldown = 0;
        range = 0;
        this.left = left;
    }

    public int getCost () {
        return cost;
    }

    public int getRange () { return range; }

    public void upgrade () {
        if (level < maxLevel) {
            level++;
            set (type, level);
        }
    }

    public boolean canUpgrade (int cells) {
        return cells >= cost && level < maxLevel;
    }

    public void highlightSpawner () {
        ((TowerSpawner)spawner).setColor ("#50FFFFFF");
    }

    public void unhighlightSpawner () {
        ((TowerSpawner)spawner).setColor ("#50000000");
    }

    public Projectile attack (Enemy enemy) {
        Projectile projectile = null;

        if (countdown == 0) {
            if (type == WBC.class) {
                int dx = enemy.getX() - this.x;
                int dy = enemy.getY() - this.y;
                double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                if (dist <= range) {
                    projectile = projectileSpawner.spawn(type, level);
                    if (projectile != null) {
                        projectile.set(type, level);
                        projectile.set(enemy);
                        countdown = cooldown;
                    }
                }
            }
        }

        return projectile;
    }

    @Override
    public void set(Class c, int level) {
        if (c == WBC.class) {
            this.type = c;
            this.level = level;
            this.icon = WBC.getIcon(level, left);
            this.cost = WBC.getCost(level);
            this.range = WBC.getRange (level);
            this.cooldown = WBC.getCooldown (level);
            spawned = true;
            System.out.println (level);
        }
    }

    @Override
    public void update() {
        if (spawned) {
            if (countdown > 0)
                countdown--;
        }
    }

    @Override
    public void draw(Canvas c) {
        c.drawBitmap(icon, null, rect, paint);
    }

}
