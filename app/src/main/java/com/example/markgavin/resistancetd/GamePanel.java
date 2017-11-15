package com.example.markgavin.resistancetd;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Mark Gavin on 11/2/2017.
 */

public class GamePanel extends SurfaceView  implements SurfaceHolder.Callback{

    private MainThread thread;
    private ArrayList<Bacteria> bacterias = new ArrayList<>();
    private ArrayList<whiteSlot> spawnPlaces = new ArrayList<>();
    public GamePanel(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        spawnBacterium();
        createSlots();
        setFocusable(true);

    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        tryDrawing(holder);
    }

    private void tryDrawing(SurfaceHolder holder) {
        Log.i("", "Trying to draw...");

        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            Log.e("", "Cannot draw onto the canvas as it's null");
        } else {
            draw(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }
    public void spawnBacterium() {
        bacterias.add(new Bacteria(Constants.SCREEN_WIDTH/2-50, 0,5));
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
    public void createSlots()
    {
        int iteratorY = Constants.SCREEN_HEIGHT;
        int iteratorX = Constants.SCREEN_WIDTH;
        for(int i = 0; i < 8; i++) {
            spawnPlaces.add(new whiteSlot(Constants.SCREEN_WIDTH / 6 - 100, Constants.SCREEN_HEIGHT / 10 + 200*i));
            spawnPlaces.add(new whiteSlot(Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 10 + 200*i));

            spawnPlaces.add(new whiteSlot(Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH / 4 + 100, Constants.SCREEN_HEIGHT / 10 + 200*i));
            spawnPlaces.add(new whiteSlot(Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH / 4 - 100, Constants.SCREEN_HEIGHT / 10 + 200*i));
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        for(whiteSlot x:spawnPlaces) {
            if ((x.getX() - 100) <= event.getX() && (x.getX() + 100) >= event.getX()
                    && (x.getY() - 100) <= event.getY() && (x.getY() + 100) >= event.getY())
                spawnTower(x);
        }
        return true;
        //return super.onTouchEvent(event);
    }

    public void spawnTower(whiteSlot x) {
        x.setColor(Color.rgb(255, 255, 255));
        x.setLevel(1);
        x.setAttackPower(50);
        x.setTaken(true);
    }

    public void update()
    {

    }
    public void moveBacteriaDown(){
        for(Bacteria b: bacterias){
            b.moveDown();
        }
    }
    public void removeOut()
    {
        for(Bacteria f:bacterias)
            if(f.getY() > Constants.SCREEN_HEIGHT)
                bacterias.remove(f);
    }
    @Override
    public void draw(Canvas canvas){

        super.draw(canvas);
        Paint paint = new Paint();
        canvas.drawColor(Color.rgb(255, 182, 193));
        removeOut();
        for(Bacteria f:bacterias){
            paint.setColor(f.getColor());
            Rect r = new Rect(f.getX(), f.getY(), f.getX()+100, f.getY()+100);
            canvas.drawRect(r, paint);
            }
        for(whiteSlot x:spawnPlaces){
            paint.setColor(x.getColor());
            Rect r = new Rect(x.getX(), x.getY(), x.getX()+100, x.getY()+100);
            canvas.drawRect(r, paint);
        }
    }
}
