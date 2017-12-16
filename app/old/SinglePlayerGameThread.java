package edu.dlsu.mobapde.resistancetd;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Mark Gavin on 11/15/2017.
 *
 * Edited by Jasper 11/15/2017.
 */

public class SinglePlayerGameThread extends Thread {
	public static final int MAX_FPS = 30;
	public static final int NANO_IN_MILLIS = 1000000;
	public static final int MILLIS_PER_SEC = 1000;
    public static final int TARGET_TIME = MILLIS_PER_SEC/MAX_FPS;

	private double averageFPS;
	private SurfaceHolder surfaceHolder;
	private SinglePlayerGamePanel gamePanel;
	private boolean running;
	public static Canvas canvas;

	public SinglePlayerGameThread (SurfaceHolder surfaceHolder, SinglePlayerGamePanel gamePanel) {
		super ();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	public void setRunning (boolean running) {
		this.running = running;
	}

	private void updateAndDraw () {
        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                gamePanel.update();
                gamePanel.draw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void synchronizeTime (long elapsedTime) {
        long syncTime = TARGET_TIME - elapsedTime;

        try {
            if (syncTime > 0)
                Thread.sleep(syncTime);
        } catch (Exception e) { e.printStackTrace(); }
    }

	@Override
	public void run() {
		long startTime;
		int frameCount = 0;
		long totalTime = 0;

		while (running) {
			startTime = System.nanoTime();
			canvas = null;

            updateAndDraw();
            synchronizeTime((System.nanoTime()-startTime)/NANO_IN_MILLIS);

			totalTime += System.nanoTime() - startTime;

			frameCount++;

			if (frameCount == MAX_FPS) {
				averageFPS = MILLIS_PER_SEC/((totalTime/frameCount)/NANO_IN_MILLIS);
				frameCount = 0;
				totalTime = 0;
				System.out.println (averageFPS);
			}
		}

	}


}
