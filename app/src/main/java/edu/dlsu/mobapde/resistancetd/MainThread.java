package edu.dlsu.mobapde.resistancetd;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mark Gavin on 11/2/2017.
 */

public class MainThread extends Thread {
    public static final int MAX_FPS = 30;
    private double AVE_FPS;
    private SurfaceHolder surfaceHolder;
    private SinglePlayerGamePanel gamePanel; // edited from Jasper's code
    private boolean running;
    private int howMany = 0;
    Timer timer = new Timer();
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, SinglePlayerGamePanel gamePanel)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running)
    {
        this.running = true;
    }

    @Override
    public void run()
    {
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                    this.gamePanel.moveBacteriaDown();

                    if(howMany % 5 == 0 && howMany != 0) {
                        this.gamePanel.spawnBacterium();
                    }
                }
            }catch(Exception e) {e.printStackTrace();}
            finally{
                if(canvas != null)
                {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e) {e.printStackTrace();}
                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try{
                if(waitTime > 0)
                    this.sleep(waitTime);
            }catch(Exception e) {e.printStackTrace();}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == MAX_FPS){
                AVE_FPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(AVE_FPS);
                howMany++;
            }

        }
    }
}