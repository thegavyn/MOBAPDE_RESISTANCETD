package edu.dlsu.mobapde.resistancetd;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Jasper on 12/7/2017.
 */

public class GameThread extends Thread {
    private static final int MAX_FPS = 60;
    private static final int MAX_LOSS = 5;
    private static final int NANOS_PER_MILLIS = 1000000;
    private static final int MILLIS_PER_SEC = 1000;
    private static final int FRAME_PERIOD = MILLIS_PER_SEC/MAX_FPS;

    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    private static Canvas canvas;

    public GameThread (SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super ();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning (boolean running) {
        this.running = running;
    }

    private void updateAndDraw () {
        gamePanel.update ();
        gamePanel.draw (canvas);
    }

    public void run () {
        long timeBegin, timeDiff;
        int timeSleep, frameLoss;

        while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas ();
                synchronized (surfaceHolder) {
                    timeBegin = System.nanoTime()/NANOS_PER_MILLIS;
                    frameLoss = 0;

                    updateAndDraw();

                    timeDiff = System.nanoTime()/NANOS_PER_MILLIS - timeBegin;
                    timeSleep = (int)(FRAME_PERIOD - timeDiff);

                    if (timeSleep > 0) {
                        try {
                            Thread.sleep(timeSleep);
                        } catch (InterruptedException e) {}
                    }

                    while (timeSleep < 0 && frameLoss < MAX_LOSS) {
                        gamePanel.update ();
                        timeSleep += FRAME_PERIOD;
                        frameLoss++;
                    }
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost (canvas);
                }
            }

        }

    }

}
