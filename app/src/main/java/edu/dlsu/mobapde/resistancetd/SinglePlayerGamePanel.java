package edu.dlsu.mobapde.resistancetd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Iterator;

public class SinglePlayerGamePanel extends GamePanel {

    //initialization
	public static final int MAX_INIT = 100;
    private int currInit;

    //screen values
    private final int screenWidth;
    private final int screenHeight;
    private final double scale;

    //game loop
    private GameThread gameThread;

    //game resources
    private static final int gameEntityWidth = 70;
    private static final int gameEntityHeight = 70;
    private static final int textSize = 20;
    private static final int strokeWidth = 10;
    private final int scaledEntityWidth;
    private final int scaledEntityHeight;
    private final int scaledTextSize;
    private final int scaledStrokeWidth;
    private Bitmap bacteriaIcon;
    private Bitmap bacteriaIcon1;
    private Bitmap whiteBloodCellIcon;
    private Bitmap whiteBloodCellIcon1;
    private Bitmap whiteBloodCellIcon2;
    private Bitmap backgroundIcon;
    private Rect bgRect;

    // stage text
    private int stageLevel;
    private String stageString;
    private float stageLevelX;
    private float stageLevelY;
    private Paint stageLevelPaint;

    // trix text
    private int trixHP;
    private String trixString;
    private float trixHPX;
    private float trixHPY;
    private Paint trixHPPaint;

    // cells text
    private int cells;
    private String cellsString;
    private float cellsX;
    private float cellsY;
    private Paint cellsPaint;

    // score text
    private int score;
    private String scoreString;
    private float scoreX;
    private float scoreY;
    private Paint scorePaint;

    //game entities
    private final ArrayList<EnemySpawner> enemySpawners;
    private final ArrayList<TowerSpawner> towerSpawners;
    private final ArrayList<Tower> towers;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Projectile> projectiles;

    //sfx
    private BackgroundMusicManager bmm;
    private SoundEffectsManager sem;

    // main activity
    private MainActivity mainActivity;

	public SinglePlayerGamePanel(Context context) {
		super(context);

		mainActivity = (MainActivity)context;

		DisplayMetrics dm = getResources().getDisplayMetrics();
		scale = dm.density;
		screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        scaledEntityWidth = (int)(gameEntityWidth * scale);
        scaledEntityHeight = (int)(gameEntityHeight * scale);
        scaledTextSize = (int)(textSize * scale);
        scaledStrokeWidth = (int)(strokeWidth * scale);

        enemySpawners = new ArrayList<>();
        towerSpawners = new ArrayList<>();
        towers = new ArrayList<>();
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();

        WBC.setScale(scale);
        WBC.setBaseRange((screenWidth/2));
        Bacteria.setScale(scale);

        // Background Music
        bmm = new BackgroundMusicManager(context);
        bmm.playMusic(BackgroundMusicManager.GAME_PANEL);

        // Sound effects
        sem = new SoundEffectsManager(context);

        getHolder().addCallback(this);
        getHolder().setFixedSize(screenWidth, screenHeight);
        setFocusable(true);
	}

	@Override
	public void initialize() {
		currInit = 0;

		new Thread() {

		    private void initIcons () {
                bgRect = new Rect();
                bgRect.set(0, 0, screenWidth, screenHeight);

                bacteriaIcon = BitmapFactory.decodeResource(getResources(), R.drawable.bac_1);
                bacteriaIcon1 = BitmapFactory.decodeResource(getResources(), R.drawable.bac_2);
                whiteBloodCellIcon = BitmapFactory.decodeResource(getResources(), R.drawable.wbc_lvl1);
                whiteBloodCellIcon1 = BitmapFactory.decodeResource(getResources(), R.drawable.wbc_lvl2);
                whiteBloodCellIcon2 = BitmapFactory.decodeResource(getResources(), R.drawable.wbc_lvl3);
                backgroundIcon = BitmapFactory.decodeResource(getResources(), R.drawable.bloodvessel_bg);

                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f, 1.0f);

                Bacteria.setIcon(bacteriaIcon);
                Bacteria.setIcon(bacteriaIcon1);
                WBC.setIcon(whiteBloodCellIcon, true);
                WBC.setIcon(whiteBloodCellIcon1, true);
                WBC.setIcon(whiteBloodCellIcon2, true);
                WBC.setIcon(
                        Bitmap.createBitmap(
                                whiteBloodCellIcon, 0, 0,
                                whiteBloodCellIcon.getWidth(), whiteBloodCellIcon.getHeight(),
                                matrix, true), false);
                WBC.setIcon(
                        Bitmap.createBitmap(
                                whiteBloodCellIcon1, 0, 0,
                                whiteBloodCellIcon1.getWidth(), whiteBloodCellIcon1.getHeight(),
                                matrix, true), false);
                WBC.setIcon(
                        Bitmap.createBitmap(
                                whiteBloodCellIcon2, 0, 0,
                                whiteBloodCellIcon2.getWidth(), whiteBloodCellIcon2.getHeight(),
                                matrix, true), false);

                initUpdate();
            }

            private void initEnemySpawners () {
                int x = screenWidth/2 - scaledEntityWidth/2;
                int y = -scaledEntityHeight * 2;

                for (int i = 0; i < 15; i++) {
                    enemySpawners.add(new EnemySpawner(x, y, scaledEntityWidth, scaledEntityHeight));
                    y -= scaledEntityHeight;
                    y -= scaledEntityHeight;
                }

                initUpdate();
            }

            private void initTowerSpawner () {
		        int pad = (int) (10 * scale);
		        int w = 3*scaledEntityWidth/4;
		        int h = 3*scaledEntityHeight/4;
                int y = (screenHeight - pad * 8 - h * 8) / 2;

		        for (int i = 0; i < 8; i++) {
                    towerSpawners.add(new TowerSpawner(pad,y + i *(h + pad), w, h, true));
                    towerSpawners.add(new TowerSpawner(w + 2 * pad, y + i *(h + pad), w, h, true));
                    towerSpawners.add(new TowerSpawner(screenWidth - 2 * (pad + w), y + i *(h + pad), w, h, false));
                    towerSpawners.add(new TowerSpawner(screenWidth - w - pad, y + i *(h + pad), w, h, false));
                }

                initUpdate();
            }

            private void initScore () {
                score = 0;
                scoreX = (float)(60 * scale);
                scoreY = (float)(30 * scale);
                scorePaint = new Paint();
                scorePaint.setColor(Color.WHITE);
                scorePaint.setTextSize(scaledTextSize);
                scorePaint.setStrokeWidth(scaledStrokeWidth);
                scorePaint.setTextAlign(Paint.Align.CENTER);
                scorePaint.setStyle(Paint.Style.FILL);

                initUpdate();
            }

            private void initCells () {
                cells = 400;
                cellsX = (float)(60 * scale);
                cellsY = (float)(60 * scale);
                cellsPaint = new Paint();
                cellsPaint.setColor(Color.WHITE);
                cellsPaint.setTextSize(scaledTextSize);
                cellsPaint.setStrokeWidth(scaledStrokeWidth);
                cellsPaint.setTextAlign(Paint.Align.CENTER);
                cellsPaint.setStyle(Paint.Style.FILL);

                initUpdate();
            }

            private void initStageLevel () {
                stageLevel = 0;
                stageLevelX = (float)(60 * scale);
                stageLevelY = (float)(30 * scale);
                stageLevelPaint = new Paint();
                stageLevelPaint.setColor(Color.WHITE);
                stageLevelPaint.setTextSize(scaledTextSize);
                stageLevelPaint.setStrokeWidth(scaledStrokeWidth);
                stageLevelPaint.setTextAlign(Paint.Align.CENTER);
                stageLevelPaint.setStyle(Paint.Style.FILL);
                initUpdate();
            }

            private void initTrix () {
                trixHP = 100;
                trixHPX = (float)(60 * scale);
                trixHPY = (float)(60 * scale);
                trixHPPaint = new Paint();
                trixHPPaint.setColor(Color.WHITE);
                trixHPPaint.setTextSize(scaledTextSize);
                trixHPPaint.setStrokeWidth(scaledStrokeWidth);
                trixHPPaint.setTextAlign(Paint.Align.CENTER);
                trixHPPaint.setStyle(Paint.Style.FILL);

                initUpdate();
            }

            private void initUpdate () {
                currInit += 12;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

			@Override
			public void run() {
		        initIcons();
		        initEnemySpawners();
		        initTowerSpawner();
                initScore();
                initCells();
                initStageLevel();
                initTrix();
                spawnEnemies();
                initUpdate();

                currInit = MAX_INIT - 1;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
		gameThread = new GameThread(getHolder(), this);

		gameThread.setRunning (true);
		gameThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {}

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
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {

            float x = event.getX(), y = event.getY();

            synchronized (towers) {
                if (!towers.isEmpty()) {
                    for (Tower t : towers) {
                        if (t.intersects(x, y) && t.canUpgrade(cells)) {
                            cells -= t.getCost();
                            t.upgrade();
                        }
                    }
                }
            }

            synchronized (towerSpawners) {
                for (TowerSpawner ts : towerSpawners) {

                    if (ts.intersects(x, y) && ts.canSpawn() &&
                            WBC.getCost(1) <= cells) {
                            Tower t = ts.spawn(WBC.class, 1);

                            if (t != null) {
                                synchronized (towers) {
                                    sem.playSFX(SoundEffectsManager.BUILD1);
                                    towers.add(t);
                                    cells -= WBC.getCost(1);
                                }
                            }

                    }

                }
            }

        }

		return false;
	}

    public void pause () {
	    gameThread.pause();
    }

    public void resume () {
	    gameThread.unpause();
    }

    public void spawnEnemies () {
	    synchronized (enemySpawners) {
            boolean nextRound = true;
            for (EnemySpawner enemySpawner : enemySpawners) {
                if (!enemySpawner.canSpawn()) {
                    nextRound = false;
                    break;
                }
            }

            if (nextRound) {
                stageLevel++;

                for (EnemySpawner e : enemySpawners) {
                    synchronized (enemies) {
                        enemies.add(e.spawn(Bacteria.class, stageLevel));
                    }
                }
            }
        }
    }

	@Override
	public void update () {
	    spawnEnemies();

        scoreString = new StringBuilder().append("Score: ").append(score).toString();
        scoreX = scorePaint.measureText(scoreString)/2 + (float) (10 * scale);

        cellsString = new StringBuilder().append("Cells: ").append(cells).toString();
        cellsX = cellsPaint.measureText(cellsString)/2 + (float) (10 * scale);

        trixString = new StringBuilder().append("Health: ").append(trixHP).toString();
        trixHPX = screenWidth - cellsPaint.measureText(trixString)/2 - (float) (10*scale);

        stageString = new StringBuilder().append("Wave: ").append(stageLevel).toString();
        stageLevelX = screenWidth - cellsPaint.measureText(stageString)/2 - (float) (10*scale);

        synchronized (towers) {
            if (!towers.isEmpty())
                for (Tower tower : towers) {
                    tower.update();
                    if (tower.canUpgrade(cells)) {
                        tower.highlightSpawner();
                    }
                    else tower.unhighlightSpawner();
                    synchronized (enemies) {
                        for (Enemy e: enemies) {
                            Projectile p = tower.attack(e);
                            if (p != null) {
                                projectiles.add(p);
                                break;
                            }
                        }
                    }
                }
        }

        synchronized (enemies) {
            Iterator<Enemy> iterator = enemies.iterator();

            while (iterator.hasNext()) {
                Enemy e = iterator.next();
                e.update();
                if (!e.isAlive ()) {
                    sem.playSFX(SoundEffectsManager.BACDEATH);
                    score += e.getScore ();
                    cells += e.getCells ();
                    e.despawn();
                    iterator.remove();
                }
                else if (e.getY() > screenHeight) {
                    trixHP -= e.getAttackDamage ();
                    if (trixHP < 100 && trixHP > 60)
                        sem.playSFX(SoundEffectsManager.PAIN1);
                    else if (trixHP <= 60 && trixHP > 30)
                        sem.playSFX(SoundEffectsManager.PAIN2);
                    else if (trixHP <= 30 && trixHP > 0)
                        sem.playSFX(SoundEffectsManager.PAIN3);
                    else
                        sem.playSFX(SoundEffectsManager.DEATH);
                    e.despawn();
                    iterator.remove();
                }
            }
        }

        synchronized (projectiles) {
            Iterator<Projectile> iterator = projectiles.iterator();

            while (iterator.hasNext()) {
                Projectile p = iterator.next();
                p.update();
                sem.playSFX(SoundEffectsManager.FIRE);

                if (p.getX() < 0 || p.getX() > screenWidth ||
                        p.getY() < 0 || p.getY() > screenHeight)
                    p.despawn();

                if (p.hit()) {
                    p.attack ();
                    p.despawn();
                    iterator.remove();
                }
            }
        }

        if (trixHP <= 0) {
            gameThread.setRunning(false);
            Intent intent = new Intent(mainActivity.getBaseContext(), GameOverActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("waves", stageLevel);
            mainActivity.startActivity(intent);
            mainActivity.finish();
            bmm.stopMusic();
        }

	}

	@Override
	public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(backgroundIcon, null, bgRect, new Paint());

        canvas.drawText(scoreString, scoreX, scoreY, scorePaint);
        canvas.drawText(cellsString, cellsX, cellsY, cellsPaint);
        canvas.drawText(trixString, trixHPX, trixHPY, trixHPPaint);
        canvas.drawText(stageString, stageLevelX, stageLevelY, stageLevelPaint);

        synchronized (towerSpawners) {
            for (int i = 0; i < towerSpawners.size(); i++)
                towerSpawners.get(i).draw(canvas);
        }

        synchronized (towers) {
            for (int i = 0; i < towers.size(); i++)
                towers.get(i).draw(canvas);
        }

        synchronized (enemies) {
            for (int i = 0; i < enemies.size(); i++)
                enemies.get(i).draw(canvas);
        }

        synchronized (projectiles) {
            for (int i = 0; i < projectiles.size(); i++)
                projectiles.get(i).draw(canvas);
        }

	}

}
