package edu.dlsu.mobapde.resistancetd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;

public class SinglePlayerGamePanel extends SurfaceView implements SurfaceHolder.Callback, GamePanelInitializer {

	public static final int MAX_INIT = 8;

    public final double scale;
	private int currInit;
    private Bitmap bacteriaIcon;
    private SinglePlayerGameThread gameThread;

    private Spawner spawner;
    private ArrayList<GameEntity> entities;

	public SinglePlayerGamePanel(Context context) {
		super(context);

        scale = getResources().getDisplayMetrics().density;

		getHolder().addCallback(this);
		gameThread = new SinglePlayerGameThread(getHolder(), this);
		setFocusable(true);
	}

	@Override
	public void initialize() {
		currInit = 0;

		new Thread() {

            private void initUpdate () {
                currInit ++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

			@Override
			public void run() {
                bacteriaIcon = BitmapFactory.decodeResource(getResources(), R.drawable.game_logo_icon);
                initUpdate ();

                entities = new ArrayList<>();
                initUpdate ();

                int x = (int)(Constants.SCREEN_WIDTH/2 - 50*scale);
                initUpdate ();

                int y = (int) -(100*scale);
                initUpdate ();

                int width = (int) (100*scale);
                initUpdate ();

                int height = (int) (100*scale);
                initUpdate ();

                spawner = new Spawner(x, y, width, height);
                GameEntity e = spawner.spawn(Bacteria.class, bacteriaIcon);
                initUpdate ();

                if(e != null)
                    entities.add (e);

				currInit = MAX_INIT;
			}
		}.start();

	}

	@Override
	public int status() {
		return (int)Math.floor(currInit * 100.0 /MAX_INIT);
	}

	@Override
	public boolean isInitialized() {
		return currInit == MAX_INIT;
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		gameThread = new SinglePlayerGameThread(getHolder(), this);

		gameThread.setRunning (true);
		gameThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		boolean retry = true;
        gameThread.setRunning (false);
		while (retry) {
			try {
				gameThread.join();
                retry = false;
			} catch (Exception e) { e.printStackTrace(); }
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}

	public void update () {
        for (GameEntity ge: entities)
            ge.update();

        Iterator<GameEntity> iter = entities.iterator();

        boolean removed = false;
        while (iter.hasNext()) {
            GameEntity ge = iter.next();

            if (ge.getY() > Constants.SCREEN_HEIGHT) {
                iter.remove();
                System.out.println ("Bacteria Removed");
                removed = true;
            }
        }

        if (removed) {
            GameEntity ge = spawner.spawn(Bacteria.class, bacteriaIcon);

            entities.add (ge);
        }

	}

	@Override
	public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor (Color.rgb(255, 182, 193));

        for (GameEntity e: entities)
            e.draw(canvas);
	}
}
