package com.example.markgavin.resistancetd;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Mark Gavin on 11/2/2017.
 */

public class GamePanel extends SurfaceView  implements SurfaceHolder.Callback{

    private MainThread thread;
    private RectBacteria enemy;
    private Point bacteriaPoint;
    private int trixHealth = 100;
    private ArrayList<RectBacteria> enemies = new ArrayList<>();
    private ArrayList<Point> enemyPlaces = new ArrayList<>();
    private ObstacleManager om = new ObstacleManager();
    public GamePanel(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        /*
        player = new RectPlayer(new Rect(100,0,200,100), Color.rgb(0,128,0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2 - player.getRectangle().width()/2
                ,player.getRectangle().height()/2);
        */
        /*
        enemies.add(om.spawnBacterium());
        enemyPlaces.add(new Point(Constants.SCREEN_WIDTH/2 - player.getRectangle().width()/2
                ,player.getRectangle().height()/2));
                */
        enemy = new RectBacteria();
        bacteriaPoint = new Point(Constants.SCREEN_WIDTH/2 - enemy.getRectangle().width()/2
                ,enemy.getRectangle().height()/2);
        setFocusable(true);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;

        while(retry == true)
        {
            try{
                thread.setRunning(false);
                thread.join();
                retry = false;
            }catch(Exception e) {e.printStackTrace();retry = true;}
            retry = false;
        }
    }
    /*

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int)event.getX(), (int)event.getY());
                break;
        }
        return true;
        //return super.onTouchEvent(event);
    }
    */
    public void update()
    {
        /*
        for(int i = 0 ; i < enemies.size(); i++) {
            enemies.get(i).update(enemyPlaces.get(i));
            enemyPlaces.get(i).set(enemyPlaces.get(i).x, enemyPlaces.get(i).y + 5);
        }
        */
        enemy.update(bacteriaPoint);

    }

    @Override
    public void draw(Canvas canvas){

        super.draw(canvas);
        canvas.drawColor(Color.rgb(255, 182, 193));
        /*
        for(int i = 0 ; i < enemies.size(); i++)
            enemies.get(i).draw(canvas);
            */
        enemy.draw(canvas);
    }
}
