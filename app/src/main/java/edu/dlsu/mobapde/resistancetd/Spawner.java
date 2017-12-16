package edu.dlsu.mobapde.resistancetd;

import java.util.ArrayList;
import java.util.List;

/**
 * Resistance TD
 * Created by Jasper Glen A. Pillejera on 11/15/2017.
 */

public abstract class Spawner<T extends GameEntity> {

    protected int x, y, width, height;
    protected List<T> gameEntities;

    public Spawner (int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        gameEntities = new ArrayList<>();
    }

    public T spawn (Class c, int level) {
        T entity = null;

        if (gameEntities.size() > 0) {
            entity = gameEntities.remove(0);
            entity.set(c, level);
        }

        return entity;
    }

    public void despawn (T gameEntity) {
        gameEntity.set(x, y, width, height);
        gameEntities.add(gameEntity);
    }

    public boolean canSpawn () {
        return gameEntities.size() > 0;
    }

}
