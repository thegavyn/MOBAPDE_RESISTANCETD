package edu.dlsu.mobapde.resistancetd;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SinglePlayerGamePanel extends SurfaceView implements SurfaceHolder.Callback, GamePanelInitializer {

	private static final int MAX_INIT = 100;
	private int currInit;

	public SinglePlayerGamePanel(Context context) {
		super(context);
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

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

	}

}
