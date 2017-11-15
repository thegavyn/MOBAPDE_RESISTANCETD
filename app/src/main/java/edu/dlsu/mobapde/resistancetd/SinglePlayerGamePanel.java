package edu.dlsu.mobapde.resistancetd;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Merged by Jo w/ Gavin's CleanCanvas on 11/15/2017
 */

public class SinglePlayerGamePanel extends SurfaceView implements SurfaceHolder.Callback, GamePanelInitializer {

	private static final int MAX_INIT = 100;
	private int currInit;

	// from Gavin's code
	private MainThread thread;
	private ArrayList<Bacteria> bacterias = new ArrayList<>();

	public SinglePlayerGamePanel(Context context) {
		super(context);

		// from Gavin's code
		getHolder().addCallback(this);
		thread = new MainThread(getHolder(), this);
		spawnBacterium();
		setFocusable(true);
	}

	@Override
	public void initialize() {
		currInit = 0;

		new Thread(){
			@Override
			public void run() {
				try {
					for( ; currInit <MAX_INIT; currInit++)
						Thread.sleep(20);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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


	// from Gavin's code
	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		thread = new MainThread(getHolder(), this);
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
		tryDrawing(surfaceHolder);
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

	/* ------------------------- WARNING -------------------------
	 * ALL OF THE FOLLOWING CODE
	 * WERE DERIVED FROM CleanCanvas (Gavin)
	 * ----------------------------------------------------------- */

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
	}
}
