package edu.dlsu.mobapde.resistancetd;

/**
 * ResistanceTD
 * Created by Jasper Glen A. Pillejera on 2017-12-16.
 */

public class ProjectileSpawner extends Spawner<Projectile> {
    public ProjectileSpawner(int x, int y, int width, int height) {
        super(x, y, width, height);
        for (int i = 0; i < 10; i++)
            gameEntities.add(new Projectile(x + width/2, y + height/2, width, height, this));
    }
}
