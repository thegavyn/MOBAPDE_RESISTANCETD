package edu.dlsu.mobapde.resistancetd;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * ResistanceTD
 * Created by Jasper Glen A. Pillejera on 2017-12-12/15/2017.
 */

public class TowerSpawner extends Spawner<Tower> {

    private ProjectileSpawner projectileSpawner;
    private Rect rect;
    private Paint paint;
    private int cx, cy, r;

    public TowerSpawner(int x, int y, int width, int height, boolean left) {
        super(x, y, width, height);
        projectileSpawner = new ProjectileSpawner(x, y, width, height);
        gameEntities.add(new Tower (x, y, width, height, this, projectileSpawner, left));

        rect = new Rect();
        rect.set(x, y, x + width, y + height);
        paint = new Paint();
        paint.setColor(Color.parseColor("#50000000"));

        cx = x + width/2;
        cy = y + height/2;
        r = (width + height)/4;
    }

    public void setColor (String str) {
        paint.setColor(Color.parseColor(str));
    }

    public void draw (Canvas c) {
        c.drawCircle(cx, cy, r, paint);
    }

    public boolean intersects (float px, float py) {
        return  (px >= x && px <= (x + width) &&
                py >= y && py <= (y + height));
    }

}
