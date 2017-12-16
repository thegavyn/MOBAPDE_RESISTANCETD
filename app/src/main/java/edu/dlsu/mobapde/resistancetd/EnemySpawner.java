package edu.dlsu.mobapde.resistancetd;

/**
 * ResistanceTD
 * Created by Jasper Glen A. Pillejera on 2017-12-12/15/2017.
 */

public class EnemySpawner extends Spawner<Enemy> {

    public EnemySpawner(int x, int y, int width, int height) {
        super(x, y, width, height);
        gameEntities.add(new Enemy(x, y, width, height, this));
    }

}
