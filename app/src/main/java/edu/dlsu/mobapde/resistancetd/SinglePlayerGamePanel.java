package edu.dlsu.mobapde.resistancetd;

import android.content.Context;
import android.view.MotionEvent;
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
	private ArrayList<WhiteSlot> spawnPlaces = new ArrayList<>();
	private int currentCells = 200;
	public SinglePlayerGamePanel(Context context) {
		super(context);

		// from Gavin's code
		getHolder().addCallback(this);
		thread = new MainThread(getHolder(), this);
		spawnBacterium();
		createSlots();
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
	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
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

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		for(WhiteSlot x:spawnPlaces) {
			if ((x.getX() - 100) <= event.getX() && (x.getX() + 100) >= event.getX()
					&& (x.getY() - 100) <= event.getY() && (x.getY() + 100) >= event.getY()) {
				if(!x.isTaken() && currentCells >= 50) {
					spawnTower(x);
					currentCells = currentCells - 50;
				}
				else if(x.isTaken())
				{
					upgradeTower(x);
				}
			}
		}
		return true;
		//return super.onTouchEvent(event);
	}

	public void createSlots()
	{
		int iteratorY = Constants.SCREEN_HEIGHT;
		int iteratorX = Constants.SCREEN_WIDTH;
		for(int i = 0; i < 8; i++) {
			spawnPlaces.add(new WhiteSlot(Constants.SCREEN_WIDTH / 6 - 100, Constants.SCREEN_HEIGHT / 10 + 200*i));
			spawnPlaces.add(new WhiteSlot(Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 10 + 200*i));

			spawnPlaces.add(new WhiteSlot(Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH / 4 + 100, Constants.SCREEN_HEIGHT / 10 + 200*i));
			spawnPlaces.add(new WhiteSlot(Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH / 4 - 100, Constants.SCREEN_HEIGHT / 10 + 200*i));
		}

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

	public void spawnTower(WhiteSlot x) {
		x.setColor(Color.rgb(255, 255, 255));
		x.setLevel(1);
		x.setAttackPower(50);
		x.setTaken(true);
	}

	public void upgradeTower(WhiteSlot x)
	{
		if(x.getLevel() == 1 && currentCells >= 100)
		{
			currentCells = currentCells - 100;
			x.setLevel(2);
			x.setAttackPower(100);
			x.setColor(Color.rgb(206, 20, 73));
		}
		else if(x.getLevel() == 2 && currentCells >= 200)
		{
			currentCells = currentCells - 200;
			x.setLevel(3);
			x.setAttackPower(150);
			x.setColor(Color.rgb(48, 48, 48));
		}
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
		/*
		for(Bacteria f:bacterias)
			if(f.getY() > Constants.SCREEN_HEIGHT)
				bacterias.remove(f);
				*/
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
		for(WhiteSlot x:spawnPlaces){
			paint.setColor(x.getColor());
			Rect r = new Rect(x.getX(), x.getY(), x.getX()+100, x.getY()+100);
			canvas.drawRect(r, paint);
		}
	}
}
