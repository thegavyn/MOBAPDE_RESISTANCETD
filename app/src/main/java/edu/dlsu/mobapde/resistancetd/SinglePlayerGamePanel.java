package edu.dlsu.mobapde.resistancetd;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    private ArrayList<WhiteSlot> spawnPlaces = new ArrayList<>();
    private int currentCells = 200;
    private Spawner spawner;
    private ArrayList<GameEntity> entities;

	public SinglePlayerGamePanel(Context context) {
		super(context);

        scale = getResources().getDisplayMetrics().density;

		getHolder().addCallback(this);
		gameThread = new SinglePlayerGameThread(getHolder(), this);
        createSlots();
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
    public void createSlots()
    {
        int iteratorY = Constants.SCREEN_HEIGHT;
        int iteratorX = Constants.SCREEN_WIDTH;
        for(int i = 0; i < 8; i++) {
            spawnPlaces.add(new WhiteSlot(Constants.SCREEN_WIDTH / 6 - 100, Constants.SCREEN_HEIGHT / 10 + 200*i,
                    Constants.SCREEN_WIDTH/2));
            spawnPlaces.add(new WhiteSlot(Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 10 + 200*i,
                    Constants.SCREEN_WIDTH/4));

            spawnPlaces.add(new WhiteSlot(Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH / 4 + 100, Constants.SCREEN_HEIGHT / 10 + 200*i,
                    Constants.SCREEN_WIDTH/2));
            spawnPlaces.add(new WhiteSlot(Constants.SCREEN_WIDTH - Constants.SCREEN_WIDTH / 4 - 100, Constants.SCREEN_HEIGHT / 10 + 200*i,
                    Constants.SCREEN_WIDTH/4));
        }

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

    public void detectBacteria()
    {

        for(WhiteSlot x:spawnPlaces) {

            for (GameEntity f: entities){
                if ((f.getX() - x.getX())*(f.getX() - x.getX()) +
                        (f.getY() - x.getY())*(f.getY() - x.getY()) <= x.getRange()*x.getRange())
                    System.out.println("Pumasok");
                /*
                if(x.getX()+50+x.getRange() >= f.getX() && x.getX()-50+x.getRange() <= f.getX()
                        && x.getY()+50+x.getRange() >= f.getY() && x.getY()-50+x.getRange() <= f.getY())
                    System.out.println("HELLO I AM THE CARABAO");
                    */

            }
        }

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
		return false;
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
    detectBacteria();
	}

	@Override
	public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor (Color.rgb(255, 182, 193));
        Paint paint = new Paint();
        for (GameEntity e: entities)
            e.draw(canvas);

        for(WhiteSlot x:spawnPlaces){
            paint.setColor(x.getColor());
            Rect r = new Rect(x.getX(), x.getY(), x.getX()+100, x.getY()+100);
            canvas.drawRect(r, paint);
        }
	}
}
